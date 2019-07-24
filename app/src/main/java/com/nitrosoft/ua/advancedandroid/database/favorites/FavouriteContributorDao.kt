package com.nitrosoft.ua.advancedandroid.database.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface FavouriteContributorDao {

    @Query("SELECT * from favorite_contributors")
    fun getFavouritedContributors(): Flowable<FavouriteContributor>

    @Insert
    fun addFavotite(favouriteContributor: FavouriteContributor)

    @Delete
    fun deleteFavotite(favouriteContributor: FavouriteContributor)
}