package com.notes.mylibrary.data.repository

import androidx.annotation.WorkerThread
import com.notes.mylibrary.data.DataState
import com.notes.mylibrary.utils.StringUtils
import com.notes.mylibrary.data.remote.*
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import com.notes.mylibrary.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */
class RepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: ApiService
) : Repository {

    @WorkerThread
    override suspend fun getAllMoviesList(): Flow<DataState<UpcomingMoviesModel>> {
        return flow {
            apiService.getUpcomingMovies(AppConstants.Network.API_ACCESS_KEY).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }.onErrorSuspend {
                    emit(DataState.error<UpcomingMoviesModel>(message()))

                }.onExceptionSuspend {
                    if (this.exception is IOException) {
                        emit(DataState.error<UpcomingMoviesModel>(stringUtils.noNetworkErrorMessage()))
                    } else {
                        emit(DataState.error<UpcomingMoviesModel>(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }
    }
}
