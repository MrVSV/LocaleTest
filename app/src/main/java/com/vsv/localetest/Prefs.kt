package com.vsv.localetest

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Locale

class Prefs(context: Context) {

    private val PREFS_NAME = "prefs"
    private val PREFS_KEY_LANGUAGE = "language"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLanguage(language: String) {
        prefs.edit() {
            putString(PREFS_KEY_LANGUAGE, language)
        }
    }

    fun getLanguage(): String {
        return prefs.getString(PREFS_KEY_LANGUAGE, "ru") ?: Locale.getDefault().language
    }
}