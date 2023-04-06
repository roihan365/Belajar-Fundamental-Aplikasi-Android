package com.example.submissionroihan2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = "",
)