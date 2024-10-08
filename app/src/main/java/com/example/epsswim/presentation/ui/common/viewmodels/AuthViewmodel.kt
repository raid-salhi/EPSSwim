package com.example.epsswim.presentation.ui.common.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.epsswim.data.model.auth.LoginBody
import com.example.epsswim.data.model.auth.LoginResponse
import com.example.epsswim.data.repositories.AuthRepository
import com.example.epsswim.data.repositories.tokenRepository.JwtTokenDataStore
import com.example.epsswim.data.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel  @Inject constructor(
    private val authRepo: AuthRepository,
    private val jwtTokenDataStore: JwtTokenDataStore
) : ViewModel()  {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> = _role

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn
    private val _isIncorrectCredentials = mutableStateOf(false)
    val isIncorrectCredentials: State<Boolean> = _isIncorrectCredentials
    private val _isNotConnected = mutableStateOf(false)
    val isNotConnected : State<Boolean> = _isNotConnected


    init {
        viewModelScope.launch {
            _token.value = jwtTokenDataStore.getAccessJwt()
            checkIfUserLoggedIn()
        }
    }

    fun login(loginBody: LoginBody) {
        authRepo.login(loginBody).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _token.value = response.body()?.token
                    _role.value = Utils.getRoleFromToken(response.body()?.token!!)
                    viewModelScope.launch {
                        jwtTokenDataStore.saveAccessJwt(token.value!!)
                    }
                    _isIncorrectCredentials.value = false
                    _isNotConnected.value = false
                    Log.d("AuthApi", "onResponse: ${response.body()}")
                } else {
                    _isIncorrectCredentials.value = true
                    _isNotConnected.value = false
                    Log.d("AuthApi", "onResponse: failed fetch data ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isNotConnected.value = true
                _isIncorrectCredentials.value = false

                Log.d("AuthApi", "onFailure: failed fetch data, check your internet connection ${t.message}")
            }
        })
    }
    fun logout(){
        viewModelScope.launch {
            _token.value = null
            _role.value = null
            _isLoggedIn.value = false
            _isIncorrectCredentials.value = false
            _isNotConnected.value = false
            jwtTokenDataStore.clearAllTokens()
        }
    }
    private fun checkIfUserLoggedIn(){
        if (token.value != null)
            _isLoggedIn.value = ! JWT(token.value!!).isExpired(10)
        else
            _isLoggedIn.value = false
    }
     fun getRole(){
        viewModelScope.launch {
            _role.value = jwtTokenDataStore.getRole()
        }
    }
}