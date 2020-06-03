package id.practice.githubuser.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig {
    companion object{
        const val KEY = "242d91c646ab45ef8f604490892b1a4e6898f8e7"

        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}