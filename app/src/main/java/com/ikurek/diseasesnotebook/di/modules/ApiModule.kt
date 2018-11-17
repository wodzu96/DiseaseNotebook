package com.ikurek.diseasesnotebook.di.modules

import com.ikurek.diseasesnotebook.communication.api.DiseaseCommunicationService
import com.ikurek.diseasesnotebook.communication.api.UsersApi
import dagger.Module
import dagger.Provides

@Module
class ApiModule(
    private val usersApi: UsersApi,
    private val diseaseCommunicationService: DiseaseCommunicationService
) {

    @Provides
    fun provideUsersApi(): UsersApi = usersApi

    @Provides
    fun provideDiseaseCommunicationService(): DiseaseCommunicationService =
        diseaseCommunicationService
}