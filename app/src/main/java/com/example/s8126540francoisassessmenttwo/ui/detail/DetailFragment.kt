package com.example.s8126540francoisassessmenttwo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.s8126540francoisassessmenttwo.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.ui.dashboard.DashboardFragmentArgs
import javax.inject.Named

class DetailFragment : Fragment() {

    private val arguments: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null

    //private val detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemCode.text = arguments.entity.courseCode
        binding.itemName.text = arguments.entity.courseName
        binding.intText.text = context?.getString(R.string.credits, arguments.entity.credits)
        binding.itemPerson.text = arguments.entity.instructor
        binding.itemDetials.text = arguments.entity.description

        val button = binding.navigationButton

        button.setOnClickListener{
            // use popBackStack, rather than navigate, as it persists the keypass.
            findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}