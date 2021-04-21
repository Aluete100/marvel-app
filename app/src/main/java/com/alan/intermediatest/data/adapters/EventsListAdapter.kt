package com.alan.intermediatest.data.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.models.*
import com.alan.intermediatest.utils.Constants
import com.alan.intermediatest.utils.ViewUtils
import com.alan.intermediatest.utils.glide.GlideHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.characters_list_item.view.*
import kotlinx.android.synthetic.main.events_list_item.view.*
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_events_list.*
import kotlinx.android.synthetic.main.fragment_events_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EventsListAdapter :
    RecyclerView.Adapter<EventsListAdapter.EventsViewHolder>() {

    private var events = listOf<Event>()
    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    fun setEventsData(data: List<Event>) {
        events = if (events.isEmpty()) {
            data
        } else {
            events + data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.events_list_item, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val currentEvent = events[position]
        holder.bind(currentEvent)
    }

    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var adapter: ComicsListAdapter = ComicsListAdapter()

        fun bind(event: Event) {
            setupRecyclerView()

            itemView.txt_event_name.text = event.title
            itemView.txt_from_date.text =
                if (event.start != null) ViewUtils.formatDate(event.start, "dd MMMM yyyy")
                else "Sin Info"
            itemView.txt_to_date.text =
                if (event.end != null) ViewUtils.formatDate(event.end, "dd MMMM yyyy")
                else "Sin Info"
            itemView.comics_recycler_view.adapter = adapter

            itemView.img_expand.setOnClickListener {
                onExpandPress(
                    itemView.card_view_expandable,
                    itemView.expanded_layout,
                    itemView.img_expand
                )
            }

            event.thumbnail?.path?.let { path ->
                //Check if thumbnail path exists
                event.thumbnail.extension?.let { extension ->
                    //Check if thumbnail extension
                    GlideHelper.loadImage(
                        itemView,
                        path, Constants.IMAGE_XL, extension, itemView.img_event
                    )
                }
            }

            adapter.apply {
                event.comics?.items?.let { adapter.setComicsData(it) }
                adapter.notifyDataSetChanged()
            }


        }

        private fun setupRecyclerView() {
            adapter = ComicsListAdapter()
            itemView.comics_recycler_view.adapter = adapter
            itemView.comics_recycler_view.layoutManager = LinearLayoutManager(itemView.context)
            itemView.comics_recycler_view.setRecycledViewPool(viewPool)
        }

        private fun onExpandPress(cardView: ViewGroup, view: View, imgButton: ImageView) {
            if (view.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                view.visibility = View.GONE
                imgButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                view.visibility = View.VISIBLE
                imgButton.setImageResource(R.drawable.ic_keyboard_arrow_up);
            }
        }

    }
}
