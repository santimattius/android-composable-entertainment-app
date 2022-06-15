package com.santimattius.template.data.client.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Transaction
import androidx.room.OnConflictStrategy
import com.santimattius.template.data.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id=:id")
    suspend fun findById(id: Int): MovieEntity?

    @Query("SELECT COUNT(id) FROM movie")
    fun count(): Int

    @Insert
    suspend fun insertAll(vararg movies: MovieEntity)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Delete
    suspend fun delete(movies: MovieEntity)

    @Transaction
    suspend fun deleteAndInsert(vararg movies: MovieEntity) {
        deleteAll()
        insertAll(*movies)
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movies: MovieEntity)

}