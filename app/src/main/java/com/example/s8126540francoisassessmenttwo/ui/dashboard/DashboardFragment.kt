package com.example.s8126540francoisassessmenttwo.ui.dashboard

import android.app.AlertDialog
import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.Entity
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.databinding.FragmentDashboardBinding
import com.example.s8126540francoisassessmenttwo.recyclerview.RecyclerViewAdapter
import com.example.s8126540francoisassessmenttwo.ui.login.LoginFragmentDirections
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val arguments: DashboardFragmentArgs by navArgs()
    private lateinit var navigationFunctionLambda: (Entity) -> Unit
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter


    // Allow for caching of data
    private lateinit var data:Pair<MutableStateFlow<ItemData?>, MutableStateFlow<Exception?>>

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set loading bar to invisible
        binding.progressBar.isVisible = false

        navigationFunctionLambda = { findNavController()
            .navigate(DashboardFragmentDirections
                .detailsScreen(entity = it)) }

        recyclerViewAdapter = RecyclerViewAdapter(
            data = ItemData(emptyList(), 0),
            navigationFunction = navigationFunctionLambda
        )

        val button: MaterialButton = binding.navigationButton;
        val textView: TextView = binding.screenTitle
        textView.text = arguments.keypass.keypass.toString()

        lifecycleScope.launch{
            // Check to see if data has already been received
            if (::data.isInitialized && data.first.value != null) {
                // If data has been cahced, simply send to recycler view. No need for network call.
                Log.v("NIT3213", "${data.first.value}")
                lifecycleScope.launch {
                    recyclerViewAdapter.setData(data.first.value!!)
                }
            }
            else {


                getData(arguments.keypass)
            }
        }

        button.setOnClickListener{
            // Create alert dialog box

            val alert = AlertDialog.Builder(this@DashboardFragment.context)

            // Sets message and title from strings.xml file
            alert.setMessage(resources.getString(R.string.signOutAlert))
            alert.setTitle(resources.getString(R.string.alertTitle))
            alert.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                // if user is sure they want to log out, send back to log-in screen
                // Clear cached data
                data.first.value = null
                findNavController().navigate(R.id.loggedOut)

            }

            alert.setNegativeButton(resources.getString(R.string.no)){_,_ ->
                // if not, keep them here
            }

            // create alert dialog and display on click
            val alertDialog = alert.create()
            alertDialog.show()
        }


        binding.recyclerView.adapter = recyclerViewAdapter
    }

    // Separate data retrieval function
    private suspend fun getData(key:Keypass){

        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val loadingScroll: ProgressBar = binding.progressBar;

        try {
            loadingScroll.isVisible = true

            // get data from viewmodel, passing keypass
            data = dashboardViewModel.getData(keypass = key)

            // if data returned as not null, set up recyclerview
            if (data.first.value != null) {
                Log.v("NIT3213", "${data.first.value}")
                lifecycleScope.launch {
                    recyclerViewAdapter.setData(data.first.value!!)
                }
                loadingScroll.isVisible = false
            } else { // keypass is given by API - so there should never be errors, unless the API goes down. Handle anyway.
                Log.v("NIT3213", "${data.second.value}")

                loadingScroll.isVisible = false

                val error = data.second.value;
                val timeout = Exception("timeout");
                val invalid = Exception("invalid");

                binding.errorText.text = if (error == timeout) "Please try again later"
                else if (error == invalid) resources.getString(R.string.invalidLogin)
                else "$error"
            }
        } catch (ex: Exception) {
            Log.v("NIT3213", "$ex")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}