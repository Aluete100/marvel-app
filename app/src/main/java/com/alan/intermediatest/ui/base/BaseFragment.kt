package com.alan.intermediatest.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.alan.intermediatest.data.viewmodel.AuthViewModel
import com.alan.intermediatest.data.viewmodel.CharactersViewModel
import com.alan.intermediatest.data.viewmodel.EventsViewModels
import com.alan.intermediatest.utils.ViewUtils

open class BaseFragment : Fragment() {

    lateinit var authViewModel: AuthViewModel
    lateinit var charactersViewModel: CharactersViewModel
    lateinit var eventsViewModel: EventsViewModels

    private lateinit var navController: NavController

    private var viewUtils: ViewUtils? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        charactersViewModel =
            ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
        eventsViewModel =
            ViewModelProvider(requireActivity()).get(EventsViewModels::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewUtils = ViewUtils(requireActivity())
    }


    // Navigation features

    fun navigate(destinationId: Int) {
        navController.navigate(destinationId)
    }

    fun navigate(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun popBackStack() {
        navController.popBackStack()
    }


}