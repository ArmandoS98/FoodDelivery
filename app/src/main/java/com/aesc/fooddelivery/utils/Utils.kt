package com.aesc.fooddelivery.utils

import android.app.Activity
import android.content.Intent
import com.aesc.fooddelivery.providers.services.models.OrderFormatter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

object Utils {
    fun gotoDestinations(context: Activity, newActivity: Class<*>) {
        val intent = Intent(context, newActivity::class.java)
        context.startActivity(intent)
        context.finish()
    }

    fun logsUtils(s: String) {
        println("DEBUG $s")
    }

    fun serializeToJson(myClass: OrderFormatter?): String? {
        val gson = Gson()
        return gson.toJson(myClass)
    }

    fun convert(jsonData: String?): OrderFormatter? {
        val gsonb = GsonBuilder()
        val gson = gsonb.create()
        val j: JSONObject
        val jVisa: OrderFormatter
        return try {
            j = JSONObject(jsonData)
            jVisa = gson.fromJson(j.toString(), OrderFormatter::class.java)
            jVisa
        } catch (e: java.lang.Exception) {
            logsUtils("Error No se puedo deserealizar el Json")
            null
        }
    }
}