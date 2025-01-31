package com.example.feedo

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

data class SigninRequest(val email: String, val password: String)

fun signinUser(email: String, password: String) {
    val client = OkHttpClient()
    val gson = Gson()

    // Prepare the request body
    val signinRequest = SigninRequest(email, password)
    val requestBody = RequestBody.create(
        "application/json".toMediaTypeOrNull(),
        gson.toJson(signinRequest)
    )

    // Create the POST request
    val request = Request.Builder()
        .url("http://10.0.2.2:5000/signin") // Replace with your Flask server URL
        .post(requestBody)
        .build()

    // Make the HTTP call
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Handle network error
            println("Signin failed: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Signin error: ${response.message}")
                    return
                }


                // Parse the response
                val responseMessage = gson.fromJson(response.body?.string(), ResponseMessage::class.java)
                println("Signin response: ${responseMessage.message ?: responseMessage.error}")
            }
        }
    })
}
