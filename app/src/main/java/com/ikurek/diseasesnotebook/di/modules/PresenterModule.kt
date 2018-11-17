package com.ikurek.diseasesnotebook.di.modules

import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsContract
import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsPresenter
import com.ikurek.diseasesnotebook.ui.loginactivity.LoginContract
import com.ikurek.diseasesnotebook.ui.loginactivity.LoginPresenter
import com.ikurek.diseasesnotebook.ui.mainactivity.MainContract
import com.ikurek.diseasesnotebook.ui.mainactivity.MainPresenter
import com.ikurek.diseasesnotebook.ui.registeractivity.RegisterContract
import com.ikurek.diseasesnotebook.ui.registeractivity.RegisterPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideLoginPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

    @Provides
    fun provideRegisterPresenter(): RegisterContract.Presenter {
        return RegisterPresenter()
    }

    @Provides
    fun provideDiseaseDetailsPresenter(): DiseaseDetailsContract.Presenter{
        return DiseaseDetailsPresenter()
    }
}