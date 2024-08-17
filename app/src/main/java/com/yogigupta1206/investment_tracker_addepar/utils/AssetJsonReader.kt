package com.yogigupta1206.investment_tracker_addepar.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetJsonReader {
    fun readJSONFromAssets(context: Context, path: String): String {
        val identifier = "[ReadJSON]"

        return try {
            context.assets.open(path).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                    val jsonString = bufferedReader.readText()
                    Log.i(identifier, "Success JSON as String: $jsonString")
                    jsonString
                }
            }
        } catch (e: Exception) {
            Log.e(identifier, "Failed Error reading JSON: $e")
            ""
        }
    }
}

