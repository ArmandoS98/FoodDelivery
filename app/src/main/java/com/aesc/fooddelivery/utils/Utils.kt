package com.aesc.fooddelivery.utils

import android.app.Activity
import android.content.Intent

object Utils {
    fun gotoDestinations(context: Activity, newActivity: Class<*>) {
        val intent = Intent(context, newActivity::class.java)
        context.startActivity(intent)
        context.finish()
    }

    fun logsUtils(s: String) {
        println("DEBUG $s")
    }

}