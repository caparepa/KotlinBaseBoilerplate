package com.example.kotlinbaseboilerplate.data.db.weatherbit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitWeatherDescription

@Database(
    entities = [BitCurrentWeatherData::class, BitWeatherDescription::class],
    version = 1
)
abstract class BitWeatherDatabase : RoomDatabase() {

    abstract fun getCurrentWeatherDataDao() : BitCurrentWeatherDataDao
    abstract fun getWeatherDescriptionDao() : BitWeatherDescriptionDao

    //We create a companion object that will act as singleton in order to create a single instance
    //of the database
    companion object {

        //@Volatile annotation allows all of the threads to have access to the property (instance)
        @Volatile
        private var instance: BitWeatherDatabase? = null

        //Lock object for the threads
        private val LOCK = Any()

        //We create an operator function to initialize the database
        //If there is an instance, return it (instance ?:)
        //else, syncronize a block with a lock, check again if there is an instance,

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                //if there is no instance, build a database (buildDatabase(context))
                //and also, whatever is returned from the builder, set the instance equal to "it"
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        //We create a function to build the database, which will receive the application context
        ///This builder will initialize the database java class for the app (ForecastDatabase)
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BitWeatherDatabase::class.java, "bitweather.db"
            ).build()

    }

}