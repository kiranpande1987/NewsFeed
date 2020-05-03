package com.kprights.infosys.newsfeed.common

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.kprights.infosys.newsfeed.model.NewsFeed


/**
 * Copyright (c) 2020 for KPrights
 *
 * User : Kiran Pande
 * Date : 11/04/20
 * Time : 2:09 AM
 */

@Database(entities = [NewsFeed::class], version = 1, exportSchema = false)
@TypeConverters(NewsFeed.Converters::class)
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
    fun insert(newsFeed: NewsFeed)

    @Update
    fun update(newsFeed: NewsFeed)

    @Query("DELETE FROM NewsFeed")
    fun clear()

    @Query("SELECT * FROM NewsFeed")
    fun getAllNews(): LiveData<NewsFeed>
}