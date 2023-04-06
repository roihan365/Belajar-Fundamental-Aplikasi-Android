package com.example.submissionroihan2.api

import com.example.submissionroihan2.GithubResponse
import com.example.submissionroihan2.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun detail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authentication: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}