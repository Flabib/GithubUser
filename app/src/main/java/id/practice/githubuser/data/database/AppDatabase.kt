package id.practice.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.practice.githubuser.App
import id.practice.githubuser.data.database.favorite.Favorite
import id.practice.githubuser.data.database.favorite.FavoriteDao

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favorite() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private lateinit var appContext: Context

        fun getDatabase(context: Context? = null) : AppDatabase {
            if (context != null ) {
                appContext = context
            } else {
                appContext = App.context!!
            }

            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "github_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}