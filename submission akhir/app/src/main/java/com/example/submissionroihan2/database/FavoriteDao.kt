package com.example.submissionroihan2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getFavoriteList(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM favorite WHERE favorite.username = :username")
    suspend fun removeFavorite(username: String)
}