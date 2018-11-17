package com.ikurek.diseasesnotebook.ui.registeractivity

import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.utlis.Validators
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    @Inject
    lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        BaseApp.activityComponent.inject(this)
        presenter.attach(this)
        bindHandlers()
    }

    override fun bindHandlers() {
        register_button.setOnClickListener {
            if (areRequiredFieldsValid()) presenter.handleRegisterButton(
                email_text.text.toString(),
                password_text.text.toString(),
                repeat_password_text.text.toString()
            )
        }
    }

    override fun showEmailAlreadyExistsDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_email_taken)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showServerOfflineDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_server_offline)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showRegistrationSuccessfulDialog() {
        MaterialDialog(this).apply {
            title(R.string.registration)
            message(R.string.success_registration)
            positiveButton {
                it.dismiss()
                this@RegisterActivity.finish()
            }
            onDismiss {
                this@RegisterActivity.finish()
            }
        }.show()
    }

    override fun showConnectionFailedDialog() {
        MaterialDialog(this).apply {
            title(R.string.error)
            message(R.string.error_connection_failed)
            positiveButton { this.dismiss() }
        }.show()
    }

    override fun showProgressIndicator() {
        register_button.visibility = View.INVISIBLE
        progress_indicator.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progress_indicator.visibility = View.INVISIBLE
        register_button.visibility = View.VISIBLE
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

        if (!Validators.isPasswordMatching(
                password_text.text.toString(),
                repeat_password_text.text.toString()
            )
        ) {
            repeat_password_input_layout.error = getString(R.string.error_passwords_do_not_match)
            return false
        } else {
            repeat_password_input_layout.error = null
        }

        return true
    }

    override fun getWindowToken(): IBinder {
        return register_layout.windowToken
    }
}
