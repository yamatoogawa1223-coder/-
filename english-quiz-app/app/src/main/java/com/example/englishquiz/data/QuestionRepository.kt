package com.example.englishquiz.data

import com.example.englishquiz.data.model.Course
import com.example.englishquiz.data.model.Question

/**
 * クイズの問題データを管理する場所。
 *
 * ステップ1では問題をコードの中に直接書いています。
 * 問題を増やしたいときは、下の allQuestions に Question(...) を追加してください。
 * （id は他の問題と重ならない番号にすること）
 *
 * 後のステップで、問題データを JSON ファイルやデータベース（Room）に移します。
 */
object QuestionRepository {

    /** 指定したコースの問題をすべて返す */
    fun getQuestions(course: Course): List<Question> =
        allQuestions.filter { it.course == course }

    /** 指定したコースの問題数を返す */
    fun countOf(course: Course): Int =
        allQuestions.count { it.course == course }

    private val allQuestions: List<Question> = listOf(

        // ───────────── 中学1年 ─────────────
        Question(
            id = 101, course = Course.GRADE1, unit = "be動詞",
            text = "I (   ) a student.",
            choices = listOf("am", "is", "are", "be"),
            answerIndex = 0,
            explanation = "主語が I（私は）のとき、be動詞は am を使います。\n訳：私は生徒です。"
        ),
        Question(
            id = 102, course = Course.GRADE1, unit = "be動詞",
            text = "My sister (   ) a nurse.",
            choices = listOf("am", "is", "are", "be"),
            answerIndex = 1,
            explanation = "主語 My sister は3人称単数なので、be動詞は is です。\n訳：私の姉（妹）は看護師です。"
        ),
        Question(
            id = 103, course = Course.GRADE1, unit = "一般動詞（3単現）",
            text = "Ken (   ) soccer every day.",
            choices = listOf("play", "plays", "playing", "is play"),
            answerIndex = 1,
            explanation = "主語が3人称単数（Ken）で現在の文なので、動詞に s をつけて plays にします。\n訳：ケンは毎日サッカーをします。"
        ),
        Question(
            id = 104, course = Course.GRADE1, unit = "一般動詞の否定文",
            text = "I (   ) like carrots.",
            choices = listOf("am not", "don't", "doesn't", "isn't"),
            answerIndex = 1,
            explanation = "一般動詞 like の否定文です。主語が I なので don't を使います。\n訳：私はニンジンが好きではありません。"
        ),
        Question(
            id = 105, course = Course.GRADE1, unit = "一般動詞の疑問文",
            text = "(   ) you speak English? — Yes, I do.",
            choices = listOf("Are", "Do", "Does", "Is"),
            answerIndex = 1,
            explanation = "一般動詞 speak の疑問文で、主語が you なので Do で始めます。答えの文も do を使っています。\n訳：あなたは英語を話しますか。— はい、話します。"
        ),
        Question(
            id = 106, course = Course.GRADE1, unit = "名詞の複数形",
            text = "I have three (   ).",
            choices = listOf("box", "boxs", "boxies", "boxes"),
            answerIndex = 3,
            explanation = "x で終わる名詞は es をつけて複数形にします。box → boxes。\n訳：私は箱を3つ持っています。"
        ),
        Question(
            id = 107, course = Course.GRADE1, unit = "代名詞",
            text = "This is (   ) bag.（これは私のかばんです。）",
            choices = listOf("I", "my", "me", "mine"),
            answerIndex = 1,
            explanation = "名詞 bag の前に置いて「私の」を表すのは my です。mine は「私のもの」という意味で、後ろに名詞を置きません。"
        ),
        Question(
            id = 108, course = Course.GRADE1, unit = "疑問詞",
            text = "(   ) is this? — It's a pen.",
            choices = listOf("What", "Who", "Where", "When"),
            answerIndex = 0,
            explanation = "「それはペンです」と物を答えているので、「何」をたずねる What を使います。\n訳：これは何ですか。— それはペンです。"
        ),
        Question(
            id = 109, course = Course.GRADE1, unit = "現在進行形",
            text = "Mika is (   ) now.",
            choices = listOf("swim", "swims", "swiming", "swimming"),
            answerIndex = 3,
            explanation = "現在進行形は〈be動詞＋動詞のing形〉です。swim は m を重ねて swimming になります。\n訳：ミカは今泳いでいます。"
        ),
        Question(
            id = 110, course = Course.GRADE1, unit = "can",
            text = "Tom can (   ) the piano.",
            choices = listOf("play", "plays", "playing", "played"),
            answerIndex = 0,
            explanation = "can の後ろの動詞は、主語に関係なく原形（もとの形）にします。\n訳：トムはピアノをひくことができます。"
        ),
        Question(
            id = 111, course = Course.GRADE1, unit = "命令文",
            text = "(   ) quiet in the library.",
            choices = listOf("Be", "Are", "Is", "Do"),
            answerIndex = 0,
            explanation = "quiet（静かな）は形容詞なので、be動詞の命令文〈Be ＋形容詞〉にします。\n訳：図書館では静かにしなさい。"
        ),
        Question(
            id = 112, course = Course.GRADE1, unit = "一般動詞の過去形",
            text = "I (   ) Kyoto last year.",
            choices = listOf("visit", "visits", "visited", "visiting"),
            answerIndex = 2,
            explanation = "last year（去年）があるので過去の文です。visit の過去形は visited です。\n訳：私は去年、京都を訪れました。"
        ),

        // ───────────── 中学2年 ─────────────
        Question(
            id = 201, course = Course.GRADE2, unit = "be動詞の過去形",
            text = "We (   ) busy yesterday.",
            choices = listOf("are", "was", "were", "is"),
            answerIndex = 2,
            explanation = "yesterday（昨日）があるので過去の文です。主語が We（複数）なので were を使います。\n訳：私たちは昨日忙しかったです。"
        ),
        Question(
            id = 202, course = Course.GRADE2, unit = "過去進行形",
            text = "I (   ) watching TV when he called me.",
            choices = listOf("am", "was", "were", "did"),
            answerIndex = 1,
            explanation = "過去進行形は〈was / were ＋ ing形〉です。主語が I なので was を使います。\n訳：彼が電話をしてきたとき、私はテレビを見ていました。"
        ),
        Question(
            id = 203, course = Course.GRADE2, unit = "未来（will）",
            text = "It (   ) rain tomorrow.",
            choices = listOf("is", "will", "does", "was"),
            answerIndex = 1,
            explanation = "tomorrow（明日）の予想を表すので〈will ＋動詞の原形〉を使います。\n訳：明日は雨が降るでしょう。"
        ),
        Question(
            id = 204, course = Course.GRADE2, unit = "未来（be going to）",
            text = "I'm going to (   ) my uncle next week.",
            choices = listOf("visit", "visits", "visited", "visiting"),
            answerIndex = 0,
            explanation = "be going to の後ろは動詞の原形です。\n訳：私は来週、おじを訪ねるつもりです。"
        ),
        Question(
            id = 205, course = Course.GRADE2, unit = "不定詞",
            text = "I want (   ) a doctor.",
            choices = listOf("be", "to be", "being", "am"),
            answerIndex = 1,
            explanation = "want to ～ で「～したい」。to の後ろは原形なので to be になります。\n訳：私は医者になりたいです。"
        ),
        Question(
            id = 206, course = Course.GRADE2, unit = "動名詞",
            text = "I enjoyed (   ) with my friends.",
            choices = listOf("talk", "to talk", "talking", "talked"),
            answerIndex = 2,
            explanation = "enjoy の後ろには動名詞（ing形）を置きます。enjoy to ～ とは言いません。\n訳：私は友達と話して楽しみました。"
        ),
        Question(
            id = 207, course = Course.GRADE2, unit = "比較級",
            text = "This bag is (   ) than that one.",
            choices = listOf("big", "bigger", "biggest", "more big"),
            answerIndex = 1,
            explanation = "than（～よりも）があるので比較級です。big は g を重ねて bigger になります。\n訳：このかばんはあのかばんより大きいです。"
        ),
        Question(
            id = 208, course = Course.GRADE2, unit = "最上級",
            text = "Mt. Fuji is the (   ) mountain in Japan.",
            choices = listOf("high", "higher", "highest", "most high"),
            answerIndex = 2,
            explanation = "〈the ＋最上級＋ in ～〉で「～の中でいちばん…」。high の最上級は highest です。\n訳：富士山は日本でいちばん高い山です。"
        ),
        Question(
            id = 209, course = Course.GRADE2, unit = "比較（more）",
            text = "This book is (   ) interesting than that one.",
            choices = listOf("much", "more", "most", "very"),
            answerIndex = 1,
            explanation = "interesting のような長い語の比較級は、前に more をつけます。\n訳：この本はあの本よりおもしろいです。"
        ),
        Question(
            id = 210, course = Course.GRADE2, unit = "have to",
            text = "He (   ) to get up early tomorrow.",
            choices = listOf("must", "has", "have", "does"),
            answerIndex = 1,
            explanation = "have to ～ で「～しなければならない」。主語が He（3人称単数）なので has to になります。must to とは言いません。\n訳：彼は明日早く起きなければなりません。"
        ),
        Question(
            id = 211, course = Course.GRADE2, unit = "接続詞",
            text = "(   ) it was cold, I wore a coat.",
            choices = listOf("Because", "But", "And", "Or"),
            answerIndex = 0,
            explanation = "「寒かったので」と理由を表すので Because を使います。\n訳：寒かったので、私はコートを着ました。"
        ),
        Question(
            id = 212, course = Course.GRADE2, unit = "There is / are",
            text = "There (   ) two cats under the table.",
            choices = listOf("is", "are", "be", "am"),
            answerIndex = 1,
            explanation = "There is / are の文では、後ろの名詞が複数（two cats）なので are を使います。\n訳：テーブルの下にネコが2匹います。"
        ),

        // ───────────── 中学3年 ─────────────
        Question(
            id = 301, course = Course.GRADE3, unit = "受け身",
            text = "This room is (   ) every day.",
            choices = listOf("clean", "cleans", "cleaned", "cleaning"),
            answerIndex = 2,
            explanation = "受け身（～される）は〈be動詞＋過去分詞〉です。\n訳：この部屋は毎日そうじされます。"
        ),
        Question(
            id = 302, course = Course.GRADE3, unit = "受け身（by）",
            text = "This book was written (   ) Natsume Soseki.",
            choices = listOf("by", "of", "from", "with"),
            answerIndex = 0,
            explanation = "受け身で「～によって」と動作をする人を表すときは by を使います。\n訳：この本は夏目漱石によって書かれました。"
        ),
        Question(
            id = 303, course = Course.GRADE3, unit = "現在完了（継続）",
            text = "I have (   ) in Osaka for five years.",
            choices = listOf("live", "lived", "living", "lives"),
            answerIndex = 1,
            explanation = "現在完了は〈have / has ＋過去分詞〉です。live の過去分詞は lived です。\n訳：私は5年間ずっと大阪に住んでいます。"
        ),
        Question(
            id = 304, course = Course.GRADE3, unit = "現在完了（since / for）",
            text = "She has been sick (   ) last Sunday.",
            choices = listOf("for", "since", "from", "ago"),
            answerIndex = 1,
            explanation = "last Sunday のように「始まった時点」を表すときは since（～以来）を使います。for は for three days のように「期間」を表します。\n訳：彼女はこの前の日曜日からずっと体調が悪いです。"
        ),
        Question(
            id = 305, course = Course.GRADE3, unit = "現在完了（経験）",
            text = "Have you ever (   ) to Hokkaido?",
            choices = listOf("go", "went", "been", "gone"),
            answerIndex = 2,
            explanation = "「～へ行ったことがある」は have been to ～ で表します。have gone to ～ は「～へ行ってしまった（今ここにいない）」という意味です。\n訳：あなたは北海道へ行ったことがありますか。"
        ),
        Question(
            id = 306, course = Course.GRADE3, unit = "現在完了（完了）",
            text = "I haven't finished my homework (   ).",
            choices = listOf("already", "yet", "ever", "still"),
            answerIndex = 1,
            explanation = "現在完了の否定文で「まだ～していない」は、文の最後に yet を置きます。\n訳：私はまだ宿題を終えていません。"
        ),
        Question(
            id = 307, course = Course.GRADE3, unit = "関係代名詞（who）",
            text = "I have a friend (   ) lives in Canada.",
            choices = listOf("who", "which", "what", "whose"),
            answerIndex = 0,
            explanation = "先行詞 a friend が「人」で、後ろに動詞 lives が続くので、主格の関係代名詞 who を使います。\n訳：私にはカナダに住んでいる友達がいます。"
        ),
        Question(
            id = 308, course = Course.GRADE3, unit = "関係代名詞（which）",
            text = "This is the bus (   ) goes to the station.",
            choices = listOf("who", "which", "where", "what"),
            answerIndex = 1,
            explanation = "先行詞 the bus が「物」なので、関係代名詞 which を使います（that でもOK）。\n訳：これは駅へ行くバスです。"
        ),
        Question(
            id = 309, course = Course.GRADE3, unit = "分詞（後置修飾）",
            text = "The boy (   ) under the tree is Ken.",
            choices = listOf("sit", "sits", "sitting", "sat"),
            answerIndex = 2,
            explanation = "「～している」という意味で名詞を後ろから説明するときは ing形 を使います。\n訳：木の下にすわっている男の子はケンです。"
        ),
        Question(
            id = 310, course = Course.GRADE3, unit = "間接疑問文",
            text = "Do you know where (   )?",
            choices = listOf("he lives", "does he live", "lives he", "he live"),
            answerIndex = 0,
            explanation = "文の中に疑問詞で始まる文が入る「間接疑問文」では、〈疑問詞＋主語＋動詞〉の語順になります。\n訳：あなたは彼がどこに住んでいるか知っていますか。"
        ),
        Question(
            id = 311, course = Course.GRADE3, unit = "It is ～ for ... to",
            text = "It is important (   ) us to study English.",
            choices = listOf("of", "for", "to", "with"),
            answerIndex = 1,
            explanation = "〈It is ～ for 人 to ...〉で「（人）にとって…することは～だ」という意味になります。\n訳：私たちにとって英語を勉強することは大切です。"
        ),
        Question(
            id = 312, course = Course.GRADE3, unit = "仮定法",
            text = "I wish I (   ) play the guitar well.",
            choices = listOf("can", "could", "will", "am"),
            answerIndex = 1,
            explanation = "I wish ～ で「～ならいいのに」と現実とちがう願望を表すときは、過去形を使います。can の過去形 could を選びます。\n訳：ギターを上手にひけたらいいのに。"
        ),

        // ───────────── 高校入試対策 ─────────────
        Question(
            id = 401, course = Course.EXAM, unit = "会話表現",
            text = "A: Can I use your pen?\nB: (   )",
            choices = listOf("Sure. Here you are.", "No, I'm not.", "You're welcome.", "Yes, I can."),
            answerIndex = 0,
            explanation = "「ペンを使ってもいい？」と許可を求められているので「いいよ。はい、どうぞ。」が自然です。Yes, I can. は「私はできます」となり、答えとして不自然です。"
        ),
        Question(
            id = 402, course = Course.EXAM, unit = "会話表現",
            text = "A: Thank you for your help.\nB: (   )",
            choices = listOf("Yes, please.", "You're welcome.", "That's right.", "I'm sorry."),
            answerIndex = 1,
            explanation = "お礼を言われたときは You're welcome.（どういたしまして）と答えます。"
        ),
        Question(
            id = 403, course = Course.EXAM, unit = "語彙",
            text = "A (   ) is a place where people borrow books.",
            choices = listOf("hospital", "station", "library", "museum"),
            answerIndex = 2,
            explanation = "「人々が本を借りる場所」は library（図書館）です。borrow は「借りる」という意味です。"
        ),
        Question(
            id = 404, course = Course.EXAM, unit = "語彙",
            text = "The eighth month of the year is (   ).",
            choices = listOf("June", "July", "August", "September"),
            answerIndex = 2,
            explanation = "eighth は「8番目の」。1年の8番目の月は August（8月）です。"
        ),
        Question(
            id = 405, course = Course.EXAM, unit = "動名詞",
            text = "Thank you for (   ) me.",
            choices = listOf("help", "to help", "helping", "helped"),
            answerIndex = 2,
            explanation = "前置詞 for の後ろに動詞を置くときは動名詞（ing形）にします。\n訳：手伝ってくれてありがとう。"
        ),
        Question(
            id = 406, course = Course.EXAM, unit = "過去形と現在完了",
            text = "I (   ) him yesterday.",
            choices = listOf("have met", "met", "meet", "has met"),
            answerIndex = 1,
            explanation = "yesterday のように過去のある時点をはっきり表す語は、現在完了と一緒に使えません。過去形 met を使います。\n訳：私は昨日彼に会いました。"
        ),
        Question(
            id = 407, course = Course.EXAM, unit = "比較（as ～ as）",
            text = "Tom is as (   ) as his father.",
            choices = listOf("tall", "taller", "tallest", "more tall"),
            answerIndex = 0,
            explanation = "〈as ＋形容詞の原級＋ as〉で「～と同じくらい…」。比較級にはしません。\n訳：トムはお父さんと同じくらいの背の高さです。"
        ),
        Question(
            id = 408, course = Course.EXAM, unit = "前置詞",
            text = "I was born (   ) 2011.",
            choices = listOf("on", "at", "in", "for"),
            answerIndex = 2,
            explanation = "年・月・季節の前には in を使います。日付や曜日は on、時刻は at です。\n訳：私は2011年に生まれました。"
        ),
        Question(
            id = 409, course = Course.EXAM, unit = "前置詞",
            text = "Let's meet (   ) three o'clock.",
            choices = listOf("in", "on", "at", "to"),
            answerIndex = 2,
            explanation = "時刻の前には at を使います。\n訳：3時に会いましょう。"
        ),
        Question(
            id = 410, course = Course.EXAM, unit = "疑問詞＋to",
            text = "Please tell me (   ) to the station.",
            choices = listOf("how to get", "how get", "to how get", "get how to"),
            answerIndex = 0,
            explanation = "〈how to ＋動詞の原形〉で「～のしかた」。how to get to ～ で「～への行き方」です。\n訳：駅への行き方を教えてください。"
        ),
        Question(
            id = 411, course = Course.EXAM, unit = "後置修飾（接触節）",
            text = "This is the picture (   ) yesterday.",
            choices = listOf("I took", "I took it", "took I", "taking I"),
            answerIndex = 0,
            explanation = "〈名詞＋主語＋動詞〉の形で、後ろから名詞を説明できます（関係代名詞 which / that の省略）。picture を it でくり返さないことに注意。\n訳：これは私が昨日撮った写真です。"
        ),
        Question(
            id = 412, course = Course.EXAM, unit = "How much / many",
            text = "How (   ) money do you have?",
            choices = listOf("many", "much", "long", "old"),
            answerIndex = 1,
            explanation = "money は数えられない名詞なので、量をたずねるときは How much を使います。数えられる名詞（books など）には How many を使います。\n訳：あなたはお金をいくら持っていますか。"
        )
    )
}
