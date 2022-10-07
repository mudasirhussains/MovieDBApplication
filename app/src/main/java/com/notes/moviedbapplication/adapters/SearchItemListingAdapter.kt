package com.notes.moviedbapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notes.moviedbapplication.R
import com.notes.mylibrary.model.search.SearchItemsModel

class SearchItemListingAdapter(private var mList: ArrayList<SearchItemsModel>) : RecyclerView.Adapter<SearchItemListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vList = mList[position]
        holder.imageItemWatch.setImageResource(vList.itemImage)
        holder.txtItemWatch.text = vList.itemCategory
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItemWatch = itemView.findViewById(R.id.imageItemWatch) as ImageView
        val txtItemWatch  = itemView.findViewById(R.id.txtItemWatch) as TextView
    }


}