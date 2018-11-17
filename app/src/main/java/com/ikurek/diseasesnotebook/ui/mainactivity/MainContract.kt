package com.ikurek.diseasesnotebook.ui.mainactivity

import com.ikurek.diseasesnotebook.base.BaseContract
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel

interface MainContract {

    interface Presenter : BaseContract.Presenter<MainContract.View>{
        fun refreshData()
    }

    interface View : BaseContract.View {
        fun showDiseasesList(disease: List<DiseaseModel>)
        fun signOut()
    }
}