package com.notes.moviedbapplication.ui.detailpage

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.notes.moviedbapplication.Constants
import com.notes.moviedbapplication.R
import com.notes.moviedbapplication.adapters.GenresListingAdapter
import com.notes.moviedbapplication.databinding.ActivityDetailPageBinding
import com.notes.moviedbapplication.ui.EmptyState
import com.notes.moviedbapplication.ui.ErrorState
import com.notes.moviedbapplication.ui.LoadingState
import com.notes.moviedbapplication.ui.UnloadingState
import com.notes.moviedbapplication.viewmodels.DetailPageViewModel
import com.notes.mylibrary.model.movies.Genre
import com.notes.mylibrary.model.movies.UpcomingMoviesModel
import com.notes.mylibrary.utils.toast

class DetailScreenActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityDetailPageBinding
    lateinit var mViewModel : DetailPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setBinding()
        setUpObservers()
        callBacks()
    }

    private fun setBinding() {
        mBinding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this)[DetailPageViewModel::class.java]
        mViewModel.getAllMoviesGenre()
    }

    private fun setGenreAdapter(genres: List<Genre>) {
        mBinding.recyclerGenres.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = GenresListingAdapter(this, genres)
        mBinding.recyclerGenres.adapter = adapter
    }

    private fun callBacks() {
        mBinding.imageBackFromDetail.setOnClickListener {
            finish()
        }
    }

    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun setUpObservers() {
        mViewModel.allGenreListLiveData.observe(this) {
            setGenreAdapter(it.genre)
            setOtherFields(it)
        }
        mViewModel.uiStateLiveData.observe(this) {
            when (it) {
                is LoadingState -> {
                    mBinding.progressBarDetailPage?.visibility = View.VISIBLE
                    mBinding.recyclerGenres.visibility = View.GONE
                }
                is UnloadingState -> {
                    mBinding.progressBarDetailPage?.visibility = View.GONE
                    mBinding.recyclerGenres.visibility = View.VISIBLE
                }
                is EmptyState -> {
                    applicationContext.toast(getString(R.string.no_movie_found))
                    mBinding.progressBarDetailPage?.visibility = View.GONE
                    mBinding.recyclerGenres.visibility = View.GONE
                }
                is ErrorState -> {
                    applicationContext.toast(it.message)
                    mBinding.progressBarDetailPage?.visibility = View.GONE
                    mBinding.recyclerGenres.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setOtherFields(it: List<UpcomingMoviesModel>?) {
        Glide.with(this)
            .load(Constants.IMAGE_URL+it.backdropPath)
            .apply(RequestOptions().override(600, 200))
            .into(mBinding.posterImage!!)
        mBinding.txtReleaseDate.text = "In Theaters " + it.releaseDate
        mBinding.txtDescription.text = it.overview
        mBinding.progressBarDetailPage?.visibility = View.GONE
    }
}