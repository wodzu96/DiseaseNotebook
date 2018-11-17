package com.ikurek.diseasesnotebook.ui.registeractivity

import android.os.IBinder
import com.ikurek.diseasesnotebook.base.BaseContract

interface RegisterContract {

    interface Presenter : BaseContract.Presenter<RegisterContract.View> {

        fun handleRegisterButton(email: String, password: String, repeatPassword: String)
    }

    interface View : BaseContract.View {

        fun bindHandlers()
        fun showEmailAlreadyExistsDialog()
        fun showServerOfflineDialog()
        fun showRegistrationSuccessfulDialog()
        fun showProgressIndicator()
        fun hideProgressIndicator()
        fun getWindowToken(): IBinder
        fun showConnectionFailedDialog()
    }
}