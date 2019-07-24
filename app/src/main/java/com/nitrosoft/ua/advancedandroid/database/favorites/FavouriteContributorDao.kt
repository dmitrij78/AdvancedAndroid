package com.nitrosoft.ua.advancedandroid.database.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface FavouriteContributorDao {

    @Query("SELECT * from favorite_contributors")
    fun getFavoriteContributors(): Flowable<List<FavouriteContributor>>

    @Insert
    fun addFavorite(favouriteContributor: FavouriteContributor)

    @Delete
    fun deleteFavorite(favouriteContributor: FavouriteContributor)
}