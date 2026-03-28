package com.tech.cursor.presentation.common

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.tech.cursor.presentation.common.models.Connection
import com.tech.cursor.presentation.common.models.OnlineStatus
import com.tech.cursor.presentation.common.models.TradeStatus
import com.tech.cursor.presentation.common.models.UserProfile

val kashmirCards = listOf(
    SkillCard(
        id = "1", initial = "A", name = "Aadil Mir", age = 24,
        title = "Graphic Designer",
        location = "Baramulla", district = "Baramulla", distanceKm = 1,
        bio = "\"Design is my language — logos, brand kits, social media. Looking to learn coding so I can build my own portfolio.\"",
        offers = listOf("Android", "Kotlin"),
        wants  = listOf("Sales", "Finance"),
        matchPercent = 96, trades = 7, rating = 4.9f,
        responseRate = 92, experienceYears = 3, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1A0A28), Color(0xFF8B1A3D), Color(0xFFC0302A))),
        avatarColor  = Color(0x598B1A3D)
    ),
    SkillCard(
        id = "2", initial = "Z", name = "Zubair Khan", age = 28,
        title = "Full-Stack Developer",
        location = "Baramulla", district = "Baramulla", distanceKm = 2,
        bio = "\"I build web apps for local businesses. My code is clean but my designs are tragic — will trade backend skills for UI magic.\"",
        offers = listOf("Node.js", "React", "MongoDB", "REST API"),
        wants  = listOf("UI/UX Design", "Figma", "Branding"),
        matchPercent = 91, trades = 14, rating = 4.8f,
        responseRate = 88, experienceYears = 6, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF0C2318), Color(0xFF1A7A4A), Color(0xFF0F4D2B))),
        avatarColor  = Color(0x591A7A4A)
    ),
    SkillCard(
        id = "3", initial = "S", name = "Saba Wani", age = 22,
        title = "Content Creator & Writer",
        location = "Anantnag", district = "Baramulla", distanceKm = 3,
        bio = "\"I write SEO blogs, scripts and social captions. Want to learn video editing to level up my content game.\"",
        offers = listOf("Blog Writing", "SEO Content", "Script Writing", "Copywriting"),
        wants  = listOf("Video Editing", "After Effects", "Reels Editing"),
        matchPercent = 83, trades = 5, rating = 4.7f,
        responseRate = 95, experienceYears = 2, isVerified = false,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1C0E30), Color(0xFF6B2FA0), Color(0xFF4A1A7A))),
        avatarColor  = Color(0x596B2FA0)
    ),
    SkillCard(
        id = "4", initial = "F", name = "Farhan Lone", age = 19,
        title = "Drone Photographer",
        location = "Gulmarg", district = "Baramulla", distanceKm = 5,
        bio = "\"I capture Kashmir from the sky — weddings, tourism, real estate. Looking to trade aerial footage for digital marketing skills.\"",
        offers = listOf("Drone Photography", "Video Production", "Aerial Shots", "4K Footage"),
        wants  = listOf("Digital Marketing", "Instagram Ads", "SEO"),
        matchPercent = 79, trades = 21, rating = 5.0f,
        responseRate = 97, experienceYears = 7, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF0A1A2E), Color(0xFF1A5C8B), Color(0xFF0D3A5C))),
        avatarColor  = Color(0x591A5C8B)
    ),
    SkillCard(
        id = "5", initial = "N", name = "Nida Bhat", age = 19,
        title = "Yoga & Wellness Coach",
        location = "Pulwama", district = "Pulwama", distanceKm = 35,
        bio = "\"Teaching yoga, meditation and breathwork online. Want someone to build me a proper website — will trade wellness sessions!\"",
        offers = listOf("Yoga Coaching", "Meditation", "Nutrition Tips", "Live Sessions"),
        wants  = listOf("Web Design", "WordPress", "Webflow"),
        matchPercent = 74, trades = 9, rating = 4.6f,
        responseRate = 90, experienceYears = 4, isVerified = false,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1A1008), Color(0xFF7A4A1A), Color(0xFF5C3512))),
        avatarColor  = Color(0x597A4A1A)
    ),
    SkillCard(
        id = "6", initial = "B", name = "Bilal Dar", age = 25,
        title = "Mobile App Developer",
        location = "Shopian", district = "Shopian", distanceKm = 52,
        bio = "\"Building Flutter apps for local startups. Need someone to handle my social media presence — code I have, clout I don't.\"",
        offers = listOf("Flutter", "Dart", "Firebase", "Android/iOS"),
        wants  = listOf("Social Media Mgmt", "Content Creation", "Reels"),
        matchPercent = 88, trades = 11, rating = 4.8f,
        responseRate = 85, experienceYears = 4, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF0D1A1C), Color(0xFF1A6B70), Color(0xFF0F4A4D))),
        avatarColor  = Color(0x591A6B70)
    ),
    SkillCard(
        id = "7", initial = "H", name = "Huma Qadri", age = 23,
        title = "Digital Illustrator",
        location = "Budgam", district = "Budgam", distanceKm = 15,
        bio = "\"I paint digital art inspired by Kashmiri culture. Want to learn animation to bring my characters to life — let's collab!\"",
        offers = listOf("Digital Art", "Illustration", "Procreate", "Character Design"),
        wants  = listOf("2D Animation", "After Effects", "Lottie"),
        matchPercent = 85, trades = 3, rating = 4.9f,
        responseRate = 93, experienceYears = 2, isVerified = false,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1A0A1A), Color(0xFF7A1A8B), Color(0xFF5C0D6B))),
        avatarColor  = Color(0x597A1A8B)
    ),
    SkillCard(
        id = "8", initial = "T", name = "Tariq Sofi", age = 34,
        title = "Accountant & Tax Consultant",
        location = "Kupwara", district = "Kupwara", distanceKm = 98,
        bio = "\"Handling accounts, GST filings and audits for 12 years. Want to upskill in Excel automation and learn Python for finance.\"",
        offers = listOf("Accounting", "Tax Filing", "GST", "Tally ERP"),
        wants  = listOf("Python", "Excel VBA", "Power BI"),
        matchPercent = 70, trades = 18, rating = 4.7f,
        responseRate = 80, experienceYears = 12, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF121A0A), Color(0xFF4A6B1A), Color(0xFF2E4D0D))),
        avatarColor  = Color(0x594A6B1A)
    ),
    SkillCard(
        id = "9", initial = "R", name = "Ruqaiya Shah", age = 27,
        title = "Pashmina Artisan",
        location = "Ganderbal", district = "Ganderbal", distanceKm = 22,
        bio = "\"My family has crafted Pashmina for generations. I need help taking our products online — will teach you authentic Kashmiri craft!\"",
        offers = listOf("Pashmina Crafting", "Embroidery", "Carpet Weaving", "Traditional Art"),
        wants  = listOf("E-Commerce", "Shopify", "Product Photography"),
        matchPercent = 77, trades = 6, rating = 4.8f,
        responseRate = 88, experienceYears = 8, isVerified = true,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1A0A0A), Color(0xFF8B3A1A), Color(0xFF6B2510))),
        avatarColor  = Color(0x598B3A1A)
    ),
    SkillCard(
        id = "10", initial = "O", name = "Owais Beg", age = 29,
        title = "Music Producer",
        location = "Kulgam", district = "Kulgam", distanceKm = 75,
        bio = "\"I produce Sufi-fusion beats blending Kashmiri music with modern production. Need help with music marketing & distribution.\"",
        offers = listOf("Music Production", "Mixing", "FL Studio"),
        wants  = listOf("Music Marketing", "Spotify Pitching", "YouTube SEO"),
        matchPercent = 82, trades = 8, rating = 4.9f,
        responseRate = 91, experienceYears = 5, isVerified = false,
        cardGradient = Brush.linearGradient(listOf(Color(0xFF1A0D0A), Color(0xFF8B4A1A), Color(0xFF6B3510))),
        avatarColor  = Color(0x598B4A1A)
    )
)


val sampleConnections = listOf(
    Connection(
        id = "1", initial = "A", name = "Aadil Mir",
        title = "Graphic Designer", district = "Baramulla", distanceKm = 1,
        avatarColor = Color(0xFF6B1A3D), status = OnlineStatus.ONLINE,
        isVerified = true, isNewMatch = true,
        offersSkill = "Logo Design", wantsSkill = "HTML/CSS",
        lastMessage = "Bhai send kar dena woh Figma file, kab tak start karein?",
        lastTime = "2m ago", unreadCount = 3, trades = 2, rating = 4.9f,
        tradeStatus = TradeStatus.ACTIVE
    ),
    Connection(
        id = "2", initial = "Z", name = "Zubair Khan",
        title = "Full-Stack Developer", district = "Baramulla", distanceKm = 55,
        avatarColor = Color(0xFF1A5C3A), status = OnlineStatus.ONLINE,
        isVerified = true, isNewMatch = true,
        offersSkill = "Node.js", wantsSkill = "UI Design",
        lastMessage = "API docs share kiye maine. Check karo aur batao koi issue ho to.",
        lastTime = "18m ago", unreadCount = 1, trades = 5, rating = 4.8f,
        tradeStatus = TradeStatus.ACTIVE
    ),
    Connection(
        id = "3", initial = "F", name = "Farhan Lone",
        title = "Drone Photographer", district = "Baramulla", distanceKm = 62,
        avatarColor = Color(0xFF1A4A7A), status = OnlineStatus.ONLINE,
        isVerified = true, isNewMatch = false,
        offersSkill = "Drone Footage", wantsSkill = "SEO",
        lastMessage = "4K footage ready hai. Google Drive link bhejta hoon kal.",
        lastTime = "1h ago", unreadCount = 0, trades = 8, rating = 5.0f,
        tradeStatus = TradeStatus.COMPLETED
    ),
    Connection(
        id = "4", initial = "S", name = "Saba Wani",
        title = "Content Creator", district = "Baramulla", distanceKm = 60,
        avatarColor = Color(0xFF4A1A7A), status = OnlineStatus.AWAY,
        isVerified = false, isNewMatch = false,
        offersSkill = "Blog Writing", wantsSkill = "Video Editing",
        lastMessage = "Blog draft tayar ho gaya! Review karo please 🙏",
        lastTime = "3h ago", unreadCount = 2, trades = 3, rating = 4.7f,
        tradeStatus = TradeStatus.ACTIVE
    ),
    Connection(
        id = "5", initial = "B", name = "Bilal Dar",
        title = "Flutter Developer", district = "Shopian", distanceKm = 52,
        avatarColor = Color(0xFF1A5C60), status = OnlineStatus.AWAY,
        isVerified = true, isNewMatch = false,
        offersSkill = "Flutter App", wantsSkill = "Reels Editing",
        lastMessage = "App ka beta build ready hai. Testing kar lo ek baar.",
        lastTime = "5h ago", unreadCount = 0, trades = 4, rating = 4.8f,
        tradeStatus = TradeStatus.COMPLETED
    ),
    Connection(
        id = "6", initial = "H", name = "Huma Qadri",
        title = "Digital Illustrator", district = "Budgam", distanceKm = 15,
        avatarColor = Color(0xFF5C1A7A), status = OnlineStatus.OFFLINE,
        isVerified = false, isNewMatch = false,
        offersSkill = "Procreate Art", wantsSkill = "After Effects",
        lastMessage = "Characters ka final set share kiya. Pasand aya?",
        lastTime = "Yesterday", unreadCount = 0, trades = 1, rating = 4.9f,
        tradeStatus = TradeStatus.COMPLETED
    ),
    Connection(
        id = "7", initial = "N", name = "Nida Bhat",
        title = "Yoga Coach", district = "Pulwama", distanceKm = 35,
        avatarColor = Color(0xFF6B3D12), status = OnlineStatus.OFFLINE,
        isVerified = false, isNewMatch = false,
        offersSkill = "Yoga Sessions", wantsSkill = "Webflow",
        lastMessage = "5 free sessions ready hain jab aap website complete karo.",
        lastTime = "2 days ago", unreadCount = 0, trades = 2, rating = 4.6f,
        tradeStatus = TradeStatus.ACTIVE
    ),
    Connection(
        id = "8", initial = "R", name = "Ruqaiya Shah",
        title = "Pashmina Artisan", district = "Baramulla", distanceKm = 22,
        avatarColor = Color(0xFF7A2A12), status = OnlineStatus.OFFLINE,
        isVerified = true, isNewMatch = false,
        offersSkill = "Pashmina Craft", wantsSkill = "Shopify Setup",
        lastMessage = "Store launch ho gaya! Shukriya itni madad ke liye bhai.",
        lastTime = "3 days ago", unreadCount = 0, trades = 3, rating = 4.8f,
        tradeStatus = TradeStatus.COMPLETED
    ),
    Connection(
        id = "9", initial = "O", name = "Owais Beg",
        title = "Music Producer", district = "Kulgam", distanceKm = 75,
        avatarColor = Color(0xFF7A3A12), status = OnlineStatus.OFFLINE,
        isVerified = false, isNewMatch = false,
        offersSkill = "Beat Production", wantsSkill = "YouTube SEO",
        lastMessage = "Track upload hogaya Spotify pe. Sun ke batao!",
        lastTime = "1 week ago", unreadCount = 0, trades = 6, rating = 4.9f,
        tradeStatus = TradeStatus.COMPLETED
    ),
)

val dummyProfile = UserProfile(
    id = "me",
    initial = "I",
    name = "Muhasib Hafeez",
    title = "Android Developer & Kotlin",
    district = "Baramulla",
    memberSince = "Jan 2024",
    isVerified = true,
    avatarColor = Color(0xFF6B1A3D),
    connects = 9,
    trades = 14,
    rating = 4.9f,
    responseRate = 97,
    offersSkills = listOf("Android Developer", "Kotlin",),
    wantsSkills = listOf("Marketing", "Finance")
)