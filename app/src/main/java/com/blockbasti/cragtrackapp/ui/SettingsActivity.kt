package com.blockbasti.cragtrackapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.blockbasti.cragtrackapp.R
import com.blockbasti.cragtrackapp.databinding.SettingsActivityBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                run {
                    AppCompatDelegate.setDefaultNightMode(
                        sharedPreferences.getString(key, "-1")?.toInt()!!
                    )
                }
            }

            val darkmode =
                findPreference<ListPreference>(getString(R.string.preference_dark_mode_key))
            darkmode?.summaryProvider = Preference.SummaryProvider<ListPreference> { preference ->
                preference.entry
            }

            findPreference<Preference>(getString(R.string.preference_signout_key))?.setOnPreferenceClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                true
            }
        }
    }
}