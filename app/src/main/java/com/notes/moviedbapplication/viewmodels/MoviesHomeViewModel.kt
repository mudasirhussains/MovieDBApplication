package com.notes.moviedbapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.moviedbapplication.ui.*
import com.notes.mylibrary.data.DataState
import com.notes.mylibrary.data.usecase.GetAllMoviesUseCase
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel  @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllMoviesUseCase,
): ViewModel() {

    private var _uiState = MutableLiveData<UiState>()
    var uiStateLiveData: LiveData<UiState> = _uiState

    private var allMoviesList = MutableLiveData<List<UpcomingMoviesModel>>()
    var allMoviesListLiveData: LiveData<List<UpcomingMoviesModel>> = allMoviesList

    fun getAllMoviesList(){
        try {
            _uiState.postValue(LoadingState)
            viewModelScope.launch {
                getAllCurrenciesUseCase.invoke().collect {
                    when (it) {
                        is DataState.Success -> {
                            if(it.data.isNotEmpty()){
                                allMoviesList.postValue(it.data!!)
                                _uiState.postValue(UnloadingState)
                            }else{
                                _uiState.postValue(EmptyState)
                            }
                        }
                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(it.message))
                        }

                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}