package com.example.submissionroihan2.viewModel
//
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionroihan2.api.ApiConfig
import com.example.submissionroihan2.api.DetailUserResponse
import com.example.submissionroihan2.database.FavoriteDao
import com.example.submissionroihan2.database.FavoriteEntity
import com.example.submissionroihan2.database.FavoriteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application){
    private val _listDetail = MutableLiveData<DetailUserResponse>()
    val listDetail: LiveData<DetailUserResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var favoriteDao: FavoriteDao
    private var db: FavoriteRoomDatabase

    init {
        db = FavoriteRoomDatabase.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

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

    fun addToFavorite(username: String, foto: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                username,
                foto
            )
            favoriteDao.addFavorite(user)
        }
    }

    fun getFavoriteList() = favoriteDao.getFavoriteList()
    fun remove(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao.removeFavorite(username)
        }
    }
}