package com.example.englishquiz.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.englishquiz.data.model.Question

/** 正解の色（緑） */
private val CorrectColor = Color(0xFF2E7D32)

/** 不正解の色（赤） */
private val WrongColor = Color(0xFFC62828)

/** 選択肢の前につける記号 */
private val ChoiceLabels = listOf("A", "B", "C", "D")

/**
 * 問題画面
 * 問題文と4つの選択肢を表示し、答えると正解・不正解と解説を表示します。
 */
@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = uiState.currentQuestion ?: return

    // 「やめる」確認ダイアログを表示するかどうか
    var showQuitDialog by remember { mutableStateOf(false) }

    // スマホの「戻る」操作をしたときも確認ダイアログを出す
    BackHandler { showQuitDialog = true }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── 上部：コース名・何問目か・やめるボタン ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.course?.title ?: "クイズ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${uiState.currentIndex + 1} / ${uiState.totalCount} 問",
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(onClick = { showQuitDialog = true }) {
                Text("やめる")
            }
        }

        // ── 進み具合のバー ──
        val answeredCount = uiState.currentIndex + if (uiState.isAnswered) 1 else 0
        LinearProgressIndicator(
            progress = { answeredCount.toFloat() / uiState.totalCount },
            modifier = Modifier.fillMaxWidth()
        )

        // ── 問題文 ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "単元：${question.unit}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        // ── 4つの選択肢 ──
        question.choices.forEachIndexed { index, choice ->
            ChoiceButton(
                label = ChoiceLabels[index],
                text = choice,
                index = index,
                question = question,
                selectedIndex = uiState.selectedIndex,
                onClick = { onAnswerSelected(index) }
            )
        }

        // ── 答えた後：正解・不正解と解説、次へボタン ──
        val selectedIndex = uiState.selectedIndex
        if (selectedIndex != null) {
            AnswerFeedback(
                question = question,
                isCorrect = selectedIndex == question.answerIndex
            )
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
            ) {
                Text(
                    text = if (uiState.isLastQuestion) "結果を見る" else "次の問題へ",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("クイズをやめますか？") },
            text = { Text("ここまでの結果は保存されません。") },
            confirmButton = {
                TextButton(onClick = {
                    showQuitDialog = false
                    onQuit()
                }) { Text("やめる") }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) { Text("続ける") }
            }
        )
    }
}

/** 選択肢のボタン。答えた後は正解を緑、選んだ不正解を赤で表示する */
@Composable
private fun ChoiceButton(
    label: String,
    text: String,
    index: Int,
    question: Question,
    selectedIndex: Int?,
    onClick: () -> Unit
) {
    val isAnswered = selectedIndex != null
    val isCorrectChoice = index == question.answerIndex
    val isSelected = index == selectedIndex

    // 状態によってボタンの色と記号を変える
    val containerColor: Color
    val contentColor: Color
    val mark: String
    when {
        !isAnswered -> {
            containerColor = MaterialTheme.colorScheme.primary
            contentColor = MaterialTheme.colorScheme.onPrimary
            mark = ""
        }
        isCorrectChoice -> {
            containerColor = CorrectColor
            contentColor = Color.White
            mark = "  ⭕"
        }
        isSelected -> {
            containerColor = WrongColor
            contentColor = Color.White
            mark = "  ❌"
        }
        else -> {
            containerColor = MaterialTheme.colorScheme.surfaceVariant
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            mark = ""
        }
    }

    Button(
        onClick = onClick,
        enabled = !isAnswered,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    ) {
        Text(
            text = "$label.  $text$mark",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/** 正解・不正解の表示と解説 */
@Composable
private fun AnswerFeedback(
    question: Question,
    isCorrect: Boolean
) {
    val color = if (isCorrect) CorrectColor else WrongColor
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = if (isCorrect) "⭕ 正解！" else "❌ ざんねん…",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            if (!isCorrect) {
                Text(
                    text = "正解は「${question.answer}」",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = question.explanation,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
