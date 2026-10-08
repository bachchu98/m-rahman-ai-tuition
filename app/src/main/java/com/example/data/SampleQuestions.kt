package com.example.data

data class SampleQuestion(
    val id: String,
    val subject: String,
    val className: String,
    val question: String,
    val sampleSolutionBengali: String,
    val simplerBengali: String,
    val englishSolution: String
)

object SampleData {
    val sampleQuestions = listOf(
        SampleQuestion(
            id = "q1",
            subject = "গণিত (বীজগণিত)",
            className = "Class 10",
            question = "সমাধান করো: 2x² - 5x + 3 = 0",
            sampleSolutionBengali = """
                **প্রশ্ন:** 2x² - 5x + 3 = 0 সমীকরণটির সমাধান নির্ণয় করো।

                **১. প্রদত্ত সমীকরণ:**
                এটি একটি দ্বিঘাত সমীকরণ: ax² + bx + c = 0
                এখানে: a = 2, b = -5, c = 3

                **২. সমাধান পদ্ধতি (মিডিল টার্ম ফ্যাক্টরাইজেশন):**
                আমাদের এমন দুটি সংখ্যা খুঁজতে হবে যাদের গুণফল (2 × 3) = 6 এবং যোগফল -5।
                সংখ্যা দুটি হলো: -2 এবং -3 [কারণ (-2) × (-3) = 6 এবং (-2) + (-3) = -5]।

                **৩. সমাধান ধাপ:**
                2x² - 2x - 3x + 3 = 0
                ⇒ 2x(x - 1) - 3(x - 1) = 0
                ⇒ (x - 1)(2x - 3) = 0

                হয়: x - 1 = 0 ⇒ x = 1
                অথবা: 2x - 3 = 0 ⇒ 2x = 3 ⇒ x = 3/2 বা 1.5

                **৪. চূড়ান্ত উত্তর:**
                নির্ণেয় সমাধান: **x = 1 অথবা x = 3/2**

                **৫. এম রহমান স্যারের স্পেশাল টিপ্স:**
                সবসময় মধ্যপদ সহগ বিভাজন করার সময় চিহ্নের দিকে সতর্ক দৃষ্টি রাখবে। শ্রীধর আচার্যের সূত্র x = (-b ± √(b² - 4ac)) / (2a) দিয়েও সরাসরি মান যাচাই করে নিতে পারো!
            """.trimIndent(),
            simplerBengali = """
                **সহজ করে বোঝো (বাচ্চাদের ভাষায়):**
                মনে করো তোমার কাছে একটি সমীকরণ আছে: 2x² - 5x + 3 = 0।
                
                ১. মাঝখানের সংখ্যা -5 কে আমরা দুটো টুকরো করলাম: -2 আর -3।
                ২. প্রথম দুটো থেকে 2x কমন নিলাম, থাকলো (x - 1)।
                ৩. পরের দুটো থেকে -3 কমন নিলাম, থাকলো (x - 1)।
                ৪. এবার দুটো ব্র্যাকেট পেলাম: (x - 1) আর (2x - 3)।
                ৫. দুটোর গুণফল শূন্য মানে, যেকোনো একটা শূন্য হবেই!
                   - যদি প্রথমটা শূন্য হয়: x = 1
                   - যদি দ্বিতীয়টা শূন্য হয়: x = 3/2 (দেড়)
                
                ব্যাস! উত্তর পেয়ে গেলাম: x এর মান ১ অথবা ৩/২।
            """.trimIndent(),
            englishSolution = """
                **Problem:** Solve the quadratic equation: 2x² - 5x + 3 = 0

                **Step 1: Identify Coefficients**
                Standard form: ax² + bx + c = 0
                Here: a = 2, b = -5, c = 3

                **Step 2: Factoring Method (Splitting the Middle Term)**
                We need two numbers whose product is a × c = 2 × 3 = 6, and sum is b = -5.
                The numbers are -2 and -3.

                **Step 3: Factorization**
                2x² - 2x - 3x + 3 = 0
                2x(x - 1) - 3(x - 1) = 0
                (x - 1)(2x - 3) = 0

                **Step 4: Solve for x**
                Case 1: x - 1 = 0 => x = 1
                Case 2: 2x - 3 = 0 => x = 3/2

                **Final Answer:**
                x = 1 or x = 3/2 (1.5)
            """.trimIndent()
        ),
        SampleQuestion(
            id = "q2",
            subject = "পদার্থবিজ্ঞান",
            className = "Class 10",
            question = "ওহমের সূত্রটি লেখো এবং রোধ ৫ ওহম ও তড়িৎপ্রবাহ ২ অ্যাম্পিয়ার হলে বিভবপ্রভেদ কত?",
            sampleSolutionBengali = """
                **প্রশ্ন:** ওহমের সূত্র ও বিভবপ্রভেদ (V) নির্ণয়।

                **১. ওহমের সূত্র (Ohm's Law):**
                উষ্ণতা এবং অন্যান্য ভৌত অবস্থা অপরিবর্তিত থাকলে, কোনো পরিবাহীর মধ্য দিয়ে প্রবাহিত তড়িৎপ্রবাহ মাত্রা (I) পরিবাহীটির দুই প্রান্তের বিভবপ্রভেদের (V) সমানুপাতিক।
                গাণিতিক রূপ: V = I × R

                **২. প্রদত্ত মান:**
                - পরিবাহীর রোধ (R) = 5 Ω (ওহম)
                - তড়িৎপ্রবাহ মাত্রা (I) = 2 A (অ্যাম্পিয়ার)
                - দুই প্রান্তের বিভবপ্রভেদ (V) = ?

                **৩. ক্যালকুলেশন:**
                আমরা জানি,
                V = I × R
                V = 2 × 5 = 10 Volt (ভোল্ট)

                **৪. চূড়ান্ত উত্তর:**
                পরিবাহীটির দুই প্রান্তের বিভবপ্রভেদ হবে **১০ ভোল্ট (10 V)**।

                **৫. এম রহমান স্যারের টিপ্স:**
                মনে রাখবে: V = I · R, I = V / R, R = V / I। এই ত্রিভুজ সূত্র দিয়ে যেকোনো অংক নিমেষেই করা সম্ভব!
            """.trimIndent(),
            simplerBengali = """
                **সহজ করে বোঝো:**
                ১. ওহমের নিয়ম বলছে— যত বেশি ভোল্টেজ বা ধাক্কা দেবে, তত বেশি বিদ্যুৎ কারেন্ট প্রবাহিত হবে।
                ২. বাধা বা রোধ (Resistance) হলো ৫।
                ৩. কারেন্ট বা বিদ্যুৎ যাচ্ছে ২।
                ৪. ধাক্কা বা ভোল্টেজ = কারেন্ট × বাধা = ২ × ৫ = ১০ ভোল্ট।
                
                উত্তর: ১০ ভোল্ট। একদম সোজা!
            """.trimIndent(),
            englishSolution = """
                **Problem:** State Ohm's Law and calculate the potential difference when resistance R = 5 Ω and current I = 2 A.

                **Ohm's Law Statement:**
                At constant temperature and physical conditions, the current (I) flowing through a conductor is directly proportional to the potential difference (V) across its ends.
                Formula: V = I × R

                **Calculation:**
                Given:
                - Current (I) = 2 A
                - Resistance (R) = 5 Ω
                Potential Difference (V) = I × R = 2 × 5 = 10 Volts.

                **Final Answer:**
                The potential difference is 10 V.
            """.trimIndent()
        ),
        SampleQuestion(
            id = "q3",
            subject = "জ্যামিতি",
            className = "Class 9",
            question = "একটি সমকোণী ত্রিভুজের লম্ব ৪ সেমি এবং ভূমি ৩ সেমি হলে অতিভুজের দৈর্ঘ্য কত?",
            sampleSolutionBengali = """
                **প্রশ্ন:** সমকোণী ত্রিভুজের অতিভুজ নির্ণয়।

                **১. প্রদত্ত উপাত্ত:**
                - সমকোণী ত্রিভুজের লম্ব (p) = 4 cm
                - ভূমি (b) = 3 cm
                - অতিভুজ (h) = ?

                **২. পিথাগোরাসের উপপাদ্য:**
                যেকোনো সমকোণী ত্রিভুজের ক্ষেত্রে:
                অতিভুজ² = লম্ব² + ভূমি²
                বা, h = √(p² + b²)

                **৩. সমাধান ধাপ:**
                h² = 4² + 3²
                h² = 16 + 9 = 25
                h = √25 = 5 cm

                **৪. চূড়ান্ত উত্তর:**
                ত্রিভুজটির অতিভুজের দৈর্ঘ্য **৫ সেমি**।

                **৫. এম রহমান স্যারের শর্টকাট:**
                (৩, ৪, ৫) হলো বিখ্যাত পিথাগোরিয়ান ট্রিপলেট! পরীক্ষায় ৩ আর ৪ দেখলে অতিভুজ সরাসরি ৫ লিখে দিতে পারো।
            """.trimIndent(),
            simplerBengali = """
                **সহজ করে বোঝো:**
                একটি তিনকোনা ছবির একপাশে সোজা খাড়া দাগ ৪ সেমি, আর নিচে শোয়ানো দাগ ৩ সেমি।
                পিথাগোরাস কাকু বলে গেছেন:
                দুটো দাগকে নিজের সাথে গুণ করো:
                ৪ × ৪ = ১৬
                ৩ × ৩ = ৯
                এবার যোগ করো: ১৬ + ৯ = ২৫
                কোন সংখ্যাকে নিজের সাথে গুণ করলে ২৫ হয়? ৫! (৫ × ৫ = ২৫)।
                অতএব ঢালু বাহুর দৈর্ঘ্য ৫ সেমি।
            """.trimIndent(),
            englishSolution = """
                **Problem:** Find the hypotenuse of a right-angled triangle with perpendicular = 4 cm and base = 3 cm.

                **Formula (Pythagoras Theorem):**
                Hypotenuse² = Perpendicular² + Base²
                h = √(p² + b²)

                **Steps:**
                h² = 4² + 3² = 16 + 9 = 25
                h = √25 = 5 cm

                **Final Answer:**
                Hypotenuse is 5 cm (forming the classic 3-4-5 Pythagorean triplet).
            """.trimIndent()
        ),
        SampleQuestion(
            id = "q4",
            subject = "রসায়ন",
            className = "Class 10",
            question = "অ্যাসিড ও ক্ষারের প্রশমন বিক্রিয়া কী? একটি সমীকরণ সহ বুঝিয়ে দাও।",
            sampleSolutionBengali = """
                **প্রশ্ন:** অ্যাসিড ও ক্ষারের প্রশমন বিক্রিয়া (Neutralization Reaction)।

                **১. সংজ্ঞা:**
                যে রাসায়নিক বিক্রিয়ায় একটি অ্যাসিড এবং একটি ক্ষার পরস্পর বিক্রিয়া করে অ্যাসিড ও ক্ষার উভয়ের ধর্ম লোপ পায় এবং নিরপেক্ষ লবণ ও জল উৎপন্ন হয়, তাকে প্রশমন বিক্রিয়া বলে।

                **২. সাধারণ রূপ:**
                অ্যাসিড + ক্ষার → লবণ + জল

                **৩. উদাহরণ ও সমতাবিধান:**
                হাইড্রোক্লোরিক অ্যাসিড (HCl) এবং সোডিয়াম হাইড্রোক্সাইড (NaOH) এর বিক্রিয়া:
                HCl + NaOH → NaCl + H₂O
                এখানে:
                - HCl = অ্যাসিড
                - NaOH = ক্ষার
                - NaCl = সাধারণ খাবার লবণ (সোডিয়াম ক্লোরাইড)
                - H₂O = জল

                **৪. চূড়ান্ত সিদ্ধান্ত:**
                এই বিক্রিয়ায় উৎপন্ন দ্রবণের pH সাধারণত ৭ এর কাছাকাছি (প্রশম) থাকে।
            """.trimIndent(),
            simplerBengali = """
                **সহজ ভাষায়:**
                অ্যাসিড হলো যেমন লেবুর টক বা তেজালো রস, আর ক্ষার হলো সাবানের মতো জিনিস।
                দুটোকে একসাথে মেশালে অ্যাসিডের তেজও নষ্ট হয়ে যায়, ক্ষারের তেজও নষ্ট হয়ে যায়!
                তৈরি হয় সাধারণ লবণ আর মিষ্টি জল।
                যেমন: এসিড (HCl) + ক্ষার (NaOH) = খাবার নুন (NaCl) + জল (H2O)।
            """.trimIndent(),
            englishSolution = """
                **Problem:** Explain Neutralization Reaction with a chemical equation.

                **Definition:**
                A neutralization reaction is a chemical reaction in which an acid and a base react quantitatively with each other to form salt and water, neutralizing both their properties.

                **General Equation:**
                Acid + Base → Salt + Water

                **Example:**
                HCl (Hydrochloric Acid) + NaOH (Sodium Hydroxide) → NaCl (Sodium Chloride) + H₂O (Water)
            """.trimIndent()
        )
    )
}
