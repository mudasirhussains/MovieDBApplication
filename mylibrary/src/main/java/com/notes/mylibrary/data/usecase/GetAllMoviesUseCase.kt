package com.notes.mylibrary.data.usecase

import com.notes.mylibrary.data.DataState
import com.notes.mylibrary.data.repository.Repository
import com.notes.mylibrary.model.movies.DetailPageModel
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Flow<DataState<ArrayList<UpcomingMoviesModel>>> {
        var moviesFinalList = ArrayList<UpcomingMoviesModel>()
        repository.getAllMoviesList().collect {
            when (it) {
                is DataState.Success -> {
                    moviesFinalList = it.data as ArrayList<UpcomingMoviesModel>
                }
                is DataState.Error -> {
                    //errr handling
                }
            }
        }
        return flow {
            emit(DataState.success(moviesFinalList))
        }
    }
}