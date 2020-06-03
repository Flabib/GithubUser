package id.practice.githubuser.views.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import id.practice.githubuser.R
import id.practice.githubuser.data.model.Search
import id.practice.githubuser.data.model.User
import id.practice.githubuser.data.network.APIConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val users = MutableLiveData<ArrayList<User>>()
    private var second = false

    fun setUsers(username: String) {
        val client = APIConfig.getApiService().getSearch(username)
        val listUsers = ArrayList<User>()

        client.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                val dataArray = response.body()?.items as List<User>

                for (data in dataArray) {
                    listUsers.add(data)
                }

                users.postValue(listUsers)
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return users
    }

    fun buildUsers(context: Context) {
        if (second) return
        GlobalScope.launch {
            val listUsers = ArrayList<User>()
            val jsonSource = context.resources.openRawResource(R.raw.github_users).reader()
            val json = JsonParser.parseReader(jsonSource).asJsonArray

            for (i in 0 until json.size()) {
                listUsers.add(Gson().fromJson(json[i].asJsonObject, User::class.java))
            }

            second = true
            users.postValue(listUsers)
        }
    }
}