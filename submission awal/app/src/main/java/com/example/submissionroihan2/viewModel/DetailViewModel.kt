package com.faresa.githubsearchuser.viewmodel
//
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionroihan2.api.ApiConfig
import com.example.submissionroihan2.api.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//
//class DetailViewModel : ViewModel() {
//    private var Userdata: MutableLiveData<DetailUserResponse?>? = null
//    fun loadEvent(username: String) {
//        try {
//            val client = ApiConfig.getApiService()
//            val eventCall: Call<DetailUserResponse> = client.detail(username)
//            eventCall.enqueue(object : Callback<DetailUserResponse> {
//                private var response: Response<DetailUserResponse>? = null
//                override fun onResponse(
//                    call: Call<DetailUserResponse>,
//                    response: Response<DetailUserResponse>
//                ) {
//                    val responseBody = response.body()
////                    this.response = response
////                    Userdata!!.setValue(response.body())
//                }
//
//                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
//                    Log.e("failure", t.toString())
//                }
//            })
//        } catch (e: Exception) {
//            Log.d("token e", e.toString())
//        }
//    }
//
//    fun getData(): LiveData<DetailUserResponse?>? {
//        if (Userdata == null) {
//            Userdata = MutableLiveData<DetailUserResponse?>()
//        }
//        return Userdata
//    }
//
//}

class DetailViewModel : ViewModel() {
    private val _listDetail = MutableLiveData<DetailUserResponse>()
    val listDetail: LiveData<DetailUserResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    internal fun getGithubUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detail(login)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailures: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureawd: ${t.message}")
            }
        })
    }
}