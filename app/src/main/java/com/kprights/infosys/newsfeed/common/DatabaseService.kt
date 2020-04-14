package com.kprights.infosys.newsfeed.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.kprights.infosys.newsfeed.model.News


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 2:09 AM
 */

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class DatabaseService : RoomDatabase()
{
    abstract val newsFeedDao: NewsFeedDao

    companion object{
        @Volatile
        private var INSTANCE: DatabaseService? = null

        fun getInstance(context: Context) : DatabaseService
        {
            var instance = INSTANCE

            if(instance == null)
            {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseService::class.java,
                    "news_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
            }

            return instance
        }
    }
}

@Dao
interface NewsFeedDao
{
    @Insert
    fun insert(model: News)

    @Update
    fun update(model: News)

    @Query("SELECT * FROM news WHERE Id = :key")
    fun get(key: Long): News

    @Query("DELETE FROM news")
    fun clear()

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<News>>
}