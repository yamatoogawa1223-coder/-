package com.example.englishquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.englishquiz.ui.quiz.CourseSelectScreen
import com.example.englishquiz.ui.quiz.QuizScreen
import com.example.englishquiz.ui.quiz.QuizStep
import com.example.englishquiz.ui.quiz.QuizViewModel
import com.example.englishquiz.ui.quiz.ResultScreen
import com.example.englishquiz.ui.theme.EnglishQuizTheme

class MainActivity : ComponentActivity() {

    // クイズの状態を管理する ViewModel（画面を回転しても消えない）
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnglishQuizTheme {
                EnglishQuizApp(viewModel = quizViewModel)
            }
        }
    }
}

/**
 * アプリ全体の画面。
 * ViewModel の currentStep を見て、表示する画面を切り替えます。
 * （ステップ2で Navigation Compose を使った本格的な画面遷移に置きかえます）
 */
@Composable
fun EnglishQuizApp(viewModel: QuizViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)

        when (viewModel.currentStep) {
            QuizStep.COURSE_SELECT -> CourseSelectScreen(
                onCourseSelected = { course -> viewModel.startQuiz(course) },
                modifier = screenModifier
            )

            QuizStep.QUESTION -> QuizScreen(
                uiState = viewModel.uiState,
                onAnswerSelected = { index -> viewModel.selectAnswer(index) },
                onNext = { viewModel.goToNext() },
                onQuit = { viewModel.backToCourseSelect() },
                modifier = screenModifier
            )

            QuizStep.RESULT -> ResultScreen(
                uiState = viewModel.uiState,
                onRetry = { viewModel.retrySameCourse() },
                onRetryWrong = { viewModel.retryWrongQuestions() },
                onBackToCourseSelect = { viewModel.backToCourseSelect() },
                modifier = screenModifier
            )
        }
    }
}
