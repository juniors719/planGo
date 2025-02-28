package com.example.plango.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plango.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    var loginResult: ((Boolean) -> Unit)? = null
    var registerResult: ((Boolean) -> Unit)? = null

    data class UserState(
        val name: String? = "Loading...",
        val email: String? = "Loading...",
        val isLoading: Boolean = false
    )

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    fun loadUserData() {
        _userState.value = _userState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val nameDeferred = async { repository.getUserName() }
                val emailDeferred = async { repository.getUserEmail() }

                _userState.value = UserState(
                    name = nameDeferred.await(),
                    email = emailDeferred.await(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _userState.value = _userState.value.copy(isLoading = false)
                // Trate o erro
            }
        }
    }

    var isLoading by mutableStateOf(false)
        private set

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            val success = repository.loginUser(email, password)
            isLoading = false
            onResult(success) // Retorna true ou false para a tela de login
        }
    }

    fun resetPassword(email: String, onResult: (Boolean) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            val success = repository.resetPassword(email)
            isLoading = false
            onResult(success)
        }
    }

    fun getUserName(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val name = repository.getUserName()
            onResult(name)
        }
    }

    fun getUserEmail(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val email = repository.getUserEmail()
            onResult(email)
        }
    }

    fun loginWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            val success = repository.loginWithGoogle(idToken)
            isLoading = false
            onResult(success)
        }
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        return repository.getGoogleSignInClient(context)
    }

    fun logout() {
        repository.logout()
    }

    fun register(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            val success = repository.registerUser(email, password, name)
            isLoading = false
            onResult(success)
        }
    }

    fun editName(name: String, onResult: (Boolean) -> Unit) {
        isLoading = true
        viewModelScope.launch {
            val success = repository.editProfileName(name)
            isLoading = false
            onResult(success)
        }
    }

    fun isUserLogged(): Boolean {
        return repository.isUserLogged()
    }

}