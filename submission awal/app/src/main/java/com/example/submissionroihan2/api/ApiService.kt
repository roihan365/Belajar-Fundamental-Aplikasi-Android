package com.example.submissionroihan2.api

import com.example.submissionroihan2.GithubResponse
import com.example.submissionroihan2.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authentication: token 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")
    fun getGithubUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun detail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}