Mahakumbh Safety App

A comprehensive safety application for Simhasth Ujjain 2025 and Mahakumbh events with emergency SOS, live map zones, and lost \& found features.

🌟 Features

✅ Dashboard - User profile, zone status, distance travelled

✅ Live Map \& Zones - Real-time crowd density zones (Green, Yellow, Red)

✅ Emergency SOS - Tap to instantly call Police (100), Ambulance (108), Fire (101)

✅ Emergency Contacts - 8 emergency services with one-tap dialing

✅ Lost \& Found - Report and find lost items/people

✅ Safety Guide - Comprehensive safety tips and guidelines

✅ One-Time Login - Remember Me feature for easy access

✅ User Authentication - Firebase email/password authentication

🎯 App Screens



Splash Screen - App branding with Simhasth Ujjain logo

Login/Signup - Firebase authentication

Dashboard - User info, stats, quick access menu

Live Map - Zone visualization with markers

Emergency Contacts - Quick dial all services

Lost \& Found - Report and search items

Safety Guide - Safety tips and guidelines

Settings - App preferences



🛠️ Tech Stack



Language: Kotlin

UI Framework: Jetpack Compose (Material3)

Backend: Firebase Authentication + Firestore

Maps: Google Maps Compose API

Navigation: Jetpack Navigation Component

Image Loading: Coil



📋 Requirements



Android 8.0 (API 26) and above

Minimum 50MB free space

Internet connection required

Google Play Services



⚙️ Setup Instructions

Prerequisites



Android Studio (latest version)

JDK 11 or higher

Git installed



Step 1: Clone the Repository

bashgit clone https://github.com/dipesh-25/MahakumbhSafetyApp.git

cd MahakumbhSafetyApp

Step 2: Open in Android Studio



Open Android Studio

File → Open

Select the cloned MahakumbhSafetyApp folder

Wait for Gradle sync to complete



Step 3: Firebase Configuration



Go to Firebase Console

Create a new project (or use existing)

Enable Authentication (Email/Password)

Enable Firestore Database

Download google-services.json

Place it in app/ directory:



&nbsp;  MahakumbhSafetyApp/app/google-services.json

Step 4: Google Maps API Key



Go to Google Cloud Console

Create a new project

Enable Maps SDK for Android

Create API Key

Get your SHA-1 fingerprint:



bash   ./gradlew signingReport



Add fingerprint to Google Cloud Console

Add API key to AndroidManifest.xml:



xml   <meta-data

&nbsp;      android:name="com.google.android.geo.API\_KEY"

&nbsp;      android:value="YOUR\_API\_KEY\_HERE" />

Step 5: Dependencies

Add to build.gradle (Module: app):

gradledependencies {

&nbsp;   // Firebase

&nbsp;   implementation 'com.google.firebase:firebase-auth:22.3.1'

&nbsp;   implementation 'com.google.firebase:firebase-firestore:24.10.0'

&nbsp;   

&nbsp;   // Google Maps

&nbsp;   implementation 'com.google.maps.android:maps-compose:4.3.0'

&nbsp;   implementation 'com.google.android.gms:play-services-maps:18.2.0'

&nbsp;   

&nbsp;   // Image Loading

&nbsp;   implementation 'io.coil-kt:coil-compose:2.5.0'

}

Step 6: Build and Run

bash# Sync Gradle

./gradlew clean build



\# Run on emulator or device

./gradlew installDebug

🚀 Usage Guide

First Time Setup



Login/Signup



Enter email and password

Check "Remember Me" for one-time login

Click Login/Sign Up





Navigate Dashboard



See your profile info

Check zone status (Green/Yellow/Red)

View distance travelled





Emergency SOS



Tap Emergency tab

Choose service (Police, Ambulance, Fire, etc.)

Confirm call in dialog

Call connects immediately





View Map



Tap Map tab

See zone circles (Green/Yellow/Red)

Zoom and pan

View zone information panel





Report Lost Item



Tap Lost \& Found tab

Fill in item details

Submit report

View other reports







📱 Permissions

The app requires these permissions (automatically requested):

xml<uses-permission android:name="android.permission.INTERNET" />

<uses-permission android:name="android.permission.ACCESS\_FINE\_LOCATION" />

<uses-permission android:name="android.permission.ACCESS\_COARSE\_LOCATION" />

<uses-permission android:name="android.permission.CALL\_PHONE" />

<uses-permission android:name="android.permission.POST\_NOTIFICATIONS" />

🏗️ Project Structure

MahakumbhSafetyApp/

├── app/

│   ├── src/

│   │   ├── main/

│   │   │   ├── java/com/example/mahakumbhsafetyapp/

│   │   │   │   ├── ui/

│   │   │   │   │   ├── screens/

│   │   │   │   │   │   ├── DashboardScreen.kt

│   │   │   │   │   │   ├── MapScreen.kt

│   │   │   │   │   │   ├── EmergencyContactsScreen.kt

│   │   │   │   │   │   ├── LostFoundScreen.kt

│   │   │   │   │   │   ├── SafetyGuideScreen.kt

│   │   │   │   │   │   ├── LoginScreen.kt

│   │   │   │   │   │   ├── SignupScreen.kt

│   │   │   │   │   │   └── SplashScreen.kt

│   │   │   │   │   ├── theme/

│   │   │   │   │   ├── navigation/

│   │   │   │   │   └── Routes.kt

│   │   │   │   └── MainActivity.kt

│   │   │   ├── res/

│   │   │   │   ├── drawable/

│   │   │   │   │   └── logo.png

│   │   │   │   └── values/

│   │   │   └── AndroidManifest.xml

│   │   └── build.gradle

│   └── google-services.json (NOT in GitHub)

├── .gitignore

├── README.md

└── build.gradle

🎨 Key Features Explained

Emergency SOS



5 Major Services: Police, Ambulance, Fire, Disaster, Women Helpline

Instant Dialing: Tap → Phone dials automatically

Confirmation Dialog: Shows service name and number

All 8 Services: Plus Tourist Police, Medical, Lost \& Found



Live Map with Zones



Green Zone (🟢): Safe - Low crowd density

Yellow Zone (🟡): Moderate - Medium crowd

Red Zone (🔴): High Risk - Dense crowd

Real-time Visualization: Circle overlays on map

Zone Information: Details panel at bottom



Lost \& Found



Report Lost Item: Upload details with location

Search Reports: View all posted items

Contact Info: Reach out to reporters

Firestore Integration: Real-time data sync



One-Time Login



Remember Me: Check at login

Auto-Login: App starts directly to dashboard

Logout: Available in dashboard menu

Logout Clears: Settings are cleared



🔐 Security



✅ Firebase Authentication (secure password storage)

✅ Firestore Security Rules (database access control)

✅ .gitignore (sensitive files not committed)

✅ No hardcoded credentials

✅ Permission handling for sensitive operations



🌐 Google Maps Integration

Simhasth Ujjain Location

Latitude: 23.1815

Longitude: 75.7733

Zoom Level: 15

Zone Coordinates



Green Zone: 23.1800, 75.7700 (400m radius)

Yellow Zone: 23.1830, 75.7760 (500m radius)

Red Zone: 23.1820, 75.7750 (350m radius)



🚨 Emergency Numbers

ServiceNumberTypePolice100EmergencyAmbulance108MedicalFire101FireDisaster1070DisasterWomen1090SafetyTourist Police+91-571-220-2431TouristMedical Helpline+91-571-240-0362MedicalLost \& Found+91-571-222-5555Support

📈 Future Enhancements



&nbsp;Real-time GPS tracking

&nbsp;Push notifications

&nbsp;Chat feature for assistance

&nbsp;Family member sharing

&nbsp;Offline mode

&nbsp;SMS alerts

&nbsp;Multi-language support



🤝 Contributing

Contributions are welcome! Please:



Fork the repository

Create your feature branch:



bash   git checkout -b feature/AmazingFeature



Commit your changes:



bash   git commit -m 'Add some AmazingFeature'



Push to the branch:



bash   git push origin feature/AmazingFeature



Open a Pull Request



📄 License

This project is licensed under the MIT License - see LICENSE file for details.

👨‍💻 Author

Dipesh Kumawat



GitHub: @dipesh-25

Email: dipeshkumawat4321@gmail.com



📞 Support

If you face any issues:



Check existing GitHub Issues

Create a new Issue with details

Include error logs and screenshots



⚠️ Disclaimer

This app is for educational and safety purposes. Emergency calls should only be made in genuine emergencies. Always follow local emergency procedures.

