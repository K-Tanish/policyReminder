# Policy Reminder - Insurance Agent CRM

An Android mobile application designed for insurance agents to manage customer relationships, track policies, and handle renewal reminders efficiently.

## Project Overview

Policy Reminder is a comprehensive CRM solution that helps insurance agents:
- Manage customer information and family details
- Track multiple insurance policies per customer
- Receive automated renewal reminders
- Remember customer birthdays and anniversaries
- Communicate via WhatsApp with customizable templates
- Log call history and follow-ups
- Maintain renewal history

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Async Operations**: Kotlin Coroutines & Flow
- **Background Tasks**: WorkManager
- **Navigation**: Jetpack Navigation Compose
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Project Structure

```
app/
├── src/main/java/com/policyreminder/app/
│   ├── data/
│   │   ├── model/          # Data entities
│   │   ├── dao/            # Room DAOs
│   │   ├── database/       # Database configuration
│   │   └── repository/     # Data repositories
│   ├── ui/
│   │   ├── components/     # Reusable UI components
│   │   ├── screens/        # App screens
│   │   ├── navigation/     # Navigation setup
│   │   ├── theme/          # App theming
│   │   └── viewmodel/      # ViewModels
│   ├── MainActivity.kt
│   └── PolicyReminderApplication.kt
└── src/main/res/
    ├── values/             # Strings, colors, themes
    └── xml/                # Configuration files
```

## Key Features Implemented

### ✅ Completed (100%)
1. **Project Structure** - Complete Gradle setup with all dependencies
2. **Database Layer** - Room database with 7 entities and DAOs
3. **Repository Pattern** - Data access abstraction
4. **ViewModels** - State management for Home, Customers, Policies, Reminders
5. **UI Theme** - Monochromatic design system (#222223 & #E7E5D7)
6. **Reusable Components** - Cards, buttons, and list items
7. **Homepage UI** - Fully implemented with all sections
8. **Navigation** - Bottom navigation with screen routing
9. **Customer Screens** - List, add, and detail screens with full CRUD
10. **Policy Screens** - List, add, and detail screens with filtering and status management
11. **Reminders Screen** - Type filtering and reminder list display
12. **Settings Screen** - Organized settings with multiple sections
13. **WhatsApp Integration** - Message templates with dynamic placeholders, preview and send functionality
14. **WorkManager** - Background reminder scheduling (daily checks at 9 AM)
15. **Notification System** - Rich notifications with Call and WhatsApp actions

### 🎉 Production Ready
All core features implemented and tested. The app is ready for deployment!

## Database Schema

### Entities
- **Customer** - Customer details with contact information
- **FamilyMember** - Family members linked to customers
- **Policy** - Insurance policies with renewal tracking
- **Reminder** - Automated reminders for various events
- **CallLog** - Customer interaction history
- **MessageTemplate** - Customizable message templates
- **RenewalHistory** - Policy renewal audit trail

## Design System

The app follows a strict **monochromatic color scheme**:
- Background: `#222223` (Charcoal)
- Foreground: `#E7E5D7` (Warm Off-White)
- Variations with opacity for visual hierarchy

### Design Principles
- Minimal and sophisticated
- Functional and premium
- Clean typography hierarchy
- Rounded cards and components
- Generous spacing
- No unnecessary decorations

## Building the Project

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34
- Gradle 8.2+

### Setup
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device (API 26+)

### Gradle Tasks
```bash
# Build the app
./gradlew build

# Run tests
./gradlew test

# Generate APK
./gradlew assembleDebug
```

## Development Status

**Current Progress**: 100% ✅

- [x] Project foundation and architecture
- [x] Database and data layer
- [x] ViewModels and state management
- [x] UI theme and components
- [x] Homepage implementation
- [x] Navigation structure
- [x] Customer screens (list, add, detail)
- [x] Policy screens (list, add, detail)
- [x] Reminders screen with filtering
- [x] Settings screen
- [x] WhatsApp integration with templates
- [x] Background services (WorkManager)
- [x] Notification system

## Next Steps

### App is Complete! ✅

The app has reached 100% completion with all planned features implemented:
- ✅ Customer and Policy Management
- ✅ Reminder System with Filtering
- ✅ WhatsApp Integration with Templates
- ✅ Background Reminder Scheduling
- ✅ Rich Notifications

### Optional Future Enhancements
1. Advanced search and filtering across all screens
2. Data backup/restore to cloud storage
3. WhatsApp Business API for automated messaging
4. Multi-agent support for agencies
5. Advanced analytics dashboard
6. PDF report generation for policies
7. Email integration for policy documents
8. Dark theme support
9. Multi-language support
10. Widget for home screen quick actions

## License

This is a private project for insurance agent CRM management.

## Author

Developed for insurance agents to streamline policy management and customer relationships.
