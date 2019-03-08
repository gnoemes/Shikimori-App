package com.gnoemes.shikimoriapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager

fun Context.getDefaultSharedPreferences(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(this)
}

fun SharedPreferences.clear() {
    apply(getEditor().clear())
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    apply(getEditor().putBoolean(key, value))
}

fun SharedPreferences.putFloat(key: String, value: Float) {
    apply(getEditor().putFloat(key, value))
}

fun SharedPreferences.putInt(key: String, value: Int) {
    apply(getEditor().putInt(key, value))
}

fun SharedPreferences.putLong(key: String, value: Long) {
    apply(getEditor().putLong(key, value))
}

fun SharedPreferences.putString(key: String, value: String?) {
    apply(getEditor().putString(key, value))
}

fun SharedPreferences.putStringSet(key: String, values: Set<String>?) {
    apply(getEditor().putStringSet(key, values))
}

fun SharedPreferences.remove(key: String) {
    apply(getEditor().remove(key))
}

fun Fragment.putSetting(key: String, value: Any?) {
    val prefs = this.context?.getDefaultSharedPreferences()
    value.ifNotNull { data ->
        when (data) {
            is Long -> prefs?.putLong(key, data)
            is String -> prefs?.putString(key, data)
            is Int -> prefs?.putInt(key, data)
            is Boolean -> prefs?.putBoolean(key, data)
            is Float -> prefs?.putFloat(key, data)
        }
    }
}

private fun SharedPreferences.getEditor(): SharedPreferences.Editor {
    return this.edit()
}

private fun SharedPreferences.apply(editor: SharedPreferences.Editor) {
    editor.apply()
}