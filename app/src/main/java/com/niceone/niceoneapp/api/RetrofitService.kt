package com.niceone.niceoneapp.api
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitService {


    companion object {
        @Volatile private var instance: ApiInterface? = null

        fun getInstance(): ApiInterface {
            return instance ?: synchronized(this) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://185.118.165.197/api/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                instance ?:  retrofit.create(ApiInterface::class.java).also { instance = it }

            }

        }
    }
}