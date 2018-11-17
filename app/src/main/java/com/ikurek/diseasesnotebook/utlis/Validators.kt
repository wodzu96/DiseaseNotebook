package com.ikurek.diseasesnotebook.utlis

import android.util.Patterns

object Validators {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordLongerThenSixChars(password: String): Boolean {
        return password.length > 6
    }

    fun isPasswordMatching(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

    fun isDrugQueryValid(query: String): Boolean {
        return query.trim().length >= 3
    }

    fun isEanValid(ean: String): Boolean {
        val eanAsLong: Long? = ean.toLongOrNull()

        return eanAsLong != null
    }

}