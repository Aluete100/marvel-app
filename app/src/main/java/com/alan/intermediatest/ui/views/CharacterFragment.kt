package com.alan.intermediatest.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alan.intermediatest.R
import com.alan.intermediatest.data.adapters.ComicsListAdapter
import com.alan.intermediatest.data.models.Character
import com.alan.intermediatest.ui.base.BaseFragment
import com.alan.intermediatest.ui.base.MainActivity
import com.alan.intermediatest.utils.Constants
import com.alan.intermediatest.utils.Status
import com.alan.intermediatest.utils.ViewUtils
import com.alan.intermediatest.utils.glide.GlideHelper
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_characters_list.*


class CharacterFragment : BaseFragment() {

    private lateinit var adapter: ComicsListAdapter
    private var selectedCharacterId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

        arguments?.let {
            val safeArgs =
                CharacterFragmentArgs.fromBundle(
                    it
                )
            selectedCharacterId = safeArgs.selectedCharacterId
        }

        if (selectedCharacterId == -1) {
            navigateUp()
            return
        }

        setupRecyclerView()
        getCharacterDetails()
    }

    private fun getCharacterDetails() {
        charactersViewModel.getCharacter(selectedCharacterId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        ViewUtils(requireActivity()).showLoading(true)

                    }
                    Status.SUCCESS -> {
                        ViewUtils(requireActivity()).showLoading(false)
                        if (resource.data!!.code == 200) {
                            updateUI(resource.data.data.results[0])
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

    private fun updateUI(character: Character?) {
        GlideHelper.loadImage(
            requireView(),
            character!!.thumbnail!!.path!!,
            Constants.IMAGE_XL,
            character.thumbnail!!.extension!!,
            img_selected_character
        )
        txt_marvel_character_description.text = character?.description ?: "No description found"
        adapter.apply {
            character.comics?.items?.let { setComicsData(it) }
            notifyDataSetChanged()
        }
        (requireActivity() as MainActivity).activity_title.text = character.name

    }

    private fun setupRecyclerView() {
        adapter = ComicsListAdapter()
        character_comics_recycler_view.adapter = adapter
        character_comics_recycler_view.layoutManager = LinearLayoutManager(context)
    }

}