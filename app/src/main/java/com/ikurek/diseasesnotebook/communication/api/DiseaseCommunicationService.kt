package com.ikurek.diseasesnotebook.communication.api

import com.ikurek.diseasesnotebook.communication.api.model.DiseaseAPI
import com.ikurek.diseasesnotebook.communication.api.model.DiseasePostAPI
import retrofit2.Call
import retrofit2.http.*

interface DiseaseCommunicationService {

    @GET("/api/entries/{id}")
    fun getDisease(@Header("Authorization") token: String, @Path("id") id: Int): Call<DiseaseAPI>

    @GET("/api/entries")
    fun getDiseases(@Header("Authorization") token: String): Call<List<DiseaseAPI>>

    @POST("/api/entries")
    fun postDiseases(@Header("Authorization") token: String, @Body diseasePost: DiseasePostAPI): Call<Any>

    @PATCH("/api/entries/{id}")
    fun patchDiseases(@Header("Authorization") token: String, @Path("id") id: Int, @Body diseasePost: DiseasePostAPI): Call<Any>

    @DELETE("/api/entries/{id}")
    fun deleteDisease(@Header("Authorization") token: String, @Path("id") id: Int): Call<Any>
}