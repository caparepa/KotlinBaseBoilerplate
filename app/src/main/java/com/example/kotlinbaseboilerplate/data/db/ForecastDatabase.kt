package com.example.kotlinbaseboilerplate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherEntry
import com.example.kotlinbaseboilerplate.data.db.entity.WeatherLocation
import com.example.kotlinbaseboilerplate.utils.StringListConverter

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class ForecastDatabase : RoomDatabase() {

    //We create abstract functions for each DAO created
    abstract fun currentWeatherDao(): CurrentWeatherEntryDao
    abstract fun currentLocationDao(): WeatherLocationDao

    //We create a companion object that will act as singleton in order to create a single instance
    //of the database
    companion object {

        //@Volatile annotation allows all of the threads to have access to the property (instance)
        @Volatile
        private var instance: ForecastDatabase? = null

        //Lock object for the threads
        private val LOCK = Any()

        //We create an operator function to initialize the database
        //If there is an instance, return it (instance ?:)
        //else, syncronize a block with a lock, check again if there is an instance,

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //if there is no instance, build a database (buildDatabase(context))
            //and also, whatever is returned from the builder, set the instance equal to "it"
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        //We create a function to build the database, which will receive the application context
        ///This builder will initialize the database java class for the app (ForecastDatabase)
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java, "forecast.db"
            ).build()

    }
}