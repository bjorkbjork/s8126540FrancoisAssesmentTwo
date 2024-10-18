package com.example.s8126540francoisassessmenttwo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s8126540francoisassessmenttwo.databinding.FragmentDetailBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.s8126540francoisassessmenttwo.R

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

        //
        val entity = arguments.entity

        binding.itemCode.text = entity.stringKeyOne
        binding.itemName.text = entity.stringKeyTwo
        binding.intText.text = if(entity.intKey != 0) context?.getString(R.string.credits, entity.intTitle, entity.intKey) else entity.stringKeyFour // Convert credits
        binding.itemPerson.text = if (entity.stringKeyFour.isNotEmpty()) entity.stringKeyThree else ""
        binding.itemDetials.text = entity.stringKeyFive.ifEmpty { entity.stringKeyFour.ifEmpty { entity.stringKeyThree } }

        if (entity.stringKeyFive.length < entity.stringKeyFour.length){
            binding.itemDetials.text = entity.stringKeyFour
        }

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