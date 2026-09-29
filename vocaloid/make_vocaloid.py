#!/usr/bin/env python3
"""ボカロ風デモ曲（オリジナル）。標準ライブラリのみ。
歌声は母音フォルマント合成の仮ボーカル。本物のボカロ/UTAUには lyrics.txt とメロディを流し込む想定。
使い方: python3 make_vocaloid.py -> vocaloid_demo.wav
"""
import math, random, struct, wave

SR = 22050
BPM = 160
STEP = 60.0 / BPM / 2  # 8分音符

def hz(m): return 440.0 * 2 ** ((m - 69) / 12)

VOWEL_ROWS = {"a": "あかさたなはまやらわがざだばぱ", "i": "いきしちにひみりぎじぢびぴ",
              "u": "うくすつぬふむゆるぐずづぶぷ", "e": "えけせてねへめれげぜでべぺ",
              "o": "おこそとのほもよろをごぞどぼぽ"}
def vowel(k):
    for v, s in VOWEL_ROWS.items():
        if k in s: return v
    return "a"

FORM = {"a": (800, 1200, 2500), "i": (300, 2300, 3000), "u": (350, 1300, 2400),
        "e": (500, 1900, 2500), "o": (500, 900, 2400)}

class BP:  # biquad バンドパス
    def __init__(s, f, q=8):
        w = 2 * math.pi * f / SR; al = math.sin(w) / (2 * q); a0 = 1 + al
        s.b0 = al / a0; s.b2 = -al / a0
        s.a1 = -2 * math.cos(w) / a0; s.a2 = (1 - al) / a0
        s.x1 = s.x2 = s.y1 = s.y2 = 0.0
    def __call__(s, x):
        y = s.b0 * x + s.b2 * s.x2 - s.a1 * s.y1 - s.a2 * s.y2
        s.x2, s.x1, s.y2, s.y1 = s.x1, x, s.y1, y
        return y

# 歌詞つきメロディ (MIDI, かな, 8分音符の長さ)  Am | F | C | G | Am | F | G | Am
Aは = [
 [(76,"よ",1),(76,"ぞ",1),(81,"ら",1),(79,"に",1),(76,"と",1),(74,"け",1),(72,"る",1),(74,"う",1)],
 [(77,"き",1),(77,"み",1),(81,"の",1),(79,"こ",1),(77,"え",1),(76,"が",1),(74,"き",1),(72,"こ",1)],
 [(72,"ま",1),(76,"だ",1),(79,"と",1),(79,"ど",1),(81,"い",1),(79,"て",1),(76,"る",1),(72,"よ",1)],
 [(74,"ね",1),(74,"え",1),(79,"も",1),(77,"う",1),(76,"い",1),(74,"ち",1),(71,"ど",1),(74,"だ",1)],
 [(81,"は",1),(81,"し",1),(84,"れ",1),(83,"ぼ",1),(81,"く",1),(79,"ら",1),(76,"の",1),(79,"ひ",1)],
 [(77,"か",1),(77,"り",1),(81,"を",1),(79,"つ",1),(77,"か",1),(76,"め",1),(74,"き",1),(72,"み",1)],
 [(79,"と",1),(79,"お",1),(83,"く",1),(81,"ま",1),(79,"で",1),(77,"ゆ",1),(74,"こ",1),(71,"う",1)],
 [(81,"あ",3),(79,"す",1),(76,"へ",4)],
]
prog = [(45,[57,60,64]),(41,[57,60,65]),(48,[55,60,64]),(43,[55,59,62]),
        (45,[57,60,64]),(41,[57,60,65]),(43,[55,59,62]),(45,[57,60,64])]

def mix(buf, start, samples, vol):
    n0 = int(start * SR)
    for i, s in enumerate(samples):
        if n0 + i < len(buf): buf[n0 + i] += s * vol

def voice(f0, dur, v):
    n = int(dur * SR); fs = FORM[v]
    filt = [BP(fs[0], 6), BP(fs[1], 8), BP(fs[2], 10)]; g = (1.0, 0.7, 0.35)
    out = []; ph = 0.0
    for i in range(n):
        t = i / SR
        f = f0 * (1 + 0.006 * math.sin(2 * math.pi * 5.8 * t) * min(1, t * 3))
        ph = (ph + f / SR) % 1.0
        src = 2 * ph - 1
        y = sum(g[k] * filt[k](src) for k in range(3)) * 4 + src * 0.08
        a = min(1, i / (0.012 * SR)) * min(1, (n - i) / (0.03 * SR))
        out.append(y * a)
    return out

def build():
    total = 16 * 8 * STEP + 0.6
    lead = [0.0] * int(total * SR); inst = [0.0] * int(total * SR)
    for lp in range(2):
        for b in range(8):
            base = (lp * 8 + b) * 8 * STEP
            root, ch = prog[b]
            pos = 0
            for m, k, d in Aは[b]:
                mix(lead, base + pos * STEP, voice(hz(m), d * STEP * 0.95, vowel(k)), 0.35)
                pos += d
            for k in range(8):
                t0 = base + k * STEP
                mix(inst, t0, [(1 if (hz(root) * i / SR) % 1 < .5 else -1) * 0.5 * max(0, 1 - i / (STEP * SR * .9)) for i in range(int(STEP * SR))], 0.22)
                mix(inst, t0, [(1 if (hz(ch[k % 3] + 0) * 2 * i / SR) % 1 < .25 else -1) * max(0, 1 - i / (STEP * SR)) for i in range(int(STEP * SR * .9))], 0.06)
                if k % 2 == 0:  # キック
                    mix(inst, t0, [math.sin(2 * math.pi * (50 + 110 * math.exp(-i / 500)) * i / SR) * math.exp(-i / 2500) for i in range(int(.15 * SR))], 0.5)
                if k % 4 == 2:  # スネア
                    mix(inst, t0, [random.uniform(-1, 1) * math.exp(-i / 1800) for i in range(int(.14 * SR))], 0.25)
                mix(inst, t0, [random.uniform(-1, 1) * math.exp(-i / 500) for i in range(int(.05 * SR))], 0.07)
    return [a + b for a, b in zip(lead, inst)], total

if __name__ == "__main__":
    random.seed(3)
    buf, total = build()
    peak = max(abs(x) for x in buf)
    out = [int(x / peak * 0.85 * 32767) for x in buf]
    with wave.open("vocaloid_demo.wav", "wb") as w:
        w.setnchannels(1); w.setsampwidth(2); w.setframerate(SR)
        w.writeframes(struct.pack("<%dh" % len(out), *out))
    print(f"{total:.1f}s")
