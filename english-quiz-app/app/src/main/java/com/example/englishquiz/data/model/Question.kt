package com.example.englishquiz.data.model

/**
 * 4択クイズの問題1問分のデータ
 *
 * @property id 問題番号（重複しない番号）
 * @property course どのコースの問題か
 * @property unit 単元名（例：「be動詞」）
 * @property text 問題文
 * @property choices 選択肢（4つ）
 * @property answerIndex 正解の選択肢の番号（0〜3）
 * @property explanation 解説文
 */
data class Question(
    val id: Int,
    val course: Course,
    val unit: String,
    val text: String,
    val choices: List<String>,
    val answerIndex: Int,
    val explanation: String
) {
    /** 正解の選択肢の文字列 */
    val answer: String
        get() = choices[answerIndex]

    /**
     * 選択肢の順番をシャッフルした新しい問題を返す。
     * 正解の番号（answerIndex）もシャッフル後の位置に合わせて更新する。
     */
    fun withShuffledChoices(): Question {
        val shuffled = choices.shuffled()
        return copy(choices = shuffled, answerIndex = shuffled.indexOf(answer))
    }
}
