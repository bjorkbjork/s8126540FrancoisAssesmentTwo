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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.s8126540francoisassessmenttwo.databinding.FragmentLoginBinding
import com.example.s8126540francoisassessmenttwo.R
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

//                Inject constructor(private val repository: RestfulApiDevRepositoryClass)
                lifecycleScope.launch{
                    try {
                        loginViewModel.logInUser(user)?.observe(viewLifecycleOwner) { key ->
                            if (key != null) {
                                Log.v("NIT3213", "$key")
                                findNavController().navigate(LoginFragmentDirections.loggedIn(keypass = key))
                            } else {
                                Log.v("NIT3213", "ERROR")
                                buttonHelperText.text = resources.getString(R.string.invalidLogin)
                            }
                        }
                    } catch(ex: Exception){
                        Log.v("NIT3213", "$ex")
                    }
                }

                //navigationFunctionLambda = { findNavController().navigate(LoginFragmentDirections.loggedIn(user = it)) }

            } else{
                // Sets helper text to 'required' if users attempt to log in without typing in username or password

                firstNameWrapper.helperText = if (firstName.isEmpty()) resources.getString(R.string.required) else ""

                studentIdWrapper.helperText = if (studentId.isEmpty()) resources.getString(R.string.required) else ""

                // TODO: Add email validation. Probably overkill for this assignment, but I want to give it a try at some point
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

