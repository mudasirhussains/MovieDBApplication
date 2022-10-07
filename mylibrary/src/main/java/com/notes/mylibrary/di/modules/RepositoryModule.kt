package com.notes.mylibrary.di.modules

import android.app.Application
import com.notes.mylibrary.data.remote.ApiService
import com.notes.mylibrary.data.repository.Repository
import com.notes.mylibrary.data.repository.RepositoryImpl
import com.notes.mylibrary.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideRepository(stringUtils: StringUtils, apiService: ApiService): Repository {
        return RepositoryImpl(stringUtils, apiService)
    }
}
