package com.alan.intermediatest.data.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.models.*
import kotlinx.android.synthetic.main.comics_list_item.view.*

class ComicsListAdapter :
    RecyclerView.Adapter<ComicsListAdapter.ComicsListViewHolder>() {

    private var comics = listOf<ComicSummary>()

    fun setComicsData(data: List<ComicSummary>) {
        Log.d("", "bind: Hola?")

        comics = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comics_list_item, parent, false)
        return ComicsListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ComicsListViewHolder, position: Int) {
        val currentComic = comics[position]
        holder.bind(currentComic)
    }

    inner class ComicsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comic: ComicSummary) {
            itemView.txt_comic_name.text = comic.name

            Log.d("", "bind: WTF")
        }
    }

}
