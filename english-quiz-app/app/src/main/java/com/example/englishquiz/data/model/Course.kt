package com.example.englishquiz.data.model

/**
 * クイズのコース（学年・入試対策）
 *
 * @property title 画面に表示するコース名
 * @property emoji コース名の横に表示する絵文字
 * @property description コースの説明文
 */
enum class Course(
    val title: String,
    val emoji: String,
    val description: String
) {
    GRADE1("中学1年", "🌱", "be動詞・一般動詞・複数形・代名詞・canなど"),
    GRADE2("中学2年", "🌿", "過去進行形・未来・不定詞・動名詞・比較など"),
    GRADE3("中学3年", "🌳", "受け身・現在完了・関係代名詞・分詞など"),
    EXAM("高校入試対策", "🎓", "3年間の総まとめ・会話表現・前置詞・語彙")
}
