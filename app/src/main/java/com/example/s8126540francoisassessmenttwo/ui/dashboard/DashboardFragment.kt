package com.example.s8126540francoisassessmenttwo.ui.dashboard

import android.app.AlertDialog
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
import com.example.s8126540francoisassessmenttwo.databinding.FragmentDashboardBinding
import com.example.s8126540francoisassessmenttwo.recyclerview.RecyclerViewAdapter
import com.example.s8126540francoisassessmenttwo.ui.login.LoginFragmentDirections
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val arguments: DashboardFragmentArgs by navArgs()
    private lateinit var navigationFunctionLambda: (Entity) -> Unit
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

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

        navigationFunctionLambda = { findNavController()
            .navigate(DashboardFragmentDirections
                .detailsScreen(entity = it)) }

        recyclerViewAdapter = RecyclerViewAdapter(
            data = ItemData(emptyList(), 0),
            navigationFunction = navigationFunctionLambda
        )

        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)


        val loadingScroll: ProgressBar = binding.progressBar;
        val button: MaterialButton = binding.navigationButton;
        val textView: TextView = binding.screenTitle
        textView.text = arguments.keypass.keypass.toString()

        loadingScroll.isVisible = false
        
        lifecycleScope.launch{
            try {
                loadingScroll.isVisible = true
                // get data from viewmodel, passing keypass
                val data = dashboardViewModel.getData(arguments.keypass)

                // if data returned as not null, set up recyclerview
                if (data.first.value != null) {
                    Log.v("NIT3213", "${data.first.value}")
                    lifecycleScope.launch{
                        recyclerViewAdapter.setData(data.first.value!!)
                    }
                    loadingScroll.isVisible = false
                } else { // keypass is given by API - so there should never be errors, unless the API goes down. Handle anyway.
                    Log.v("NIT3213", "${data.second.value}")

                    loadingScroll.isVisible = false

                    val error = data.second.value;
                    val timeout = Exception("timeout");
                    val invalid = Exception("invalid");

                    textView.text = if (error == timeout) "Please try again later"
                                    else if (error == invalid) resources.getString(R.string.invalidLogin)
                                    else "$error"
                }
            } catch(ex: Exception){
                Log.v("NIT3213", "$ex")
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
                findNavController().navigate(R.id.loggedOut)

            }

            alert.setNegativeButton(resources.getString(R.string.no)){_,_ ->
                // if not, keep them here
            }

            // create alert dialog and display on click
            val alertDialog = alert.create()
            alertDialog.show()
        }


        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = recyclerViewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}