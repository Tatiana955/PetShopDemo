package by.petshop.petshopdemo.RemoteModel

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

val BASE_URL = "http://newgen-pet-shop.herokuapp.com/"

interface ShopApiService {

    @GET("catalog")
    suspend fun getCatalog(): MutableList<Map<String, Any>>

    companion object Factory {
        fun create(): ShopApiService {
            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(ShopApiService::class.java)
        }
    }
}