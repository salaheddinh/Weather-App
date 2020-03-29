package com.salaheddin.weatherapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard(view: View) {
    view.requestFocus()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInputFromWindow(view.windowToken, 0, 0)
}

fun Activity.showLoading(data: View, loading: View, error: View) {
    data.visibility = View.GONE
    loading.visibility = View.VISIBLE
    error.visibility = View.GONE
}

fun Activity.showError(data: View, loading: View, error: View) {
    data.visibility = View.GONE
    loading.visibility = View.GONE
    error.visibility = View.VISIBLE
}

fun Activity.showData(data: View, loading: View, error: View) {
    data.visibility = View.VISIBLE
    loading.visibility = View.GONE
    error.visibility = View.GONE
}