package com.rucech.p2_master_detail_booksb

import android.content.Context
import com.rucech.p2_master_detail_booksb.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

data class BookItem
    (
    val id: String,
    val author: String,
    val description: String,
    val publication_date: String,
    val title: String,
    val url_image: String

){

    companion object
    {
        val listaBookItem = mutableListOf<BookItem>()

        fun loadJson(context: Context):MutableList<BookItem>?
        {
            //We create a list in which the books will be loaded
            var booksList:MutableList<BookItem>? = null
            val raw = context.resources.openRawResource(R.raw.datos_json)
            //We use the buffered reader to read the json
            val rd = BufferedReader(InputStreamReader(raw))

            val listType: Type = object : TypeToken<MutableList<BookItem?>?>() {}.type

            val gson = Gson()
            booksList = gson.fromJson(rd, listType)

            //  We use a for loop to add the books to the list of books
            for((count, b:BookItem) in booksList!!.withIndex())
            {
                listaBookItem.add(count, booksList[count])
            }

            return booksList;
        }

        //Function in charge of returning the id of BookItem
        fun getBookItemById(id:String):BookItem
        {


            return listaBookItem.filter { it.id == id }[0]
        }
    }


    //ToString
    override fun toString(): String
    {
        return "$author - $title - $description - $publication_date - $title - $url_image "
    }

    //Getters & Setters
}