package com.example.data

data class TuitionService(
    val id: String,
    val titleBengali: String,
    val titleEnglish: String,
    val subtitleBengali: String,
    val descriptionBengali: String,
    val highlights: List<String>,
    val badge: String,
    val iconType: String // e.g. "school", "ai", "notes", "online"
)

object CoachingData {
    const val TEACHER_NAME = "M Rahman"
    const val INSTITUTE_NAME = "M Rahman AI Tuition"
    const val SUBTITLE_TEXT = "AI Hub And Knowledge | Kaliachak, Malda | Call: 9851374847"
    const val PHONE_NUMBER = "9851374847"
    const val WHATSAPP_NUMBER = "919851374847"
    const val LOCATION_TEXT = "কালিয়াচক, মালদা, পশ্চিমবঙ্গ"
    const val FOOTER_TEXT = "Made by M Rahman | AI Hub And Knowledge And Education"

    val services = listOf(
        TuitionService(
            id = "tuition_5_12",
            titleBengali = "ক্লাস ৫-১২ টিউশন",
            titleEnglish = "Class 5-12 Tuition",
            subtitleBengali = "গণিত ও বিজ্ঞান স্পেশাল কোচিং (WBBSE ও CBSE)",
            descriptionBengali = "৫ম থেকে ১২শ শ্রেণীর ছাত্র-ছাত্রীদের জন্য বিশেষ নিবিড় পাঠদান। প্রতিটি বিষয়ের মূল ভিত্তি মজবুত করা, নিয়মিত মক টেস্ট এবং দুর্বল শিক্ষার্থীদের জন্য অতিরিক্ত কেয়ার।",
            highlights = listOf(
                "গণিত, পদার্থবিজ্ঞান, রসায়ন ও জীববিদ্যা",
                "ছোট ব্যাচে ব্যক্তিগত নজরদারি",
                "সাপ্তাহিক মূল্যায়ন পরীক্ষা ও প্রগ্রেস রিপোর্ট",
                "বোর্ড পরীক্ষার শতভাগ নিশ্চিত প্রস্তুতি"
            ),
            badge = "জনপ্রিয় ব্যাচ",
            iconType = "school"
        ),
        TuitionService(
            id = "ai_education",
            titleBengali = "AI শিক্ষা ও কোডিং",
            titleEnglish = "AI Education",
            subtitleBengali = "স্কুল স্তরে আধুনিক কৃত্রিম বুদ্ধিমত্তা ও প্রযুক্তি",
            descriptionBengali = "আগামীর বিশ্বের জন্য শিক্ষার্থীদের প্রস্তুত করতে এআই টুলস, কোডিং বেসিকস, রোবোটিক্স ধারণা এবং লজিক্যাল থিংকিং এর ওপর ব্যবহারিক আধুনিক কর্মশালা।",
            highlights = listOf(
                "Artificial Intelligence (AI) এর বাস্তব প্রয়োগ",
                "স্কুল শিক্ষার্থীদের উপযোগী সহজ কোডিং",
                "স্মার্ট প্রবলেম সলভিং ও ক্রিয়েটিভ প্রজেক্ট",
                "সার্টিফিকেট ও প্রজেক্ট প্রদর্শনী"
            ),
            badge = "ভবিষ্যতের শিক্ষা",
            iconType = "ai"
        ),
        TuitionService(
            id = "notes_materials",
            titleBengali = "নোটস ও সাজেশন",
            titleEnglish = "Notes & Study Material",
            subtitleBengali = "অধ্যায়ভিত্তিক সাজানো হ্যান্ডরিটেন ও ডিজিটাল নোটস",
            descriptionBengali = "সহজ ও প্রাঞ্জল বাংলা ভাষায় তৈরি উচ্চমানের নোটস, সূত্রাবলী সংকলন, অধ্যায়ভিত্তিক প্রশ্নব্যাংক এবং ফাইনাল পরীক্ষার স্পেশাল কমন উপযোগী সাজেশন।",
            highlights = listOf(
                "অধ্যায়ভিত্তিক ফর্মুলা ও শর্টকাট শিট",
                "বিগত ১০ বছরের বোর্ড প্রশ্নের সমাধান",
                "অধ্যায়ভিত্তিক প্র্যাকটিস বুকলেট",
                "পরীক্ষার আগে এক্সক্লুসিভ লাস্ট মিনিট সাজেশন"
            ),
            badge = "কমন উপযোগী",
            iconType = "notes"
        ),
        TuitionService(
            id = "online_class",
            titleBengali = "অনলাইন ক্লাস",
            titleEnglish = "Online Class",
            subtitleBengali = "স্মার্ট ডিজিটাল বোর্ড সহ লাইভ ইন্টারেক্টিভ ক্লাস",
            descriptionBengali = "যে কোনো স্থান থেকে সরাসরি স্যারের সাথে যুক্ত হয়ে লাইভ ক্লাসে অংশ নেওয়ার সুবিধা। মিস হয়ে যাওয়া ক্লাসের ফুল এইচডি রেকর্ডিং ও তাৎক্ষণিক ডাউট ক্লিয়ারিং।",
            highlights = listOf(
                "স্মার্ট বোর্ডে লাইভ ক্লাস ও স্ক্রিন শেয়ারিং",
                "প্রতিটি ক্লাসের এইচডি রেকর্ডিং ব্যাকআপ",
                "ক্লাসের সাথে সাথে পিডিএফ লেকচার নোট",
                "হোয়াটসঅ্যাপ ও গুগল মিটে সরাসরি ডাউট সলভ"
            ),
            badge = "বাড়ি বসেই ক্লাস",
            iconType = "online"
        )
    )
}
