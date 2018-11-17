package com.ikurek.diseasesnotebook.communication.api

import com.ikurek.diseasesnotebook.communication.model.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UsersApi {

    @POST("/api/login")
    @Headers("Content-Type: application/json", "Accept: application/json ")
    fun login(@Body loginModel: LoginModel): Call<Void>

    @POST("/api/register")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun register(@Body loginModel: LoginModel): Call<Void>
}