package com.notes.mylibrary.data.repository

import com.notes.mylibrary.data.DataState
import com.notes.mylibrary.model.movies.DetailPageModel
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import kotlinx.coroutines.flow.Flow


/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize Unsplash API.
 */
interface Repository {
    suspend fun getAllMoviesList(): Flow<DataState<UpcomingMoviesModel>>

}
