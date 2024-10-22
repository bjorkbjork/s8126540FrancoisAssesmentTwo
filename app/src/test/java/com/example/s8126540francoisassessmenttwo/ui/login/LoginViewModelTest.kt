package com.example.s8126540francoisassessmenttwo.ui.login

import android.util.Log
import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.example.s8126540francoisassessmenttwo.data.User
import com.example.s8126540francoisassessmenttwo.ui.login.LoginViewModel
import com.squareup.moshi.JsonDataException
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
import java.net.UnknownHostException


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: RestfulApiDevRepositoryClass
    private lateinit var exceptions: Exceptions
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Mock the repository
        exceptions = mockk()
        repository = mockk()

        // Set the dispatcher for the ViewModel's scope
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0

        // Initialize the ViewModel
        viewModel = LoginViewModel(repository, exceptions)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the Main dispatcher to the original one
    }

    @Test
    fun `logInUser with valid user should return correct keypass`() = runTest {
        // Given: A valid user and expected Keypass
        val user = User("Francois", "s8126540")
        val expectedKeypass = Keypass("courses")

        // When: Mocking the repository to return the expected Keypass
        coEvery { repository.addUser(user) } returns expectedKeypass

        // Act: Call the logInUser function
        val (keypass, errors) = viewModel.logInUser(user)

        // Assert: The keypass should be the expected value and no errors should occur
        assertEquals(expectedKeypass, keypass.value)
        assertNull(errors.value)
    }

    @Test
    fun `logInUser with invalid user should return error 404`() = runTest {
        // Given: An invalid user
        val user = User("123", "s12345678")
        val httpException = mockk<retrofit2.HttpException> {
            every { code() } returns 404
        }

        // Mock exceptions for 404
        every { exceptions.noSuchDetails } returns Exception("No such details")

        // When: Mocking the repository to throw an HttpException
        coEvery { repository.addUser(user) } throws httpException

        // Act: Call the logInUser function
        val (keypass, errors) = viewModel.logInUser(user)

        // Assert: The keypass should be null and errors should contain the 404 exception
        assertNull(keypass.value)
        assertEquals(exceptions.noSuchDetails, errors.value)
    }

    @Test
    fun `logInUser should handle timeout exception`(): Unit = runTest {
        // Given: A valid user and a timeout exception
        val user = User("Francois", "s8126540")

        // Mocking the SocketTimeoutException
        every { exceptions.timeout } returns Exception("Timeout occurred")

        // When: Mocking the repository to throw a SocketTimeoutException
        coEvery { repository.addUser(user) } throws SocketTimeoutException()

        // Act: Call the logInUser function
        val (keypass, errors) = viewModel.logInUser(user)

        // Assert: The keypass should be null and the error should be the timeout exception
        assertNull(keypass.value)
        assertEquals(exceptions.timeout, errors.value)
    }

    @Test
    fun `getData should handle JsonDataException`() = runTest {
        // Given: A keypass and a JsonDataException
        val user = User("Francois", "s8126540")

        every { exceptions.jsonError } returns Exception("JSON parsing error")

        // When: Mocking the repository to throw a JsonDataException
        coEvery { repository.addUser(user) } throws JsonDataException()

        // Act: Call the logIn function
        val (keypass, errors) = viewModel.logInUser(user)

        // Assert: The keypass should be null and the error should be the JsonDataException
        assertNull(keypass.value)
        assertEquals(exceptions.jsonError, errors.value)
    }

    @Test
    fun `getData should handle UnknownHostException`() = runTest {
        // Given: Valid user and mocked exception
        val user = User("Francois", "s8126540")

        every { exceptions.offline } returns Exception("No internet connection")

        // When: Mocking the repository to throw an UnknownHostException
        coEvery { repository.addUser(user) } throws UnknownHostException()

        // Act: Call the logIn function
        val (keypass, errors) = viewModel.logInUser(user)

        // Assert: The keypass should be null and the error should be the UnknownHostException
        assertNull(keypass.value)
        assertEquals(exceptions.offline, errors.value)
    }
}
