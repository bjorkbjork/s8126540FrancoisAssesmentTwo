package s8126540francoisassessmenttwo

import com.example.s8126540francoisassessmenttwo.data.RegisterNewUsers
//import org.junit.jupiter.api.Assertions.*
import org.junit.Assert.*
import org.junit.Test

class RegisterNewUsersTest{

    lateinit var registerNewUsers: RegisterNewUsers


    @Test
    fun `empty username will return false`(){
        registerNewUsers = RegisterNewUsers()

        val result = registerNewUsers.verifyNewUsers("",
            "123",
            "123")
        assertFalse(result)
    }

    @Test
    fun `valid input will return true`(){
        registerNewUsers = RegisterNewUsers()

        val result = registerNewUsers.verifyNewUsers("user",
            "123",
            "123")
        assertTrue(result)
    }

    @Test
    fun `non-matching passwords will return false`(){
        registerNewUsers = RegisterNewUsers()

        val result = registerNewUsers.verifyNewUsers("user",
            "123",
            "456")

        assertFalse(result)
    }

}