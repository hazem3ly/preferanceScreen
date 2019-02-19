package com.example.user.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Display the fragment as the main content.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(android.R.id.content, PrefsFragment()).commit()
        }
    }

    abstract class BasePreferenceFragment : PreferenceFragmentCompat() {
        private fun setAllPreferencesToAvoidHavingExtraSpace(preference: Preference) {
            preference.isIconSpaceReserved = false
            if (preference is PreferenceGroup)
                for (i in 0 until preference.preferenceCount)
                    setAllPreferencesToAvoidHavingExtraSpace(preference.getPreference(i))
        }

        override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
            if (preferenceScreen != null)
                setAllPreferencesToAvoidHavingExtraSpace(preferenceScreen)
            super.setPreferenceScreen(preferenceScreen)

        }

        override fun onCreateAdapter(preferenceScreen: PreferenceScreen?): RecyclerView.Adapter<*> =
                object : PreferenceGroupAdapter(preferenceScreen) {
                    @SuppressLint("RestrictedApi")
                    override fun onPreferenceHierarchyChange(preference: Preference?) {
                        if (preference != null)
                            setAllPreferencesToAvoidHavingExtraSpace(preference)
                        super.onPreferenceHierarchyChange(preference)
                    }
                }
    }

    class PrefsFragment : BasePreferenceFragment() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) = setPreferencesFromResource(R.xml.preferences, rootKey)


    }
}
