package com.niceone.niceoneapp.api

import com.niceone.niceoneapp.models.Category
import com.niceone.niceoneapp.models.WorkshopPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("specialities")
    fun getCategories(): Call<List<Category>>

    @GET("search/{id}")
    fun getWorkshops(@Path("id") id:String,@Query("page") pageNumber:String): Call<WorkshopPage>
}