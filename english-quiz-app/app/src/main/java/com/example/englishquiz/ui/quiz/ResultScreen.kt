package com.example.englishquiz.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * 結果画面
 * 正解数と、間違えた問題の答え・解説を表示します。
 */
@Composable
fun ResultScreen(
    uiState: QuizUiState,
    onRetry: () -> Unit,
    onRetryWrong: () -> Unit,
    onBackToCourseSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    // スマホの「戻る」操作でコース選択画面に戻る
    BackHandler { onBackToCourseSelect() }

    val total = uiState.totalCount
    val correct = uiState.correctCount
    val percent = if (total == 0) 0 else correct * 100 / total
    val message = when {
        percent == 100 -> "パーフェクト！すばらしい！🎉"
        percent >= 80 -> "よくできました！その調子！✨"
        percent >= 50 -> "もう少し！間違えた問題を復習しよう💪"
        else -> "解説を読んで、もう一度チャレンジしよう📖"
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── スコア ──
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${uiState.course?.title ?: ""} の結果",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$correct / $total 問 正解",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "正答率 $percent%",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // ── ボタン ──
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (uiState.wrongAnswers.isNotEmpty()) {
                    Button(
                        onClick = onRetryWrong,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 52.dp)
                    ) {
                        Text("間違えた問題だけ解き直す（${uiState.wrongAnswers.size}問）")
                    }
                }
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                ) {
                    Text("同じコースでもう一度")
                }
                OutlinedButton(
                    onClick = onBackToCourseSelect,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                ) {
                    Text("コース選択にもどる")
                }
            }
        }

        // ── 間違えた問題の一覧 ──
        if (uiState.wrongAnswers.isNotEmpty()) {
            item {
                Text(
                    text = "間違えた問題",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            items(uiState.wrongAnswers) { record ->
                WrongAnswerCard(record)
            }
        }
    }
}

/** 間違えた問題1問分のカード */
@Composable
private fun WrongAnswerCard(record: AnswerRecord) {
    val question = record.question
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "あなたの答え：${question.choices[record.selectedIndex]}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Text(
                text = "正解：${question.answer}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = question.explanation,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
