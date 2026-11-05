# 🚀 Quick Start Guide - Policy Reminder

Get the Policy Reminder app up and running in minutes!

## Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: Version 17
- **Android SDK**: API 26+ (Android 8.0+)
- **Device/Emulator**: Android 8.0 or higher

## Installation Steps

### 1. Open Project in Android Studio

```bash
# Navigate to project directory
cd policyReminder

# Open with Android Studio
studio .
```

Or use Android Studio: **File → Open → Select policyReminder folder**

### 2. Sync Gradle

Android Studio will automatically prompt to sync Gradle. Click **Sync Now**.

If not, manually sync:
```
File → Sync Project with Gradle Files
```

### 3. Build Project

```bash
# Command line
./gradlew build

# Or in Android Studio
Build → Make Project (Ctrl+F9)
```

### 4. Run Application

**Option A: Using Android Studio**
- Click the green Run button (▶️) or press `Shift+F10`
- Select your device/emulator
- Wait for app to launch

**Option B: Command Line**
```bash
# Install on connected device
./gradlew installDebug

# Run directly
./gradlew run
```

## First Launch Setup

### 1. Grant Permissions
On first launch, the app will request permissions:
- ✅ **Notifications** - For reminder alerts (Required)
- ✅ **Phone** - For making calls (Optional but recommended)

### 2. Initial Data
The app starts with an empty database. Let's add some data:

#### Add Your First Customer
1. Tap **Add Customer** card on homepage
2. Fill in the form:
   - First Name: "Rahul"
   - Surname: "Sharma"
   - Mobile: "9876543210"
   - Email: "rahul@example.com" (optional)
   - Date of Birth: "15/03/1985" (optional)
3. Tap **Save Customer**

#### Add Your First Policy
1. Tap **Add Policy** card on homepage
2. Select the customer you just created
3. Fill in policy details:
   - Policy Number: "POL/2024/12345"
   - Insurance Company: "ABC Insurance"
   - Policy Type: "Health Insurance"
   - Policyholder Name: "Rahul Sharma" (auto-filled)
   - Start Date: "01/01/2024"
   - Renewal Date: "01/01/2025"
   - Premium Amount: "15000"
   - Payment Frequency: "Yearly"
4. Tap **Save Policy**

### 3. Explore Features

**Homepage:**
- View customer and policy counts
- See recent policies
- Check upcoming reminders

**Customers:**
- Search customers
- View customer details
- Call or WhatsApp directly

**Policies:**
- Filter by status (Active, Due Soon, etc.)
- Search policies
- View renewal dates

**Reminders:**
- Filter by type (Renewals, Birthdays, etc.)
- View upcoming events

**Settings:**
- Configure notification preferences
- Manage message templates
- Backup/restore options

## Testing the App

### Test Notifications

1. **Add a policy with renewal date in next 24 hours**
   - The background worker will detect it
   - Notification will appear at 9 AM (default time)

2. **Manual Test:**
   - Background worker runs automatically
   - Check notifications at scheduled time

### Test WhatsApp Integration

1. Go to **Customer Detail** screen
2. Tap **WhatsApp** button
3. App opens WhatsApp with pre-filled message
4. Review and send message

### Test Call Integration

1. Go to **Customer Detail** screen
2. Tap **Call** button
3. Phone dialer opens with customer's number
4. Make the call

## Configuration

### Change Notification Time

The default notification time is **9:00 AM**. To change:

1. Open `WorkManagerHelper.kt`
2. Find `calculateInitialDelay()` function
3. Modify the hour:
```kotlin
set(Calendar.HOUR_OF_DAY, 9)  // Change to desired hour (0-23)
```

### Customize Message Templates

Templates are in `MessageTemplateUtils.kt`. Modify these functions:
- `getBirthdayTemplate()`
- `getAnniversaryTemplate()`
- `getPolicyRenewalTemplate()`
- `getFollowUpTemplate()`

### Change Theme Colors

Colors are defined in `ui/theme/Color.kt`:
```kotlin
val BackgroundPrimary = Color(0xFF222223)  // Dark background
val ForegroundPrimary = Color(0xFFE7E5D7)  // Light text/cards
```

## Troubleshooting

### Build Errors

**Error: "SDK not found"**
- Solution: Install Android SDK via Android Studio SDK Manager

**Error: "Java version mismatch"**
- Solution: Set JDK 17 in Android Studio settings

**Error: "Gradle sync failed"**
- Solution: Clean and rebuild
```bash
./gradlew clean build
```

### Runtime Issues

**Notifications not showing:**
1. Check notification permissions in device settings
2. Verify channels are created (check logcat)
3. Ensure background worker is scheduled

**WhatsApp not opening:**
1. Ensure WhatsApp is installed on device
2. Check internet connection
3. Verify phone number format (10 digits)

**Database errors:**
1. Clear app data in device settings
2. Uninstall and reinstall app
3. Check Room queries in DAOs

## Development Tips

### Enable Debug Logging

Add to `build.gradle.kts`:
```kotlin
buildTypes {
    debug {
        buildConfigField("boolean", "DEBUG_MODE", "true")
    }
}
```

### View Database

Use Android Studio Database Inspector:
1. Run app on emulator/device
2. **View → Tool Windows → App Inspection**
3. Select **Database Inspector** tab
4. Explore tables and data

### Test WorkManager

Force run worker immediately:
```kotlin
// In your test code
WorkManagerHelper.scheduleOneTimeReminderCheck(context, delayMillis = 0)
```

### Monitor Notifications

Use Logcat filter:
```
NotificationHelper
```

## Production Build

### Generate Signed APK

1. **Build → Generate Signed Bundle/APK**
2. Select **APK**
3. Create or select keystore
4. Choose release variant
5. Click **Finish**

### Or via Command Line

```bash
# Create keystore (first time only)
keytool -genkey -v -keystore release.keystore -alias policy_reminder -keyalg RSA -keysize 2048 -validity 10000

# Build signed APK
./gradlew assembleRelease
```

APK location: `app/build/outputs/apk/release/app-release.apk`

## Next Steps

After getting the app running:

1. **Customize branding**: Update app name, icon, colors
2. **Add test data**: Create multiple customers and policies
3. **Test all flows**: Customer CRUD, Policy CRUD, Reminders
4. **Configure notifications**: Set preferred times
5. **Customize templates**: Personalize WhatsApp messages
6. **Test on real device**: Ensure everything works

## Resources

- **Android Documentation**: https://developer.android.com
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Room Database**: https://developer.android.com/training/data-storage/room
- **WorkManager**: https://developer.android.com/topic/libraries/architecture/workmanager

## Support

If you encounter issues:

1. Check `COMPLETION_SUMMARY.md` for detailed documentation
2. Review `PROJECT_SUMMARY.md` for technical details
3. Read `README.md` for project overview
4. Check Android Studio's **Build** and **Logcat** tabs for errors

## Quick Reference

### Key Files
- `MainActivity.kt` - App entry point
- `PolicyReminderApplication.kt` - Application class
- `AppDatabase.kt` - Room database configuration
- `*ViewModel.kt` - State management
- `*Screen.kt` - UI screens
- `WorkManagerHelper.kt` - Background scheduling
- `NotificationHelper.kt` - Notification system

### Important Directories
- `data/model/` - Database entities
- `data/dao/` - Data access objects
- `ui/screens/` - All app screens
- `ui/theme/` - Design system
- `utils/` - Helper utilities
- `worker/` - Background workers

---

**You're all set!** 🎉 

The app should now be running on your device. Explore all features and customize as needed!

For detailed documentation, see:
- `COMPLETION_SUMMARY.md` - Full feature list
- `PROJECT_SUMMARY.md` - Technical architecture
- `README.md` - Project overview
