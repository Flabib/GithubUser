package id.practice.githubuser.data.network

import id.practice.githubuser.data.model.Search
import id.practice.githubuser.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${APIConfig.KEY}")
    fun getSearch(@Query("q") q : String): Call<Search>

    @GET("users/{username}")
    @Headers("Authorization: token ${APIConfig.KEY}")
    fun getUser(@Path(value = "username", encoded = true) username : String): Call<User>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${APIConfig.KEY}")
    fun getFollowing(@Path(value = "username", encoded = true) username : String): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${APIConfig.KEY}")
    fun getFollower(@Path(value = "username", encoded = true) username : String): Call<ArrayList<User>>
}