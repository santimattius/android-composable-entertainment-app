package com.santimattius.template.data.client.database

import androidx.room.*
import com.santimattius.template.data.entities.TvShowEntity

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_show")
    suspend fun getAll(): List<TvShowEntity>

    @Query("SELECT * FROM tv_show WHERE id=:id")
    suspend fun findById(id: Int): TvShowEntity?

    @Query("SELECT COUNT(id) FROM tv_show")
    fun count(): Int

    @Insert
    suspend fun insertAll(vararg movies: TvShowEntity)

    @Query("DELETE FROM tv_show")
    fun deleteAll()

    @Delete
    suspend fun delete(tvShow: TvShowEntity): Int

    @Transaction
    suspend fun deleteAndInsert(vararg tvShows: TvShowEntity) {
        deleteAll()
        insertAll(*tvShows)
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(tvShow: TvShowEntity): Int

}