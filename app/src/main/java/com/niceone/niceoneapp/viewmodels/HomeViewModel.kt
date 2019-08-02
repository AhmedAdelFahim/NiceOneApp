package com.niceone.niceoneapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niceone.niceoneapp.api.HomeRepository
import com.niceone.niceoneapp.models.Category
import com.niceone.niceoneapp.models.WorkshopPage

class HomeViewModel : ViewModel() {

    private var categoryListLiveData: MutableLiveData<List<Category>>
    private var workshopPageLiveData: MutableLiveData<WorkshopPage>
    private var handleNetworkErrorLiveData: MutableLiveData<HashMap<String,Boolean>>
    private var id = "all"
    private var currentPage = 1

    init {
        categoryListLiveData = HomeRepository.getInstance().getCategories()
        workshopPageLiveData = HomeRepository.getInstance().getWorkshops(id,currentPage.toString())
        handleNetworkErrorLiveData = HomeRepository.getInstance().getNetworkError()
    }

    fun setCategories(
        categoryList: List<Category>?,
        id: String
    )
    {

        Log.d("setCategories",id)
        HomeRepository.getInstance().setCategories((categoryList))
        this.id = id
        currentPage = 1
        loadCurrentPage()
    }

    fun loadCurrentPage()
    {
        HomeRepository.getInstance().getWorkshops(id,currentPage.toString())
    }

    fun loadNextPage()
    {
        currentPage++
        HomeRepository.getInstance().getWorkshops(id,currentPage.toString())
    }
    fun getNetworkError():LiveData<HashMap<String,Boolean>> {
        return handleNetworkErrorLiveData
    }

    fun getCategoryList():LiveData<List<Category>> {
        return categoryListLiveData
    }




    fun getWorkshopPage():LiveData<WorkshopPage> {
        return workshopPageLiveData
    }

}