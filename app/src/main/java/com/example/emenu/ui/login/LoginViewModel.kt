package com.example.emenu.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.emenu.data.LoginRepository
import com.example.emenu.data.Result
import com.example.emenu.R
import com.example.emenu.data.model.ItemList
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    private val s = "LoginViewModel"

    private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        authenticate(username, password)
        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun authenticate(username: String, password: String): Int{
        var flag : Int = 0
        db!!.collection("LoginAccount")
            .whereEqualTo("Username", username).whereEqualTo("Password", password)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.data["Username"].toString()
                    Log.d(s, "${document.id} => ${document.data["Username"]}")

                }
                flag = 1
            }
            .addOnFailureListener {
                flag = 0
            }

        return flag
    }
}
