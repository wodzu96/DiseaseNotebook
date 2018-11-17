package com.ikurek.diseasesnotebook.ui.diseasedetailsactivity

import com.ikurek.diseasesnotebook.base.BaseContract
import com.ikurek.diseasesnotebook.communication.api.model.DiseasePostAPI
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel

class DiseaseDetailsContract{
    interface Presenter : BaseContract.Presenter<DiseaseDetailsContract.View>{
        fun getDisease(id: Int)
        fun postDisease(disease: DiseasePostAPI)
        fun patchDisease(disease: DiseasePostAPI, id: Int)
        fun deleteDisease(id: Int)
    }

    interface View : BaseContract.View {
        fun showDiseaseDetails(disease: DiseaseModel)
        fun close()
    }
}