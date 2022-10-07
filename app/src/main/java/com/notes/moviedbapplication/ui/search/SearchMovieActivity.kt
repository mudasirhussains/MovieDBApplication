package com.notes.moviedbapplication.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.notes.moviedbapplication.R
import com.notes.moviedbapplication.adapters.SearchItemListingAdapter
import com.notes.moviedbapplication.adapters.SearchResultListingAdapter
import com.notes.moviedbapplication.databinding.ActivitySearchMovieBinding
import com.notes.mylibrary.model.search.SearchItemsModel
import java.util.*
import kotlin.collections.ArrayList

class SearchMovieActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySearchMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        populateDataInModel("")
        callBacks()
    }

    private fun setBinding() {
        mBinding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun callBacks() {
        mBinding.imageClearSearch.setOnClickListener {
            if (mBinding.editTextSearch.text.toString().isNotEmpty()) {
                mBinding.editTextSearch.setText("")
            } else {
                //will close activity
            }
        }

        mBinding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                populateDataInModel(s.toString())
            }
        })

        mBinding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (mBinding.showEmptyText.visibility == View.VISIBLE) {
                    //also hide keyboard
                } else {
                    //also hide keyboard
                    mBinding.linearAfterResultFound.visibility = View.VISIBLE
                    mBinding.linearBeforeResultFound.visibility = View.GONE
                    mBinding.txtShowTopResult.visibility = View.GONE
                    mBinding.viewShowTopResult.visibility = View.GONE
                }
                return@setOnEditorActionListener true
            }
            false
        }


        mBinding.imageBackFromSearch.setOnClickListener {
            mBinding.linearAfterResultFound.visibility = View.GONE
            mBinding.linearBeforeResultFound.visibility = View.VISIBLE
            mBinding.txtShowTopResult.visibility = View.VISIBLE
            mBinding.viewShowTopResult.visibility = View.VISIBLE
        }
    }

    private fun populateDataInModel(keyword: String) {
        val mSearchItemList = ArrayList<SearchItemsModel>()
        mSearchItemList.add(SearchItemsModel("Friends", R.drawable.search_one, "Comedies"))
        mSearchItemList.add(SearchItemsModel("A Time To Kill", R.drawable.search_two, "Crime"))
        mSearchItemList.add(SearchItemsModel("Family", R.drawable.search_three, "Family"))
        mSearchItemList.add(
            SearchItemsModel(
                "Social Dilemma",
                R.drawable.search_four,
                "Documentary"
            )
        )
        mSearchItemList.add(SearchItemsModel("The King", R.drawable.search_five, "Dramas"))
        mSearchItemList.add(SearchItemsModel("Timeless", R.drawable.search_six, "Fantasy"))
        mSearchItemList.add(SearchItemsModel("Holidays", R.drawable.search_seven, "Holidays"))
        mSearchItemList.add(SearchItemsModel("The Mummy", R.drawable.search_eight, "Horror"))
        mSearchItemList.add(SearchItemsModel("In Time", R.drawable.search_nine, "Sci-Fi"))
        mSearchItemList.add(SearchItemsModel("It", R.drawable.search_ten, "Thriller"))

        val mSearchItemResultList = ArrayList<SearchItemsModel>()
        mSearchItemResultList.add(
            SearchItemsModel(
                "Timeless",
                R.drawable.search_result_one,
                "Fantasy"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "In Time",
                R.drawable.search_result_two,
                "Sci-Fi"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "A Time To Kill",
                R.drawable.search_result_three,
                "Crime"
            )
        )

        if (keyword.isEmpty()) {
            mBinding.recyclerBeforeSearch.visibility = View.VISIBLE
            mBinding.linearSearchedResults.visibility = View.GONE
            mBinding.recyclerBeforeSearch.layoutManager = GridLayoutManager(applicationContext, 2)
            val adapter = SearchItemListingAdapter(mSearchItemList)
            mBinding.recyclerBeforeSearch.adapter = adapter
        } else {
            mBinding.linearSearchedResults.visibility = View.VISIBLE
            mBinding.recyclerBeforeSearch.visibility = View.GONE
            mBinding.recyclerAfterSearch.layoutManager = LinearLayoutManager(applicationContext)
            val adapterSecond = SearchResultListingAdapter(mSearchItemResultList)
            mBinding.recyclerAfterSearch.adapter = adapterSecond

            val filteredList: ArrayList<SearchItemsModel> = ArrayList()
            for (item in mSearchItemResultList) {
                if (item.itemTitle.isNullOrBlank()) {
                    mBinding.showEmptyText.visibility = View.VISIBLE
                } else {
                    if (item.itemTitle.lowercase(Locale.getDefault()).contains(
                            keyword.lowercase(
                                Locale.getDefault()
                            )
                        )
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            adapterSecond.filterSearch(filteredList)

            mBinding.searchResultCount.text = filteredList.size.toString() + " Results Found"
            if (filteredList.size == 0) {
                mBinding.showEmptyText.visibility = View.VISIBLE
            } else {
                mBinding.showEmptyText.visibility = View.GONE
            }
        }
    }

}