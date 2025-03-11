package com.vsv.localetest

import android.content.Context
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, language: String): Context? {
            return updateResources(context, language);
    }

    private fun updateResources(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        val prefs = Prefs(context)
        prefs.setLanguage(language)
        return context.createConfigurationContext(configuration)
    }
}