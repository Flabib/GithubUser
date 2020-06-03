package id.practice.githubuser.views.fragment.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.practice.githubuser.data.database.favorite.Favorite
import id.practice.githubuser.data.database.favorite.FavoriteRepository
import id.practice.githubuser.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val repository: FavoriteRepository
    val favorites = MutableLiveData<List<Favorite>>()

    init {
        val favoriteDao = AppDatabase.getDatabase().favorite()
        repository = FavoriteRepository(favoriteDao)
    }

    fun read() = viewModelScope.launch(Dispatchers.IO) {
        favorites.postValue(repository.read())
    }

    fun read(login: String) = viewModelScope.launch(Dispatchers.IO) {
        favorites.postValue(repository.read(login))
    }
}