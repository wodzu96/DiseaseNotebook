package com.ikurek.diseasesnotebook.communication.api.model

import com.google.gson.annotations.SerializedName

data class DiseasePostAPI(
    @SerializedName("disease_name")
    val diseaseName: String = "",
    @SerializedName("disease_condition")
    val diseaseCondition: String = "",
    @SerializedName("drugs")
    var drugs: String = "",
    @SerializedName("start_date")
    var startDate: String,
    @SerializedName("finish_date")
    var finishDate: String
)