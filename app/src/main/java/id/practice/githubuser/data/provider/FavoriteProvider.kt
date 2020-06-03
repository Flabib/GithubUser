package id.practice.githubuser.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.practice.githubuser.data.database.favorite.FavoriteRepository
import id.practice.githubuser.data.database.AppDatabase
import id.practice.githubuser.data.database.favorite.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteProvider : ContentProvider() {

    companion object {
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private lateinit var repository: FavoriteRepository

        private const val SCHEME = "content"
        private const val AUTHORITY = "id.practice.githubuser"
        private const val TABLE_NAME = "favorite"

        init {
            // content://id.practice.githubuser/favorite
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            // content://id.practice.githubuser/favorite/id
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
        }

        // content://id.practice.githubuser/favorite
        val CONTENT_URI: Uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        repository.delete(uri.lastPathSegment.toString())

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        repository.insert(Favorite.fromContentValues(values))

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return null
    }

    override fun onCreate(): Boolean {
        val favorite = AppDatabase.getDatabase(context as Context).favorite()
        repository = FavoriteRepository(favorite)

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?

        when (uriMatcher.match(uri)) {
            FAVORITE -> cursor = repository.readCursor()
            FAVORITE_ID -> cursor = repository.readCursor(uri.lastPathSegment.toString())
            else -> cursor = null
        }

        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
