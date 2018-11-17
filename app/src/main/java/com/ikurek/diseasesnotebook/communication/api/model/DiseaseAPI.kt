package com.ikurek.diseasesnotebook.communication.api.model

import com.google.gson.annotations.SerializedName
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel

data class DiseaseAPI(
    @SerializedName("id")
    val id: Int = 0,
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
) {

    fun toDiseaseModel(): DiseaseModel {
        return DiseaseModel(id, diseaseName, diseaseCondition, drugs, startDate, finishDate)
    }
}