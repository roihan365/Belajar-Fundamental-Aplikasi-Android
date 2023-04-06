package com.example.submissionroihan2.api

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("following_url")
	val followingUrl: String?= "",

	@field:SerializedName("login")
	val login: String? = "",

	@field:SerializedName("id")
	val id: Int?= 0,

	@field:SerializedName("followers_url")
	val followersUrl: String?= "",

	@field:SerializedName("followers")
	val followers: Int?= 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String?= "",

	@field:SerializedName("following")
	val following: Int?= 0,

	@field:SerializedName("name")
	val name: String?= "",
)
