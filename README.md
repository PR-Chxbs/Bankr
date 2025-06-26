# 💰 Bankr - Personal Budget Tracker

![Android](https://img.shields.io/badge/platform-Android-blue?logo=android)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple?logo=jetpackcompose)
![Hilt](https://img.shields.io/badge/DI-Hilt-25a162?logo=dagger)
![License](https://img.shields.io/badge/license-MIT-green)
![Room](https://img.shields.io/badge/Database-Room-orange)

Bankr is a personal budgeting Android application built with **Jetpack Compose** and **Material 3**. It helps users track income and expenses, set monthly budget goals, visualize their spending, and earn badges through gamified budgeting habits.

---

## 📲 Features

- 🔐 **User Authentication** (Login/Register)
- 💸 **Add & View Transactions**
- 🗃️ **Manage Accounts and Categories**
- 🎯 **Monthly Budget Goals**
- 📈 **Analytics Dashboard**
- 🏆 **Gamification** with Badge Rewards
- 📅 **Date Filtering** and Pickers
- 🪄 **Modern UI** using Material 3 + Compose
- 💾 **Local Storage** with Room DB
- ⚙️ **Dependency Injection** via Hilt

---

## 🧠 Architecture

This app follows the **MVVM** (Model-View-ViewModel) architecture pattern:

UI (Jetpack Compose)
↑
ViewModels (State + Business Logic)
↑
Repositories (Abstract Data Access)
↑
DAOs (Room DB Access)

---

## 🗃️ Database Schema

The app uses **Room** as its local database. Key entities:

- **User**: `user_id`, `username`, `password`
- **Transaction**: `transaction_id`, `user_id`, `amount`, `description`, `type`, `account_id`, `category_id`, `date`, `receipt_image_path`
- **Account**: `account_id`, `user_id`, `name`, `balance`
- **Category**: `category_id`, `user_id`, `name`, `iconKey`, `monthly_limit`
- **BudgetGoal**: `goal_id`, `user_id`, `name`, `description`, `month`, `year`, `total_budget`
- **Badge**: `badge_id`, `user_id`, `name`, `description`, `date_earned`

---

## 🚀 Tech Stack

| Technology        | Usage                     |
|-------------------|----------------------------|
| Jetpack Compose   | Declarative UI             |
| Material 3        | Modern design system       |
| Room              | Local persistence          |
| Hilt              | Dependency injection       |
| Kotlin Coroutines | Asynchronous operations    |
| Navigation        | Compose Navigation         |
| StateFlow         | Reactive data flow         |

---

---

## 🛠️ Getting Started

### 📦 Requirements

- Android Studio Giraffe or newer
- Kotlin 1.9+
- Gradle 8+
- minSdk: 24
- targetSdk: 34

### 🔧 Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/PR-Chxbs/Bankr
   cd Bankr

Open in Android Studio

Sync Gradle & Run the app on a device or emulator.

📌 Key Modules
data.local.dao: DAOs for Room

data.local.entities: Data models (Room entities)

data.repository: App-level data abstraction

ui.screens: Composable screens

navigation: Compose navigation structure

di: Hilt DI modules

🏅 Gamification
Bankr encourages budgeting with badges that are awarded for:

Setting a budget

Staying within budget

Daily streaks

Low expense categories

Long-term consistency

Badges are viewable on the Badges screen 🎖️

🌍 Currency Support
Future Feature:

💱 Support for currency conversion using an external API (e.g. ExchangeRateHost, OpenExchangeRates)

🙌 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change or add.

📝 License
This project is licensed under the MIT License.
See the LICENSE file for details.

📬 Contact
Made by Wahina Prince Chabalala, Ororiseng Seshoka, Omolemo Ramalekana
📧 wp.chabalala@gmail.com
🌍 Based in Pretoria, South Africa

“A budget is telling your money where to go instead of wondering where it went.” — Dave Ramsey