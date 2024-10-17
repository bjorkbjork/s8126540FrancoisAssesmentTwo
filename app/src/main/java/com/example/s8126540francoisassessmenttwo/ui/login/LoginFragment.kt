package com.example.s8126540francoisassessmenttwo.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
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

        // hide progress bar by default
        binding.progressBar.isVisible = false

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get values of the button, fields, and of the layouts
        val loginButton: Button = binding.button;
        val firstNameWrapper: TextInputLayout = binding.firstNameWrapper;
        val firstNameField: EditText = binding.editTextFirstName
        val studentIdField: EditText = binding.editTextStudentID;
        val studentIdWrapper: TextInputLayout = binding.studentIdWrapper;

        loginButton.setOnClickListener{

            studentIdWrapper.helperText = ""
            firstNameWrapper.helperText=""

            // Get email and password values each time the button is clicked

            val firstName:String = firstNameField.text.toString()
            val studentId:String = studentIdField.text.toString()

            val user = User(firstName,studentId)

            if( firstName.isNotEmpty() && studentId.isNotEmpty() ){

                lifecycleScope.launch{
                    logInUser(user)
                }

            } else{
                // Sets helper text to 'required' if users attempt to log in without typing in username or password

                firstNameWrapper.helperText = if (firstName.isEmpty()) resources.getString(R.string.required) else ""

                studentIdWrapper.helperText = if (studentId.isEmpty()) resources.getString(R.string.required) else ""

            }
        }


    }


    // extract logInUser to separate function
    private suspend fun logInUser(user: User){


        val buttonHelperText: TextView = binding.buttonHelperText;
        val loadingScroll: ProgressBar = binding.progressBar;
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loadingScroll.isVisible = false
        buttonHelperText.text = ""

        try {

            loadingScroll.isVisible = true

            val result = loginViewModel.logInUser(user)

            // if keypass returned not as null, then we have a working keypass
            if (result.first.value != null) {
                loadingScroll.isVisible = false
                Log.v("NIT3213", "${result.first.value}")
                findNavController().navigate(LoginFragmentDirections.loggedIn(keypass = result.first.value!!))
            } else { // else something has gone wrong, so we have errors to handle
                loadingScroll.isVisible = false

                Log.v("NIT3213", "${result.second.value}")

                val error = result.second.value!!.message;

                // display errors to user
                buttonHelperText.text = resources.getString(error!!.toInt())
            }
        } catch(ex: Exception){
            Log.v("NIT3213", "$ex")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

