package com.example.s8126540francoisassessmenttwo.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.s8126540francoisassessmenttwo.databinding.FragmentLoginBinding
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.User
import com.example.s8126540francoisassessmenttwo.ui.dashboard.DashboardFragmentArgs
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var navigationFunctionLambda: (Keypass) -> Unit



    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    init{


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get values of the button, fields, and of the layouts

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val loginButton: Button = binding.button;
        val firstNameWrapper: TextInputLayout = binding.firstNameWrapper;
        val firstNameField: EditText = binding.editTextFirstName
        val studentIdField: EditText = binding.editTextStudentID;
        val studentIdWrapper: TextInputLayout = binding.studentIdWrapper;
        val buttonHelperText: TextView = binding.buttonHelperText;

        loginButton.setOnClickListener{

            buttonHelperText.text = ""

            // Get email and password values each time the button is clicked

            val firstName:String = firstNameField.text.toString()
            val studentId:String = studentIdField.text.toString()

            val user = User(firstName,studentId)

            if( firstName.isNotEmpty() && studentId.isNotEmpty() ){

                lifecycleScope.launch{
                    try {
//                        loginViewModel.logInUser(user)
//                        loginViewModel.result.collect { result ->
//                            if (result.first != null) {
//                                Log.v("NIT3213", "${result.first}")
//                                findNavController().navigate(LoginFragmentDirections.loggedIn(keypass = result.first.value!!))
//                            } else {
//                                Log.v("NIT3213", "${result.second}")
//
//                                buttonHelperText.text = resources.getString(R.string.invalidLogin)
//                            }
//                        }
                        val result = loginViewModel.logInUser(user)
                        if (result.first.value != null) {
                            Log.v("NIT3213!!", "${result.first.value}")
                            findNavController().navigate(LoginFragmentDirections.loggedIn(keypass = result.first.value!!))
                        } else {
                            Log.v("errors123", "${result.second.value}")

                            val error = result.second.value; val timeout = Exception("timeout"); val invalid = Exception("invalid");

                            buttonHelperText.text = if (error == timeout) "Please try again later"
                                                    else if (error == invalid) resources.getString(R.string.invalidLogin)
                                                    else "$error"

                        }
                    } catch(ex: Exception){
                        Log.v("NIT3213er", "$ex")
                    }
                }

                //navigationFunctionLambda = { findNavController().navigate(LoginFragmentDirections.loggedIn(user = it)) }

            } else{
                // Sets helper text to 'required' if users attempt to log in without typing in username or password

                firstNameWrapper.helperText = if (firstName.isEmpty()) resources.getString(R.string.required) else ""

                studentIdWrapper.helperText = if (studentId.isEmpty()) resources.getString(R.string.required) else ""

            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

