package com.example.s8126540francoisassessmenttwo.ui.login

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.example.s8126540francoisassessmenttwo.data.User
import com.example.s8126540francoisassessmenttwo.ui.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.rules.TestRule
import retrofit2.HttpException
import java.net.SocketTimeoutException


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var repository: RestfulApiDevRepositoryClass
    private lateinit var exceptions: Exceptions

    // Subject under test
    //private var loginViewModel: LoginViewModel = mockk()

    @Before
    fun setUp() {
        exceptions = mockk()
        repository = mockk()
        loginViewModel = mockk()
        // Set the Main dispatcher to a TestCoroutineDispatcher
        Dispatchers.setMain(StandardTestDispatcher())
        // Initialize the ViewModel with mocked dependencies
        loginViewModel = LoginViewModel(repository, exceptions)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        // Reset the dispatcher to the default one after tests
        Dispatchers.resetMain()
    }

    @Test
    fun logInUser_withValidUser_shouldUpdateKeypass() = runTest {
        // Given: A valid user and expected Keypass
        val user = User("Francois", "s8126540")
        val expectedKeypass = Keypass("courses")

        // Mock the repository to return the expected Keypass without calling the API
        coEvery { repository.addUser(user) } returns expectedKeypass

        // Act: Call the logInUser function
        val (keypass, errors) = loginViewModel.logInUser(user)


        // Assert: The keypass should be updated, and errors should be null
        assertEquals(expectedKeypass, keypass.value)
        assertNull(errors.value)
    }

    @Test
    fun logInUser_withHttpException_shouldUpdateErrors() = runTest {
        // Given: An invalid user and a 404 HttpException
        val user = User("123", "s12345678")
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 404
        }
        // Mock the exceptions
        coEvery { exceptions.noSuchDetails } returns Exception("No such details")

        // Mock the repository to throw an HttpException without calling the API
        coEvery { repository.addUser(user) } throws httpException

        // Act: Call the logInUser function
        val (keypass, errors) = loginViewModel.logInUser(user)

        // Assert: The keypass should be null, and errors should be the noSuchDetails exception
        assertNull(keypass.value)
        assertEquals(exceptions.noSuchDetails, errors.value)
    }

    @Test
    fun logInUser_withSocketTimeoutException_shouldUpdateErrors() = runTest {
        // Given: A valid user and a SocketTimeoutException
        val user = User("Francois", "s8126540")
        // Mock the exceptions
        coEvery { exceptions.timeout } returns Exception("Timeout occurred")

        // Mock the repository to throw a SocketTimeoutException without calling the API
        coEvery { repository.addUser(user) } throws SocketTimeoutException()

        // Act: Call the logInUser function
        val (keypass, errors) = loginViewModel.logInUser(user)

        // Assert: The keypass should be null, and errors should be the timeout exception
        assertNull(keypass.value)
        assertEquals(exceptions.timeout, errors.value)
    }
}

//    @Test
//    fun `logInUser with valid user should return correct keypass`() = runTest {
//        // Given: A valid user and expected Keypass
//        val user = User("Francois", "s8126540")
//        val expectedKeypass = Keypass("courses")
//
//        // When: Mocking the repository to return the expected Keypass
//        coEvery { repository.addUser(user) } returns expectedKeypass
//
//        // Act: Call the logInUser function
//        val (keypass, errors) = loginViewModel.logInUser(user)
//
//        // Assert: The keypass should be the expected value and no errors should occur
//        assertEquals(expectedKeypass, keypass.value)
//        assertNull(errors.value)
//
//        // Verify that addUser was called exactly once
//        coVerify(exactly = 1) { repository.addUser(user) }
//    }
//
//    @Test
//    fun `logInUser with invalid user should return error 404`() = runTest {
//        // Given: An invalid user
//        val user = User("123", "s12345678")
//        val httpException = mockk<retrofit2.HttpException> {
//            every { code() } returns 404
//        }
//
//        // Mock exceptions for 404
//        every { exceptions.noSuchDetails } returns Exception("No such details")
//
//        // When: Mocking the repository to throw an HttpException
//        coEvery { repository.addUser(user) } throws httpException
//
//        // Act: Call the logInUser function
//        val (keypass, errors) = loginViewModel.logInUser(user)
//
//        // Assert: The keypass should be null and errors should contain the 404 exception
//        assertNull(keypass.value)
//        assertEquals(exceptions.noSuchDetails, errors.value)
//
//        // Verify that addUser was called exactly once
//        coVerify(exactly = 1) { repository.addUser(user) }
//    }
//
//    @Test
//    fun `logInUser should handle timeout exception`() = runTest {
//        // Given: A valid user and a timeout exception
//        val user = User("Francois", "s8126540")
//
//        // Mocking the SocketTimeoutException
//        every { exceptions.timeout } returns Exception("Timeout occurred")
//
//        // When: Mocking the repository to throw a SocketTimeoutException
//        coEvery { repository.addUser(user) } throws java.net.SocketTimeoutException()
//
//        // Act: Call the logInUser function
//        val (keypass, errors) = loginViewModel.logInUser(user)
//
//        // Assert: The keypass should be null and the error should be the timeout exception
//        assertNull(keypass.value)
//        assertEquals(exceptions.timeout, errors.value)
//
//        // Verify that addUser was called exactly once
//        coVerify(exactly = 1) { repository.addUser(user) }
//    }

