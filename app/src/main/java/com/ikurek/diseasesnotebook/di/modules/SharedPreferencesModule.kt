package com.ikurek.diseasesnotebook.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule(val sharedPreferences: SharedPreferences) {

    @Provides
    fun provideSharedPreferences(): SharedPreferences = sharedPreferences
}