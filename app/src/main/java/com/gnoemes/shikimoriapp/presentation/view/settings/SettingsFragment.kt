package com.gnoemes.shikimoriapp.presentation.view.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.gnoemes.shikimoriapp.BuildConfig
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.entity.app.data.SettingsExtras
import com.gnoemes.shikimoriapp.utils.dialogContext
import com.gnoemes.shikimoriapp.utils.ifNotNull
import com.gnoemes.shikimoriapp.utils.putBoolean
import com.google.firebase.analytics.FirebaseAnalytics

class SettingsFragment : PreferenceFragmentCompat() {

    private val DIALOG_KEY: String = "IsRestartDialogShowing"
    private val analytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context!!) }
    private val restartDialog: MaterialDialog by lazy {
        MaterialDialog(context!!.dialogContext())
                .noAutoDismiss()
                .message(R.string.need_restart)
                .positiveButton(R.string.restart) { updateActivity() }
    }

    private var isDarkTheme: Boolean = false


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        restartDialog.setCancelable(false)


        if (savedInstanceState != null && savedInstanceState.containsKey(DIALOG_KEY)) {
            if (savedInstanceState.getBoolean(DIALOG_KEY)) {
                restartDialog.show()
            }
        }

        getPreference(R.string.pref_about_program)?.summary = BuildConfig.VERSION_NAME
        getPreference(R.string.pref_dark_theme)?.setOnPreferenceChangeListener { preference, newValue ->
            restartDialog.show()
            isDarkTheme = newValue as Boolean
            true
        }

        getPreference(R.string.pref_auto_dark_theme)?.setOnPreferenceChangeListener { preference, newValue ->
            restartDialog.show()
            true
        }

        with(getPreference(R.string.pref_naming)!!) {
            summary = namingSummary
            setOnPreferenceClickListener {
                context.ifNotNull { context ->
                    MaterialDialog(context.dialogContext())
                            .show {
                                title(R.string.titles_naming)
                                listItems(R.array.naming_types, waitForPositiveButton = true) { _, index, text ->
                                    it.summary = text
                                    getSharedPrefs().putBoolean(SettingsExtras.IS_ROMADZI_NAMING, index == 1)
                                }
                            }
                }
                true
            }
        }

        analytics.setUserProperty("theme", isDarkTheme.toString())
        analytics.setUserProperty("titles_naming", if (getSharedPrefs().getBoolean(SettingsExtras.IS_ROMADZI_NAMING, false)) "romadzi" else "russian")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDividerHeight(0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(DIALOG_KEY, restartDialog.isShowing)
    }


    private fun updateActivity() {
        val i = context!!.packageManager.getLaunchIntentForPackage(context!!.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    private fun PreferenceFragmentCompat.getPreference(@StringRes key: Int): Preference? {
        return findPreference(resources.getString(key))
    }

    private fun PreferenceFragmentCompat.getSharedPrefs(): SharedPreferences {
        return this.preferenceManager.sharedPreferences
    }

    private val namingSummary: String by lazy {
        if (preferenceManager.sharedPreferences.getBoolean(SettingsExtras.IS_ROMADZI_NAMING, false)) {
            context!!.getString(R.string.on_romadzi)
        } else {
            context!!.getString(R.string.on_russian)
        }
    }
}