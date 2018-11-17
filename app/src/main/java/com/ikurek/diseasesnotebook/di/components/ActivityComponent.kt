package com.ikurek.diseasesnotebook.di.components

import com.ikurek.diseasesnotebook.di.modules.ContextModule
import com.ikurek.diseasesnotebook.di.modules.PresenterModule
import com.ikurek.diseasesnotebook.di.modules.SharedPreferencesModule
import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsActivity
import com.ikurek.diseasesnotebook.ui.loginactivity.LoginActivity
import com.ikurek.diseasesnotebook.ui.mainactivity.MainActivity
import com.ikurek.diseasesnotebook.ui.registeractivity.RegisterActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for Application
 * Allows injecting BaseApp
 */
@Singleton
@Component(modules = [PresenterModule::class, ContextModule::class, SharedPreferencesModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(registerDiseaseDetailsActivity: DiseaseDetailsActivity)

}