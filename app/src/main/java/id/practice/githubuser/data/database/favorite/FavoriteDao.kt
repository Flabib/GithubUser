package id.practice.githubuser.data.database.favorite

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * from favorites ORDER BY login ASC")
    fun read(): List<Favorite>

    @Query("SELECT * from favorites WHERE (login LIKE '%' || :login || '%') ORDER BY login ASC")
    fun read(login: String? = null): List<Favorite>

    @Query("SELECT * from favorites ORDER BY login ASC")
    fun readCursor(): Cursor

    @Query("SELECT * from favorites WHERE (login LIKE '%' || :login || '%') ORDER BY login ASC")
    fun readCursor(login: String? = null): Cursor

    @Query("SELECT COUNT(*) from favorites WHERE login = :login")
    fun num(login: String? = null): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE login = :login")
    fun delete(login: String?)

    @Query("DELETE FROM favorites")
    fun delete()
}