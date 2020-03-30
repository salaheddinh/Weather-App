package com.salaheddin.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat

object Utils {
    fun dateLongToHour(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        return simpleDateFormat.format(time)
    }

    fun dateLongToDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        return simpleDateFormat.format(time)
    }

    fun dateLongToShowDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("EEE dd-MM-yyyy")
        return simpleDateFormat.format(time)
    }

    fun checkIfAlreadyHavePermission(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun requestForSpecificPermission(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            requestCode
        )
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}