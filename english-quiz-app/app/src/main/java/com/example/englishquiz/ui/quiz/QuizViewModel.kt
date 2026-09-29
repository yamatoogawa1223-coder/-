package com.example.englishquiz.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.englishquiz.data.QuestionRepository
import com.example.englishquiz.data.model.Course
import com.example.englishquiz.data.model.Question

/** 1回のクイズで出題する問題数 */
const val QUESTIONS_PER_QUIZ = 10

/** クイズ機能の中で、今どの画面を表示しているか */
enum class QuizStep {
    COURSE_SELECT, // コース選択画面
    QUESTION,      // 問題画面
    RESULT         // 結果画面
}

/** 1問分の解答記録（どの問題に、どの選択肢を選んだか） */
data class AnswerRecord(
    val question: Question,
    val selectedIndex: Int
) {
    val isCorrect: Boolean
        get() = selectedIndex == question.answerIndex
}

/** クイズ画面に表示する内容（状態）をまとめたもの */
data class QuizUiState(
    val course: Course? = null,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null, // まだ答えていないときは null
    val answers: List<AnswerRecord> = emptyList()
) {
    /** 今表示している問題 */
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    /** 今の問題にもう答えたかどうか */
    val isAnswered: Boolean
        get() = selectedIndex != null

    /** 今の問題が最後の問題かどうか */
    val isLastQuestion: Boolean
        get() = currentIndex == questions.lastIndex

    /** 出題数 */
    val totalCount: Int
        get() = questions.size

    /** 正解した数 */
    val correctCount: Int
        get() = answers.count { it.isCorrect }

    /** 間違えた問題の記録 */
    val wrongAnswers: List<AnswerRecord>
        get() = answers.filter { !it.isCorrect }
}

/**
 * クイズの「頭脳」にあたるクラス。
 * 画面（Composable）はここにある状態を表示するだけにして、
 * 問題の出題・正誤判定・画面の切り替えはすべてここで行います。
 *
 * ViewModel を使うと、画面を回転させてもクイズの途中経過が消えません。
 */
class QuizViewModel : ViewModel() {

    /** 今表示している画面 */
    var currentStep by mutableStateOf(QuizStep.COURSE_SELECT)
        private set

    /** クイズの状態 */
    var uiState by mutableStateOf(QuizUiState())
        private set

    /** 選んだコースでクイズを始める（問題と選択肢の順番はランダム） */
    fun startQuiz(course: Course) {
        val questions = QuestionRepository.getQuestions(course)
            .shuffled()
            .take(QUESTIONS_PER_QUIZ)
            .map { it.withShuffledChoices() }
        startWith(course, questions)
    }

    /** 同じコースでもう一度クイズをする */
    fun retrySameCourse() {
        val course = uiState.course ?: return
        startQuiz(course)
    }

    /** 間違えた問題だけでもう一度クイズをする */
    fun retryWrongQuestions() {
        val wrongQuestions = uiState.wrongAnswers
            .map { it.question.withShuffledChoices() }
            .shuffled()
        if (wrongQuestions.isEmpty()) return
        startWith(uiState.course, wrongQuestions)
    }

    /** 選択肢をタップしたときに呼ぶ */
    fun selectAnswer(choiceIndex: Int) {
        val state = uiState
        val question = state.currentQuestion ?: return
        if (state.isAnswered) return // 2回答えられないようにする

        uiState = state.copy(
            selectedIndex = choiceIndex,
            answers = state.answers + AnswerRecord(question, choiceIndex)
        )
    }

    /** 「次の問題へ」「結果を見る」ボタンを押したときに呼ぶ */
    fun goToNext() {
        val state = uiState
        if (!state.isAnswered) return

        if (state.isLastQuestion) {
            currentStep = QuizStep.RESULT
        } else {
            uiState = state.copy(
                currentIndex = state.currentIndex + 1,
                selectedIndex = null
            )
        }
    }

    /** コース選択画面に戻る */
    fun backToCourseSelect() {
        uiState = QuizUiState()
        currentStep = QuizStep.COURSE_SELECT
    }

    private fun startWith(course: Course?, questions: List<Question>) {
        uiState = QuizUiState(course = course, questions = questions)
        currentStep = QuizStep.QUESTION
    }
}
