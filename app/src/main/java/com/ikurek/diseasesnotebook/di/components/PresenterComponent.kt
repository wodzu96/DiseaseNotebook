package com.ikurek.diseasesnotebook.di.components

import com.ikurek.diseasesnotebook.di.modules.ApiModule
import com.ikurek.diseasesnotebook.di.modules.ContextModule
import com.ikurek.diseasesnotebook.di.modules.SharedPreferencesModule
import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsPresenter
import com.ikurek.diseasesnotebook.ui.loginactivity.LoginPresenter
import com.ikurek.diseasesnotebook.ui.mainactivity.MainPresenter
import com.ikurek.diseasesnotebook.ui.registeractivity.RegisterPresenter
import dagger.Component

@Component(
    modules = [
        ContextModule::class,
        ApiModule::class,
        SharedPreferencesModule::class
    ]
)
interface PresenterComponent {

    fun inject(loginPresenter: LoginPresenter)
    fun inject(registerPresenter: RegisterPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(diseasePresenter: DiseaseDetailsPresenter)
}