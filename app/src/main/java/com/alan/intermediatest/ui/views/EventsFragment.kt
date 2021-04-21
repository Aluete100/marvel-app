package com.alan.intermediatest.ui.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.adapters.EventsListAdapter
import com.alan.intermediatest.data.models.Event
import com.alan.intermediatest.ui.base.BaseFragment
import com.alan.intermediatest.ui.base.MainActivity
import com.alan.intermediatest.utils.Status
import com.alan.intermediatest.utils.ViewUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_characters_list.*
import kotlinx.android.synthetic.main.fragment_events_list.*


class EventsFragment : BaseFragment() {

    private lateinit var adapter: EventsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setHomeButtonEnabled(false);

        setupRecyclerView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        isLogged()
    }

    private fun setupRecyclerView() {
        adapter = EventsListAdapter()
        events_recycler_view.adapter = adapter
        events_recycler_view.layoutManager = LinearLayoutManager(context)

        events_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    eventsViewModel.loadNextPageData().observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.LOADING -> {
                                    //TODO: Implement loading overlay
                                }
                                Status.SUCCESS -> {
                                    //TODO: Implement loading overlay
                                    if (resource.data!!.code == 200) {
                                        adapter.apply {
                                            setEventsData(resource.data.data.results)
                                            notifyDataSetChanged()
                                        }
                                    }
                                }
                                Status.ERROR -> {
                                    //TODO: Implement loading overlay
                                    Toasty.error(requireContext(), it.message!!, Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    })
                }
            }
        })
    }


    private fun observeData() {
        eventsViewModel.getEvents().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        ViewUtils(requireActivity()).showLoading(true)
                    }
                    Status.SUCCESS -> {
                        ViewUtils(requireActivity()).showLoading(false)
                        if (resource.data!!.code == 200) {
                            adapter.apply {
                                setEventsData(resource.data.data.results)
                                notifyDataSetChanged()
                            }
                        }
                    }
                    Status.ERROR -> {
                        ViewUtils(requireActivity()).showLoading(false)
                        Toasty.error(requireContext(), "Error ${it.message!!}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })
    }

    private fun isLogged() {
        if (authViewModel.loggedUser.value == null) {
            navigate(R.id.authFragment)
        }
    }
}
