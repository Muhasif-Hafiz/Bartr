# Bartr

**A skill exchange platform built for Kashmir.**

<img width="117" height="250" alt="Simulator Screenshot - iPhone 16e - 2026-03-29 at 02 02 23" src="https://github.com/user-attachments/assets/43e0cdf4-2ba6-42c4-a597-164763fc2d72" />


<img width="117" height="250" alt="Simulator Screenshot - iPhone 16e - 2026-03-29 at 02 01 00" src="https://github.com/user-attachments/assets/d47f515f-869b-4177-9c71-f56202ea09b3" />

<img width="117" height="250" alt="Simulator Screenshot - iPhone 16e - 2026-03-29 at 02 03 29" src="https://github.com/user-attachments/assets/2f290bc0-48c3-4042-badd-4a4f4f852cd8" />
<img width="117" height="250" alt="Simulator Screenshot - iPhone 16e - 2026-03-29 at 02 03 45" src="https://github.com/user-attachments/assets/38a27aeb-9fd7-4ad1-8fb7-b82487639e52" />

<img width="117" height="250" alt="Simulator Screenshot - iPhone 16e - 2026-03-29 at 02 03 53" src="https://github.com/user-attachments/assets/d18fa14f-8137-4bcf-9088-261326af9b56" />





Bartr connects people across Kashmir's districts who want to trade skills — not money. A graphic designer in Srinagar trades branding work with a developer in Baramulla. A yoga coach in Pulwama gets her website built in exchange for wellness sessions. A drone photographer in Gulmarg teaches aerial footage in return for digital marketing. No cash changes hands. Skills do.

---

## The Problem

Kashmir has an abundance of talent — designers, developers, content creators, craftspeople, teachers, musicians — but access to services and professional growth is limited by financial barriers, geography, and a lack of structured platforms to connect people. Freelancing platforms built for global markets don't work for a local economy where trust, community, and proximity matter more than ratings and dollar figures.

People with skills can't always afford the services they need. People who need services can't always pay for them. The result is that talent sits idle, growth stalls, and potential collaborations never happen.

---

## The Solution

Bartr is a mobile application where people list the skills they have and the skills they want. The app surfaces relevant matches, facilitates introductions, and provides the structure for skill trades to happen — with transparency, trust signals, and a feedback system built in.

Think of it as a barter economy, rebuilt for the digital age, starting in Kashmir.

---

## How It Works

1. You create a profile listing what you offer and what you want in return.
2. Bartr shows you people in nearby districts whose skills match what you're looking for, and who want what you have.
3. You swipe, connect, and agree on a trade.
4. Both parties complete the trade, rate each other, and build their reputation on the platform.

Every connection on Bartr is a two-way street. There are no service fees, no payment gateways, no middlemen.

---

## Who It's For

Bartr is designed for people who are building their careers, side projects, or small businesses without the capital to hire everyone they need. Specifically:

- Freelancers who want to grow without spending money on services
- Students and recent graduates building portfolios
- Small business owners in districts like Anantnag, Kupwara, Shopian, Budgam who need digital skills but can't afford agencies
- Traditional artisans — weavers, craftspeople, Pashmina makers — who need to go online
- Creative professionals who want collaboration, not just clients

---

## Features

**Swipe-based matching** — A card deck interface shows you people whose skill profile matches yours. Swipe right to connect, left to pass. The match percentage is calculated from how well your offered skills overlap with their wants, and vice versa.

**District-based proximity** — Every profile shows the person's district and approximate distance. Bartr surfaces local matches first because trust is built through proximity in Kashmir's social fabric.

**Skill portfolios** — Each profile lists offered skills and wanted skills with a simple pill-based UI. No lengthy CVs. No cover letters.

**Trade history and ratings** — Every completed trade is recorded. Ratings are mutual. Over time, users build a reputation that signals reliability to future trade partners.

**Connection inbox** — A dedicated screen shows all active connections with trade status, last message, and quick actions — message, propose a trade, or view their profile.

**Verified profiles** — Users can verify their identity and skill credentials. Verified badges increase trust and match rates.

**Real-time status** — See who is online, who is away, and how responsive a person has been to past trade requests.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Android | Kotlin, Jetpack Compose |
| Architecture | MVVM, StateFlow, ViewModel |
| Navigation | Jetpack Navigation Component |
| State management | Kotlin Coroutines, StateFlow |
| UI | Material 3, custom design system |
| Local data | Room (planned) |
| Backend | Firebase / Supabase (planned) |
| Auth | Firebase Auth (planned) |

---

## Project Structure

```
app/
└── src/main/java/com/tech/cursor/
    └── presentation/
        ├── Splash/
        │   └── SplashScreen.kt
        ├── Home/
        │   ├── HomeScreen.kt
        │   ├── HomeViewModel.kt
        │   ├── MainScreen.kt
        │   ├── SkillCard.kt
        │   └── SampleData.kt
        ├── Connect/
        │   ├── ConnectScreen.kt
        │   ├── ConnectViewModel.kt
        │   └── ConnectModels.kt
        └── Profile/
            ├── ProfileScreen.kt
            ├── ProfileViewModel.kt
            └── ProfileModels.kt
```

---

## Screens

### Splash
Animated entry screen with the Bartr wordmark, floating skill pills, particle field, and a loading progress indicator. Built entirely in Compose with keyframe and spring animations.

### Home — Swipe Deck
The core experience. A Tinder-style card stack shows profiles of people in Kashmir with matching skill trades. Drag right to connect, drag left to pass. Cards show match percentage, offered skills, wanted skills, location, trade history, rating, and response rate. A match overlay fires when both parties connect.

### Connect — My Connections
A list of all established connections sorted by recency. Each row shows trade status (active or completed), last message preview, unread count, online status, and quick action buttons. Filterable by online status, new matches, active trades, and completed trades.

### Profile
The user's own profile — skill portfolio, stats, account settings, preferences with live toggles, and support links. Includes a logout confirmation sheet.

---

## Design System

All screens share a single dark theme with the following tokens:

```
Background:   #0A0A0D
Flame:        #FD5558
Orange:       #FF9A47
Gradient:     linear(#FD5558 → #FF9A47)
Green:        #2ECC71
Surface:      rgba(255,255,255,0.05)
Border:       rgba(255,255,255,0.09)
```

Typography uses SF Pro Display on iOS-adjacent rendering, with weight 800 for names, 700 for labels, and 500 for body. All interactive elements use a 28dp corner radius at the card level and 14dp at the component level.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Kotlin 1.9+
- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34

### Build

```bash
git clone https://github.com/your-username/bartr.git
cd bartr
# Open in Android Studio and run on device or emulator
```

### Dependencies

Add to your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material3:material3:1.2.1")
}
```

---

## Roadmap

**v1.0 — Current**
- Swipe matching with local Kashmir profiles
- Connect screen with trade status
- Profile screen with skill portfolio
- Animated splash screen

**v1.1 — In Progress**
- Firebase authentication (phone number OTP)
- Firestore backend for real profiles
- Real-time messaging between connections
- Push notifications for new matches and messages

**v1.2 — Planned**
- Skill verification via certificate upload
- Trade contract system — both parties formally agree before a trade starts
- Review and rating system post-trade
- District-based discovery radius filter

**v2.0 — Vision**
- Group trades — three or more people in a skill chain
- Bartr credits — a points system for unequal trades
- Skill workshops — people offering group sessions to multiple learners
- Expand beyond Kashmir to other parts of J&K and eventually to other regional markets

---

## The Bigger Picture

Bartr is not just an app. It is an attempt to build economic infrastructure for a community that has historically been underserved by mainstream platforms. The goal is to prove that a local, trust-first, community-driven model can enable genuine economic activity without requiring capital to change hands.

If a weaver in Ganderbal can get her products on Shopify by trading her craft knowledge with a developer in Budgam — and both of them walk away with something they couldn't afford before — then Bartr has done its job.

Kashmir has the talent. Bartr connects it.

---

## Contributing

Contributions are welcome. If you are based in Kashmir and want to help build this, that is especially appreciated — local perspective makes better product decisions.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit with clear messages: `git commit -m "Add: real-time connection status"`
4. Open a pull request with context on what you changed and why

Please keep pull requests focused. One feature or fix per PR.

---

## License

MIT License. See `LICENSE` for details.

---

## Contact

Built in Kashmir, for Kashmir.

For questions, partnership inquiries, or feedback — open an issue or reach out directly through the repository.

---

*Bartr is currently in active development. The sample data uses fictional profiles across Srinagar, Baramulla, Anantnag, Pulwama, Shopian, Budgam, Kupwara, Ganderbal, and Kulgam. Real user onboarding is planned for v1.1.*
