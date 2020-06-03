package id.practice.githubuser.views.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.practice.githubuser.data.database.favorite.Favorite
import id.practice.githubuser.data.database.favorite.FavoriteRepository
import id.practice.githubuser.data.database.AppDatabase
import id.practice.githubuser.data.model.User
import id.practice.githubuser.data.network.APIConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val follower = MutableLiveData<ArrayList<User>>()
    private val following = MutableLiveData<ArrayList<User>>()
    private val repository: FavoriteRepository
    private val user = MutableLiveData<User>()
    private val favorite = MutableLiveData<Boolean>()

    init {
        val favoriteDao = AppDatabase.getDatabase().favorite()
        repository = FavoriteRepository(favoriteDao)
    }

    fun setFollower(username : String) {
        val client = APIConfig.getApiService().getFollower(username)
        val listFollower = ArrayList<User>()

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                val dataArray = response.body() as List<User>

                for (data in dataArray) {
                    listFollower.add(data)
                }

                follower.postValue(listFollower)
            }

        })
    }

    fun setFollowing(username : String) {
        val client = APIConfig.getApiService().getFollowing(username)
        val listFollowing = ArrayList<User>()

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                val dataArray = response.body() as List<User>

                for (data in dataArray) {
                    listFollowing.add(data)
                }

                following.postValue(listFollowing)
            }

        })
    }

    fun setUser(username : String) = viewModelScope.launch(Dispatchers.IO) {
        val client = APIConfig.getApiService().getUser(username)

        if (repository.num(username) == 1) favorite.postValue(true)
        else favorite.postValue(false)

        client.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                user.postValue(response.body())
            }

        })
    }

    fun getFollower() : LiveData<ArrayList<User>> = follower
    fun getFollowing() : LiveData<ArrayList<User>> = following
    fun getUser() : LiveData<User> = user
    fun getFavorite() : LiveData<Boolean> = favorite

    fun insert(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(favorite)
    }

    fun delete(login: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(login)
    }
}