package com.notes.mylibrary.utils

import android.content.Context
import android.widget.Toast


class UtilityAndConstants {

    companion object{
        fun showMessage(context: Context,message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

        const val BASE_API_URL = "https://api.themoviedb.org/"
    }
}