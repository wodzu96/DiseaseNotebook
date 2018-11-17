package com.ikurek.diseasesnotebook.communication.model

data class DiseaseModel(
    val id: Int = 0,
    val diseaseName: String = "",
    val diseaseCondition: String = "",
    var drugs: String = "",
    var startDate: String,
    var finishDate: String
)