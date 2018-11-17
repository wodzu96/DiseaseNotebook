package com.ikurek.diseasesnotebook.utlis

import android.content.Context
import android.content.Intent
import com.ikurek.diseasesnotebook.ui.loginactivity.LoginActivity

object Session {

    fun signOut(context: Context) {
        // Wipe data
        clearSharedPreferences(context)
        // Prepare login activity
        val intent = Intent(context, LoginActivity::class.java)
        // Start login activity
        context.startActivity(intent)
    }

    private fun clearSharedPreferences(context: Context) {
        // Get instance of SharedPrefs
        val sharedPreferences = context.getSharedPreferences("drugsafesp", Context.MODE_PRIVATE)
        // Remove all saved entries
        sharedPreferences.edit().clear().apply()
    }
}