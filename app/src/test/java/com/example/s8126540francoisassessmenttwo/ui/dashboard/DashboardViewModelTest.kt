package com.example.s8126540francoisassessmenttwo.ui.dashboard

import org.junit.jupiter.api.Assertions.*
import android.util.Log
import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.example.s8126540francoisassessmenttwo.data.User
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
class DashboardViewModelTest {
    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: RestfulApiDevRepositoryClass
    private lateinit var exceptions: Exceptions
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Mock the repository and exceptions
        repository = mockk()
        exceptions = mockk()

        // Set the dispatcher for the ViewModel's scope
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0

        // Initialize the ViewModel
        viewModel = DashboardViewModel(repository, exceptions)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the Main dispatcher to the original one
    }

    @Test
    fun `getData with valid keypass should return correct data`() = runTest {
        // Given: A valid keypass and expected ItemData
        val keypass = Keypass("valid_keypass")
        val expectedItemData = ItemData(listOf(), 0)

        // When: Mocking the repository to return the expected ItemData
        coEvery { repository.getAllObjectsData(keypass) } returns expectedItemData

        // Act: Call the getData function
        val (responseData, errors) = viewModel.getData(keypass)

        // Assert: The responseData should be the expected value and no errors should occur
        assertEquals(expectedItemData, responseData.value)
        assertNull(errors.value)
    }

    @Test
    fun `getData should handle SocketTimeoutException`() = runTest {
        // Given: A valid keypass and a SocketTimeoutException
        val keypass = Keypass("timeout_keypass")
        every { exceptions.timeout } returns Exception("Timeout occurred")

        // When: Mocking the repository to throw a SocketTimeoutException
        coEvery { repository.getAllObjectsData(keypass) } throws SocketTimeoutException()

        // Act: Call the getData function
        val (responseData, errors) = viewModel.getData(keypass)

        // Assert: The responseData should be null and the error should be the timeout exception
        assertNull(responseData.value)
        assertEquals(exceptions.timeout, errors.value)
    }

    @Test
    fun `getData should handle 404 HttpException`() = runTest {
        // Given: A keypass and a 404 HttpException
        val keypass = Keypass("not_found_keypass")
        val httpException = mockk<HttpException> {
            every { code() } returns 404
        }
        every { exceptions.noSuchDetails } returns Exception("No such details")

        // When: Mocking the repository to throw an HttpException
        coEvery { repository.getAllObjectsData(keypass) } throws httpException

        // Act: Call the getData function
        val (responseData, errors) = viewModel.getData(keypass)

        // Assert: The responseData should be null and errors should contain the 404 exception
        assertNull(responseData.value)
        assertEquals(exceptions.noSuchDetails, errors.value)
    }

    @Test
    fun `getData should handle JsonDataException`() = runTest {
        // Given: A keypass and a JsonDataException
        val keypass = Keypass("json_error_keypass")
        every { exceptions.jsonError } returns Exception("JSON parsing error")

        // When: Mocking the repository to throw a JsonDataException
        coEvery { repository.getAllObjectsData(keypass) } throws JsonDataException()

        // Act: Call the getData function
        val (responseData, errors) = viewModel.getData(keypass)

        // Assert: The responseData should be null and the error should be the JsonDataException
        assertNull(responseData.value)
        assertEquals(exceptions.jsonError, errors.value)
    }

    @Test
    fun `getData should handle UnknownHostException`() = runTest {
        // Given: A keypass and an UnknownHostException
        val keypass = Keypass("offline_keypass")
        every { exceptions.offline } returns Exception("No internet connection")

        // When: Mocking the repository to throw an UnknownHostException
        coEvery { repository.getAllObjectsData(keypass) } throws UnknownHostException()

        // Act: Call the getData function
        val (responseData, errors) = viewModel.getData(keypass)

        // Assert: The responseData should be null and the error should be the UnknownHostException
        assertNull(responseData.value)
        assertEquals(exceptions.offline, errors.value)
    }
}