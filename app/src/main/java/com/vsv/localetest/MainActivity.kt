package com.vsv.localetest

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vsv.localetest.ui.theme.LocaleTestTheme
import java.util.Locale


class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            super.attachBaseContext(newBase)
        } else {
            val prefs = Prefs(newBase)
            val l = prefs.getLanguage()
            super.attachBaseContext(LocaleHelper.setLocale(newBase, l))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Locale.getDefault()) {
                Toast.makeText(this@MainActivity, Locale.getDefault().language, Toast.LENGTH_SHORT).show()
            }
            LocaleTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val locales = mapOf(
                        "English" to "en-US",
                        "Русский" to "ru-RU"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.choose_language),
                        )
                        Spacer(Modifier.height(20.dp))
                        locales.keys.forEach { locale ->
                            Row {
                                Text(
                                    text = locale,
                                    modifier = Modifier
                                        .clickable {
                                            locales[locale]?.let { changeLocale(it, this@MainActivity) }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun changeLocale(languageCode: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList(Locale.forLanguageTag(languageCode))
    } else {
        LocaleHelper.setLocale(context, languageCode.split("-").first())
        (context as MainActivity).recreate()
    }
}