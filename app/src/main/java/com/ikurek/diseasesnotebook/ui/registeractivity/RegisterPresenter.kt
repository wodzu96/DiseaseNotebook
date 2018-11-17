package com.ikurek.diseasesnotebook.ui.registeractivity

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.ikurek.diseasesnotebook.communication.api.UsersApi
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.model.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterPresenter : RegisterContract.Presenter {

    var view: RegisterContract.View? = null

    @Inject
    lateinit var usersApi: UsersApi

    @Inject
    lateinit var context: Context

    override fun attach(view: RegisterContract.View) {
        this.view = view
        BaseApp.presenterComponent.inject(this)
    }

    override fun detach() {
        this.view = null
    }

    override fun handleRegisterButton(email: String, password: String, repeatPassword: String) {

        // Hide keyboard
        val inputMethodManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.getWindowToken(), 0)

        //Show progress
        view?.showProgressIndicator()

        // Make API call
        usersApi.register(LoginModel(email, password)).enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, e: Throwable) {
                Log.e("Register", "Failed. Cause: $e")

                view?.hideProgressIndicator()
                view?.showConnectionFailedDialog()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("Register", "Success, code is ${response.code()}")

                view?.hideProgressIndicator()

                when (response.code()) {
                    200 -> view?.showRegistrationSuccessfulDialog()
                    400 -> view?.showEmailAlreadyExistsDialog()
                    404 -> view?.showServerOfflineDialog()
                }

            }
        })
    }

}