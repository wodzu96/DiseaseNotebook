package com.ikurek.diseasesnotebook.ui.mainactivity

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.api.DiseaseCommunicationService
import com.ikurek.diseasesnotebook.communication.api.model.DiseaseAPI
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter : MainContract.Presenter {

    var view: MainContract.View? = null

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var diseaseCommunicationService: DiseaseCommunicationService

    override fun attach(view: MainContract.View) {
        Log.d("MainPresenter", "Attached")
        this.view = view
        BaseApp.presenterComponent.inject(this)
        getDiseases()
    }

    override fun detach() {
        Log.d("MainPresenter", "Detached")
        this.view = null
    }

    override fun refreshData() {
        getDiseases()
    }

    private fun getDiseases() {
        val token = sharedPreferences.getString(context.getString(R.string.sp_key_auth_token), "")
        diseaseCommunicationService.getDiseases(token!!)
            .enqueue(object : Callback<List<DiseaseAPI>> {
                override fun onFailure(call: Call<List<DiseaseAPI>>, t: Throwable) {
                    Log.i("Failure", t.message)
                }

                override fun onResponse(
                    call: Call<List<DiseaseAPI>>,
                    response: Response<List<DiseaseAPI>>
                ) {
                    when (response.code()){
                        200 -> {
                            response.body()?.let {
                                view?.showDiseasesList(it.map { it.toDiseaseModel() })
                            }
                        }
                        404 -> {
                            view?.showDiseasesList(emptyList())
                        }
                    }
                }
            })
    }
}