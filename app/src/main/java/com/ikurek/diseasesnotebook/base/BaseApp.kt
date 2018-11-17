package com.ikurek.diseasesnotebook.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.ikurek.diseasesnotebook.communication.DateTimeDeserializer
import com.ikurek.diseasesnotebook.communication.api.DiseaseCommunicationService
import com.ikurek.diseasesnotebook.communication.api.UsersApi
import com.ikurek.diseasesnotebook.di.components.ActivityComponent
import com.ikurek.diseasesnotebook.di.components.DaggerActivityComponent
import com.ikurek.diseasesnotebook.di.components.DaggerPresenterComponent
import com.ikurek.diseasesnotebook.di.components.PresenterComponent
import com.ikurek.diseasesnotebook.di.modules.ApiModule
import com.ikurek.diseasesnotebook.di.modules.ContextModule
import com.ikurek.diseasesnotebook.di.modules.PresenterModule
import com.ikurek.diseasesnotebook.di.modules.SharedPreferencesModule
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApp : Application() {

    companion object {
        lateinit var presenterComponent: PresenterComponent
        lateinit var activityComponent: ActivityComponent
        var currentlyVisibleFragmentTag: String = ""
    }

    private lateinit var usersApi: UsersApi
    private lateinit var diseaseCommunicationService: DiseaseCommunicationService
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        buildRetrofit()
        buildSharedPreferences()
        buildDagger()
    }

    private fun buildDagger() {
        presenterComponent = DaggerPresenterComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .apiModule(ApiModule(usersApi, diseaseCommunicationService))
            .sharedPreferencesModule(SharedPreferencesModule(sharedPreferences))
            .build()
        activityComponent = DaggerActivityComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .presenterModule(PresenterModule())
            .sharedPreferencesModule(SharedPreferencesModule(sharedPreferences))
            .build()
    }

    private fun buildRetrofit() {
        val gson = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://ikurek.pl:8555")
            .build()

        usersApi = retrofit.create(UsersApi::class.java)
        diseaseCommunicationService = retrofit.create(DiseaseCommunicationService::class.java)
    }

    private fun buildSharedPreferences() {
        sharedPreferences =
                applicationContext.getSharedPreferences("drugsafesp", Context.MODE_PRIVATE)
    }
}