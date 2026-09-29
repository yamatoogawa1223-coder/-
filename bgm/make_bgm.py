#!/usr/bin/env python3
"""Undertale 風チップチューン BGM（オリジナル曲）を生成する。標準ライブラリのみ。
使い方: python3 make_bgm.py  ->  bgm.wav を出力
"""
import math, random, struct, wave

SR = 22050
BPM = 128
STEP = 60.0 / BPM / 2  # 8分音符

def hz(m):  # MIDI note -> Hz
    return 440.0 * 2 ** ((m - 69) / 12)

def pulse(f, t, duty):
    return 1.0 if (f * t) % 1.0 < duty else -1.0

def tri(f, t):
    p = (f * t) % 1.0
    return 4 * abs(p - 0.5) - 1

def render(events, total):
    buf = [0.0] * int(total * SR)
    for start, dur, midi, kind, vol in events:
        f = hz(midi) if midi else 0
        n0 = int(start * SR)
        n = int(dur * SR)
        for i in range(n):
            if n0 + i >= len(buf):
                break
            t = i / SR
            a = min(1.0, i / (0.004 * SR)) * max(0.0, 1 - (i / n) ** 3 if kind != "bass" else 1 - (i / n) ** 6)
            if kind == "lead":
                vib = 1 + 0.004 * math.sin(2 * math.pi * 5.5 * t) * min(1, t * 4)
                s = pulse(f * vib, t, 0.25)
            elif kind == "arp":
                s = pulse(f, t, 0.125)
            elif kind == "bass":
                s = tri(f, t)
            else:  # hat
                s = random.uniform(-1, 1)
                a = max(0.0, 1 - i / n) ** 2
            buf[n0 + i] += s * a * vol
    return buf

# コード進行 (根音, 3度, 5度) — Dm | Bb | F | C | Dm | Bb | Gm | A
N = dict(C=0, D=2, E=4, F=5, G=7, A=9, B=11)
def chord(root, quality):
    r = root
    return [r, r + (3 if quality == "m" else 4), r + 7]
prog = [
    (chord(50, "m"), 38), (chord(46, "M"), 34), (chord(53, "M"), 41), (chord(48, "M"), 36),
    (chord(50, "m"), 38), (chord(46, "M"), 34), (chord(55, "m"), 43), (chord(57, "M"), 45),
]

# メロディ: 1小節=8ステップ。 (半音, 長さステップ) None=休符。最初の4小節=A, 次の4小節=B
D5, E5, F5, G5, A5, Bb5, C6, D6 = 74, 76, 77, 79, 81, 82, 84, 86
melA = [
    [(A5,2),(F5,1),(A5,1),(D6,2),(C6,1),(A5,1)],
    [(Bb5,2),(A5,1),(F5,1),(D5,2),(F5,2)],
    [(A5,2),(C6,1),(A5,1),(F5,2),(A5,1),(G5,1)],
    [(E5,2),(G5,2),(C6,3),(None,1)],
]
melB = [
    [(D6,2),(A5,1),(D6,1),(F5,2),(A5,2)],
    [(Bb5,1),(C6,1),(D6,2),(C6,2),(A5,2)],
    [(G5,2),(Bb5,2),(D6,2),(C6,1),(Bb5,1)],
    [(A5,1),(G5,1),(F5,1),(E5,1),(D5,4)],
]
melody = melA + melB

def build(loops=2):
    ev = []
    bars = len(prog)
    for lp in range(loops):
        for b in range(bars):
            base = (lp * bars + b) * 8
            ch, bass = prog[b]
            # ベース: 根音・オクターブ交互
            for k in range(8):
                m = bass if k % 4 != 3 else bass + 12
                ev.append(((base + k) * STEP, STEP * 0.95, m, "bass", 0.32))
            # アルペジオ
            pat = [0, 1, 2, 1, 0, 1, 2, 1]
            for k in range(8):
                ev.append(((base + k) * STEP, STEP * 0.8, ch[pat[k]] + 24, "arp", 0.07))
            # ハイハット
            for k in range(8):
                if k % 2 == 1:
                    ev.append(((base + k) * STEP, STEP * 0.3, 0, "hat", 0.05))
            # メロディ（2周目は装飾としてオクターブ下のハモりを足す）
            pos = 0
            for m, d in melody[b]:
                if m:
                    ev.append(((base + pos) * STEP, d * STEP * 0.92, m, "lead", 0.16))
                    if lp == 1:
                        ev.append(((base + pos) * STEP, d * STEP * 0.92, m - 12, "arp", 0.05))
                pos += d
    return ev, loops * bars * 8 * STEP

if __name__ == "__main__":
    random.seed(1)
    ev, total = build()
    buf = render(ev, total + 0.5)
    peak = max(abs(x) for x in buf) or 1
    out = [int(x / peak * 0.85 * 32767) for x in buf]
    with wave.open("bgm.wav", "wb") as w:
        w.setnchannels(1); w.setsampwidth(2); w.setframerate(SR)
        w.writeframes(struct.pack("<%dh" % len(out), *out))
    print(f"bgm.wav: {total:.1f}s (ループ想定は先頭から {total/2:.1f}s ごと)")
