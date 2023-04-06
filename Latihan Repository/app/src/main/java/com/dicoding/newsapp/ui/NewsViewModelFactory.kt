package com.dicoding.newsapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.di.Injection

class NewsViewModelFactory private constructor(private val newsRepository: NewsRepository) :
ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: NewsViewModelFactory? = null
        fun getInstance(context: Context): NewsViewModelFactory =
            instance ?: synchronized(this) {
                instance?: NewsViewModelFactory(Injection.providerRepository(context))
            }.also { instance = it }
    }
}