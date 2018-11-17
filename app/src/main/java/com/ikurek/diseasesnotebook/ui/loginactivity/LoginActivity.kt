package com.ikurek.diseasesnotebook.ui.loginactivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.ui.mainactivity.MainActivity
import com.ikurek.diseasesnotebook.ui.registeractivity.RegisterActivity
import com.ikurek.diseasesnotebook.utlis.Validators
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.activityComponent.inject(this)

        // If token is saved, just start MainActivity
        // Else, show login screen
        if (sharedPreferences.getString(this.getString(R.string.sp_key_auth_token), null) != null) {
            // Null starting tag in case it left from previous session
            BaseApp.currentlyVisibleFragmentTag = ""
            startMainActivity()
        } else {
            setContentView(R.layout.activity_login)
            title = getString(R.string.signin)
            presenter.attach(this)
            bindHandlers()
        }

    }

    override fun startRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun startMainActivity() {
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showServerOfflineDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_server_offline)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showIncorrectPasswordEmailCombinationDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_password_email_not_matching)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showConnectionFailedDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_connection_failed)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showInternalServerErrorDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_internal_server_error)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showProgressIndicator() {
        login_button.visibility = View.INVISIBLE
        goto_register_button.visibility = View.INVISIBLE
        progress_indicator.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progress_indicator.visibility = View.INVISIBLE
        login_button.visibility = View.VISIBLE
        goto_register_button.visibility = View.VISIBLE
    }

    override fun getWindowToken(): IBinder {
        return login_layout.windowToken
    }

    private fun bindHandlers() {
        login_button.setOnClickListener {
            if (areRequiredFieldsValid()) presenter.handleLoginButton(
                email_text.text.toString(),
                password_text.text.toString()
            )
        }
        goto_register_button.setOnClickListener { presenter.handleRegisterButton() }
    }

    private fun areRequiredFieldsValid(): Boolean {
        if (!Validators.isEmailValid(email_text.text.toString())) {
            email_input_layout.error = getString(R.string.error_email_invalid)
            return false
        } else {
            email_input_layout.error = null
        }

        if (!Validators.isPasswordLongerThenSixChars(password_text.text.toString())) {
            password_input_layout.error = getString(R.string.error_password_too_short)
            return false
        } else {
            password_input_layout.error = null
        }

        return true
    }


}
