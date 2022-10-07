package com.notes.mylibrary.data.remote
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") key : String): ApiResponse<UpcomingMoviesModel>
}
