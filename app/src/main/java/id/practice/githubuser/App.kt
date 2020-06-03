package id.practice.githubuser

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import id.practice.githubuser.helper.Toolkit

class App : Application() {
    companion object {
        private var appcontext: Context? = null

        val context: Context?
            get() = appcontext
    }

    override fun onCreate() {
        super.onCreate()
        appcontext = applicationContext

        initPreferences()
    }

    private fun initPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        Toolkit.darkMode(sharedPreferences.getBoolean("theme", false))

        // Entah diperlukan atau tidak
        // Toolkit.reminder(sharedPreferences.getBoolean("reminder", false))
    }
}