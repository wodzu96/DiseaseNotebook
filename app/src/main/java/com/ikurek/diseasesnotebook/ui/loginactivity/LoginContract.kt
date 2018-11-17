package com.ikurek.diseasesnotebook.ui.loginactivity

import android.os.IBinder
import com.ikurek.diseasesnotebook.base.BaseContract

interface LoginContract {

    interface Presenter : BaseContract.Presenter<LoginContract.View> {

        fun handleLoginButton(email: String, password: String)
        fun handleRegisterButton()

    }

    interface View : BaseContract.View {
        fun startRegisterActivity()
        fun getWindowToken(): IBinder
        fun hideProgressIndicator()
        fun showProgressIndicator()
        fun showIncorrectPasswordEmailCombinationDialog()
        fun showServerOfflineDialog()
        fun showConnectionFailedDialog()
        fun showInternalServerErrorDialog()
        fun startMainActivity()
    }
}