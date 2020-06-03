package id.practice.githubuser.data.database.favorite

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.practice.githubuser.data.database.favorite.Favorite.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Favorite (
    @PrimaryKey
    @ColumnInfo(name = COLUMN_LOGIN)
    val login: String,

    @ColumnInfo(name = COLUMN_AVATAR_URL)
    val avatar_url: String
) {
    companion object {
        const val TABLE_NAME = "favorites"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_AVATAR_URL = "avatar_url"

        fun fromContentValues(values: ContentValues?): Favorite {
            if (values != null) {
                return Favorite(
                    values.getAsString(COLUMN_LOGIN) as String,
                    values.getAsString(COLUMN_AVATAR_URL) as String
                )
            } else {
                return Favorite("", "")
            }
        }
    }
}