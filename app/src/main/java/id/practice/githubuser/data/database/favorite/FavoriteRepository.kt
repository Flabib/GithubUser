package id.practice.githubuser.data.database.favorite

import android.database.Cursor

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    fun read() : List<Favorite> = favoriteDao.read()
    fun read(login: String) : List<Favorite> = favoriteDao.read(login)
    fun num(login: String) : Int = favoriteDao.num(login)

    fun readCursor() : Cursor = favoriteDao.readCursor()
    fun readCursor(login: String) : Cursor = favoriteDao.readCursor(login)

    fun insert(favorite: Favorite) = favoriteDao.insert(favorite)
    fun delete(login: String) = favoriteDao.delete(login)
    fun delete() = favoriteDao.delete()
}