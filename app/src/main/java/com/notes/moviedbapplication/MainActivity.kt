package com.notes.moviedbapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.notes.moviedbapplication.adapters.WatchItemListingAdapter
import com.notes.moviedbapplication.databinding.ActivityMainBinding
import com.notes.moviedbapplication.interfaces.ItemClickListener
import com.notes.moviedbapplication.ui.EmptyState
import com.notes.moviedbapplication.ui.ErrorState
import com.notes.moviedbapplication.ui.LoadingState
import com.notes.moviedbapplication.ui.UnloadingState
import com.notes.moviedbapplication.ui.detailpage.DetailScreenActivity
import com.notes.moviedbapplication.viewmodels.MoviesHomeViewModel
import com.notes.mylibrary.model.movies.UpcomingResult
import com.notes.mylibrary.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel:MoviesHomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setUpObservers()
    }

    private fun setBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this)[MoviesHomeViewModel::class.java]
        mViewModel.getAllMoviesList()
    }

    private fun setUpcomingMoviesAdapter(arrayListUpcomingMovies: ArrayList<UpcomingResult>) {
        mBinding.recyclerWatchHome.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = WatchItemListingAdapter(applicationContext, arrayListUpcomingMovies, this)
        mBinding.recyclerWatchHome.adapter = adapter
    }

    override fun watchItemCLicked(movieId: Int) {
        val intent = Intent(applicationContext, DetailScreenActivity::class.java)
        intent.putExtra("mIntentID", movieId)
        startActivity(intent)
    }

    private fun setUpObservers() {
        mViewModel.allMoviesListLiveData.observe(this) {
            setUpcomingMoviesAdapter(it.results)
        }
        mViewModel.uiStateLiveData.observe(this) {
            when (it) {
                is LoadingState -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.recyclerWatchHome.visibility = View.GONE
                }
                is UnloadingState -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerWatchHome.visibility = View.VISIBLE
                }
                is EmptyState -> {
                    applicationContext.toast(getString(R.string.no_movie_found))
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerWatchHome.visibility = View.GONE
                }
                is ErrorState -> {
                    applicationContext.toast(it.message)
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerWatchHome.visibility = View.VISIBLE
                }
            }
        }
    }


}