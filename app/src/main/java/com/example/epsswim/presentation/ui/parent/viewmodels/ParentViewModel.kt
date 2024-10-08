package com.example.epsswim.presentation.ui.parent.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epsswim.data.model.app.pfp.PfpResponse
import com.example.epsswim.data.model.app.swimmer.Children
import com.example.epsswim.data.model.requestBody.pfp.swimmer.SwimmerPfpVariables
import com.example.epsswim.data.model.requestBody.pfp.trainer.TrainerPfpVariables
import com.example.epsswim.data.model.requestBody.swimmer.Query
import com.example.epsswim.data.repositories.ParentRepository
import com.example.epsswim.data.utils.Queries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(
    private val parentRepository: ParentRepository,

) : ViewModel()  {
    private val _swimmerList = MutableStateFlow<Children?>(null)
    val swimmerList: StateFlow<Children?> = _swimmerList

    private val _isNotConnected = mutableStateOf(false)
    val isNotConnected : State<Boolean> = _isNotConnected

    init {
        getSwimmers()
    }

     fun getSwimmers() {
        viewModelScope.launch {
            parentRepository.getParentSwimmers(Query(Queries.GET_PARENT_SWIMMERS)).enqueue(object :
                Callback<Children> {
                override fun onResponse(call: Call<Children>, response: Response<Children>) {
                    if (response.isSuccessful) {
                        _isNotConnected.value = false
                        _swimmerList.value = response.body()
                    } else {
                        Log.d("ParentApi", "onResponse: failed fetch data ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Children>, t: Throwable) {
                    _isNotConnected.value = true
                    Log.d("ParentApi", "onFailure: failed fetch data, check your internet connection ${t.message}")
                }
            })
        }
    }

    fun updateSwimmerPfp(swimmerid: String, pfpUrl: String){
        viewModelScope.launch {
            parentRepository.updateSwimmerPfp(
                com.example.epsswim.data.model.requestBody.pfp.swimmer.Query(
                    query = Queries.UPLOAD_SWIMMER_PHOTO_PROFILE,
                    variables = SwimmerPfpVariables(
                        swimmerid = swimmerid,
                        pfpUrl = pfpUrl
                    )
                )
            ).enqueue(object : Callback<PfpResponse> {
                override fun onResponse(call: Call<PfpResponse>, response: Response<PfpResponse>) {
                    if (response.isSuccessful) {
                        Log.d("UpdatePicApi", "onResponse: success fetch data ${response.body()}")
                    } else {
                        Log.d("UpdatePicApi", "onResponse: failed fetch data ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PfpResponse>, t: Throwable) {
                    Log.d("UpdatePicApi", "onFailure: failed fetch data, check your internet connection ${t.message}")
                }
            })
        }

    }
}