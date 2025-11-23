package com.example.mahakumbhsafetyapp.data

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)

    fun saveLogin(email: String) {
        prefs.edit().putBoolean("isLoggedIn", true).apply()
        prefs.edit().putString("email", email).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun getEmail(): String? = prefs.getString("email", "")

    fun logout() {
        prefs.edit().clear().apply()
    }
}
