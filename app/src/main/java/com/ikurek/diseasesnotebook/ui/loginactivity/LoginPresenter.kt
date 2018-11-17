package com.ikurek.diseasesnotebook.ui.loginactivity


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.communication.api.UsersApi
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.model.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginPresenter : LoginContract.Presenter {

    var view: LoginContract.View? = null

    @Inject
    lateinit var usersApi: UsersApi

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun attach(view: LoginContract.View) {
        this.view = view
        BaseApp.presenterComponent.inject(this)
    }

    override fun detach() {
        this.view = null
    }

    override fun handleLoginButton(email: String, password: String) {
        // Hide keyboard
        val inputMethodManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.getWindowToken(), 0)

        //Show progress
        view?.showProgressIndicator()

        // Make API call
        usersApi.login(LoginModel(email, password)).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, e: Throwable) {
                Log.e("Login", "Failed. Cause: $e")

                // Show info
                view?.hideProgressIndicator()
                view?.showConnectionFailedDialog()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                view?.hideProgressIndicator()

                when (response.code()) {
                    200 -> {
                        val token: String? = response.headers()["Authorization"]
                        if (token != null) {
                            Log.d("Login", "Auth token ok")
                            saveAuthDataInSharedPreferences(token, email)
                            view?.startMainActivity()
                        } else {
                            Log.d("Login", "Auth token is null")
                            view?.showInternalServerErrorDialog()
                        }
                    }
                    403 -> view?.showIncorrectPasswordEmailCombinationDialog()
                    404 -> view?.showServerOfflineDialog()
                    else -> view?.showInternalServerErrorDialog()
                }
            }
        })
    }

    override fun handleRegisterButton() {
        view?.startRegisterActivity()
    }

    private fun saveAuthDataInSharedPreferences(token: String, email: String) {
        sharedPreferences.edit()
            .putString(context.getString(R.string.sp_key_auth_token), token)
            .putString(context.getString(R.string.sp_key_user_email), email)
            .apply()
    }
}