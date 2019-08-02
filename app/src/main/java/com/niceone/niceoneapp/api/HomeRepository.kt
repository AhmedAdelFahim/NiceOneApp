package com.niceone.niceoneapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.niceone.niceoneapp.models.Category
import com.niceone.niceoneapp.models.WorkshopPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private var categoryListLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    private var workshopPageLiveData: MutableLiveData<WorkshopPage> = MutableLiveData()
    private var handleNetworkError: MutableLiveData<HashMap<String,Boolean>> = MutableLiveData()

    fun setCategories(categories:List<Category>?)
    {
        categoryListLiveData.value = categories
    }

    fun getNetworkError():MutableLiveData<HashMap<String,Boolean>>
    {
        return handleNetworkError
    }

    fun getWorkshops(id:String,pageNumber:String): MutableLiveData<WorkshopPage> {

        RetrofitService.getInstance().getWorkshops(id,pageNumber).enqueue(object : Callback<WorkshopPage>{
            override fun onFailure(call: Call<WorkshopPage>, t: Throwable) {
                var map: HashMap<String, Boolean>? = handleNetworkError.value ?: HashMap<String, Boolean>()
                map?.set("workshop", false)
                handleNetworkError.value = map
            }

            override fun onResponse(call: Call<WorkshopPage>, response: Response<WorkshopPage>) {
                if (response.isSuccessful)
                {
                    var map: HashMap<String, Boolean>? = handleNetworkError.value ?: HashMap<String, Boolean>()
                    map?.set("workshop", true)
                    handleNetworkError.value = map
                    workshopPageLiveData.value = response.body()
                }

            }

        })

        return workshopPageLiveData
    }

    fun getCategories(): MutableLiveData<List<Category>> {


        RetrofitService.getInstance().getCategories().enqueue(object : Callback<List<Category>>{
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                var map: HashMap<String, Boolean>? = handleNetworkError.value ?: HashMap<String, Boolean>()
                map?.set("category", false)
                handleNetworkError.value = map
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful)
                {
                    for (i in 0..(response.body()?.size ?: 1)-1)
                    {
                        if(response.body()?.get(i)?.title.equals("all"))
                        {
                            response.body()?.get(i)?.isSelected = true
                        }
                        else
                        {
                            response.body()?.get(i)?.isSelected = false
                        }
                    }
                    var map: HashMap<String, Boolean> = handleNetworkError.value ?: HashMap<String, Boolean>()
                    map?.set("category", true)
                    handleNetworkError.value = map
                    categoryListLiveData.value = response.body()
                }

            }

        })

        return categoryListLiveData
    }
    companion object {
        @Volatile private var instance: HomeRepository? = null

        fun getInstance(): HomeRepository {
            return instance ?: synchronized(this) { instance ?:  HomeRepository().also { instance = it } }

        }
    }
}