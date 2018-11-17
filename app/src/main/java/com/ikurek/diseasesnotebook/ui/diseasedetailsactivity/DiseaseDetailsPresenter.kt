package com.ikurek.diseasesnotebook.ui.diseasedetailsactivity

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.api.DiseaseCommunicationService
import com.ikurek.diseasesnotebook.communication.api.model.DiseaseAPI
import com.ikurek.diseasesnotebook.communication.api.model.DiseasePostAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DiseaseDetailsPresenter : DiseaseDetailsContract.Presenter {

     var view: DiseaseDetailsContract.View? = null

    @Inject
    lateinit var diseaseCommunicationService: DiseaseCommunicationService

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun attach(view: DiseaseDetailsContract.View) {
        this.view = view
        BaseApp.presenterComponent.inject(this)
    }

    override fun detach() {
        Log.d("MainPresenter", "Detached")
        this.view = null
    }

    override fun getDisease(id: Int) {
        val token = sharedPreferences.getString(context.getString(R.string.sp_key_auth_token), "")
        diseaseCommunicationService.getDisease(token!!, id).enqueue(object : Callback<DiseaseAPI> {
            override fun onFailure(call: Call<DiseaseAPI>, t: Throwable) {
                Log.i("Failure", t.message)
            }

            override fun onResponse(
                call: Call<DiseaseAPI>,
                response: Response<DiseaseAPI>
            ) {
                if (response.code() == 200) {
                    response.body()?.let {
                        view?.showDiseaseDetails(it.toDiseaseModel())
                    }
                }
            }
        })
    }

    override fun patchDisease(disease: DiseasePostAPI, id: Int) {
        val token = sharedPreferences.getString(context.getString(R.string.sp_key_auth_token), "")
        diseaseCommunicationService.patchDiseases(token!!, id, disease).enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("Failure", t.message)
            }

            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.code() == 200) {
                    response.body()?.let {
                        view?.close()
                    }
                }
            }
        })
    }

    override fun postDisease(disease: DiseasePostAPI) {
        val token = sharedPreferences.getString(context.getString(R.string.sp_key_auth_token), "")
        diseaseCommunicationService.postDiseases(token!!, disease).enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("Failure", t.message)
            }

            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.code() == 201) {
                    response.body()?.let {
                        view?.close()
                    }
                }
            }
        })
    }

    override fun deleteDisease(id: Int) {
        val token = sharedPreferences.getString(context.getString(R.string.sp_key_auth_token), "")
        diseaseCommunicationService.deleteDisease(token!!, id).enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("Failure", t.message)
                Log.i("AAAA", call.request().toString())
                Log.i("AAAA", call.request().body().toString())
                Log.i("AAAA", call.request().headers().toString())

            }

            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.code() == 200) {
                    response.body()?.let {
                        view?.close()
                    }
                } else {
                    Log.d("AAAA", "${response.code()}, ${response.body()}")
                }
            }
        })
    }
}