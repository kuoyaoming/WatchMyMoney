# WatchMyMoney 💸

> **Visualize your income in real-time on Wear OS.**
> A complication that tracks your daily earnings, and an app that makes watching it grow satisfying.

![Platform](https://img.shields.io/badge/Platform-Wear%20OS-4285F4?style=flat&logo=android)
![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat&logo=kotlin)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📖 Overview

**WatchMyMoney** is a dual-component application designed for Wear OS 4/5. It consists of a battery-efficient **Complication** for your watch face and a high-performance **Standalone App** for visual gratification.

*   **The Complication**: Subtly tracks your daily accumulated income on your watch face. Updates once per minute to preserve battery.
*   **The App**: When you tap the complication, the app launches a 60fps, high-precision counter that shows your money growing in real-time down to the decimal.

## ✨ Features

### ⌚ Smart Complication
*   **Battery Efficient**: Updates passively (approx. once/min).
*   **Flexible Layouts**: Supports `SHORT_TEXT` (Circle), `LONG_TEXT` (Wide), and `RANGED_VALUE` (Progress Bar).
*   **Instant Access**: Tap to launch the full experience.

### 🚀 Visual Gratification App
*   **60Hz Real-Time Rendering**: Smooth, high-precision animation.
*   **Decimal Precision**: Watch fractions of a cent accumulate (e.g., `$120.5439`).
*   **Smart Lifecycle**: Animation freezes immediately when you lower your wrist to save power.

### ⚙️ User Friendly
*   **Easy Setup**: On-watch number pad for easy salary input.
*   **Customizable**: Set your annual salary, currency symbol, and daily reset time.

---

## 🛠️ Technical Architecture

This project is built with modern Android development standards for Wear OS.

| Component | Tech Stack |
| :--- | :--- |
| **Language** | 100% Kotlin |
| **UI Framework** | Jetpack Compose for Wear OS |
| **Complication API** | `androidx.wear.watchface:watchface-complications-data-source-ktx` |
| **Data Storage** | Jetpack DataStore (Preferences) |
| **Architecture** | MVVM (Model-View-ViewModel) |

### Core Logic
The app calculates earnings based on the current time elapsed since midnight.

```kotlin
// Daily Salary = Annual Salary / 365.25
// Rate Per Millisecond = Daily Salary / 86,400,000
val msPassedToday = System.currentTimeMillis() - midnightTimestamp
val earned = msPassedToday * ratePerMillisecond
```

### Data Flow
1.  **DataStore**: Persists `salary` and `currency` settings.
2.  **ComplicationService**: Reads DataStore → Calculates value → Pushes update to Watch Face.
3.  **MainActivity**: Reads DataStore → Runs 60Hz Loop → Renders UI.

---

## 🚀 Getting Started

### Prerequisites
*   Android Studio Koala or newer.
*   Android SDK API 33+ (Wear OS 4).

### Installation
1.  Clone the repository.
2.  Open in Android Studio.
3.  Select the `wear` configuration.
4.  Run on a Wear OS emulator or physical device.

### Configuration
1.  Launch the app or add the complication to your watch face.
2.  If no salary is set, the setup screen will appear automatically.
3.  Enter your **Annual Salary**.
4.  Enjoy watching your money grow!

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.