package com.alan.intermediatest.ui.views

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.adapters.CharactersListAdapter
import com.alan.intermediatest.data.models.Character
import com.alan.intermediatest.ui.base.BaseFragment
import com.alan.intermediatest.ui.base.MainActivity
import com.alan.intermediatest.utils.Status
import com.alan.intermediatest.utils.ViewUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_characters_list.*
import java.lang.Exception

class CharactersFragment : BaseFragment(), CharactersListAdapter.ICharacterAction {

    private lateinit var adapter: CharactersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).supportActionBar!!.show()

        ViewUtils(requireActivity()).showLoading(true)

        setupRecyclerView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        isLogged()
        charactersViewModel.resetPages()
    }

    private fun setupRecyclerView() {
        adapter = CharactersListAdapter(this)
        characters_recycler_view.adapter = adapter
        characters_recycler_view.layoutManager = LinearLayoutManager(context)

        characters_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    charactersViewModel.loadNextPageData().observe(viewLifecycleOwner, Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.LOADING -> {
                                    //TODO: Implement loading overlay
                                }
                                Status.SUCCESS -> {
                                    //TODO: Implement loading overlay
                                    if (resource.data!!.code == 200) {
                                        adapter.apply {
                                            setCharactersData(resource.data.data.results)
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
        charactersViewModel.getCharacters().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        ViewUtils(requireActivity()).showLoading(true)
                    }
                    Status.SUCCESS -> {
                        ViewUtils(requireActivity()).showLoading(false)
                        if (resource.data!!.code == 200) {
                            adapter.apply {
                                setCharactersData(resource.data.data.results)
                                notifyDataSetChanged()
                            }
                        } else {

                        }
                    }
                    Status.ERROR -> {
                        ViewUtils(requireActivity()).showLoading(false)
                        Toasty.error(requireContext(), it.message!!, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onCharacterPress(character: Character) {
        val characterDataAction =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterFragment(
                character.id
            )
        navigate(characterDataAction)
    }


    private fun isLogged() {
        if (authViewModel.getCurrentUser().value == null) {
            navigate(R.id.action_charactersFragment_to_authFragment)
        }
    }
}
