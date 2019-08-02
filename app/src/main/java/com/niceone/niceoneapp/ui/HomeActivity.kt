package com.niceone.niceoneapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niceone.niceoneapp.adapters.CategoryAdapter
import com.niceone.niceoneapp.R
import com.niceone.niceoneapp.adapters.WorkshopAdapter
import com.niceone.niceoneapp.models.Category
import com.niceone.niceoneapp.models.WorkshopPage
import com.niceone.niceoneapp.viewmodels.HomeViewModel


class HomeActivity: AppCompatActivity(), WorkshopAdapter.listItemClickListener {


    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var workshopsRecyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryRecyclerAdapter: CategoryAdapter
    private lateinit var workshopRecyclerAdapter: WorkshopAdapter
    private lateinit var workshopLayoutManager: LinearLayoutManager
    private var isLoading = false
    private var isLastPage = false
    private var pageSize = 0
    private lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressBar = findViewById(R.id.progress_bar)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        categoryRecyclerAdapter = CategoryAdapter(this, viewModel)
        workshopRecyclerAdapter = WorkshopAdapter(this)

        categoryRecyclerView = findViewById(R.id.categories_recycler)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        categoryRecyclerView.adapter = categoryRecyclerAdapter

        workshopsRecyclerView = findViewById(R.id.workshops_recycler)
        workshopLayoutManager=LinearLayoutManager(this)
        workshopsRecyclerView.layoutManager = workshopLayoutManager
        workshopsRecyclerView.adapter = workshopRecyclerAdapter

        workshopsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = workshopLayoutManager.childCount
                val totalItemCount = workshopLayoutManager.itemCount
                val firstVisibleItemPosition = workshopLayoutManager.findFirstVisibleItemPosition()

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isValidFirstItem = firstVisibleItemPosition >= 0
                val totalIsMoreThanVisible = totalItemCount >= pageSize
                val shouldLoadMore = isValidFirstItem && isAtLastItem && totalIsMoreThanVisible && isNotLoadingAndNotLastPage

                if (shouldLoadMore) {
                    viewModel.loadNextPage()
                    isLoading = true
                }

            }
        })

        setupViewModel()

    }

    override fun onListItemClick(clickedItemIndex: Int) {
        val intent = Intent(this,GarageDetailsActivity::class.java)
        intent.putExtra("workshop",workshopRecyclerAdapter.getWorkshopItem(clickedItemIndex))
        startActivity(intent)
    }


    private fun setupViewModel() {
        viewModel.getCategoryList().observe(this,object : Observer<List<Category>> {
            override fun onChanged(t: List<Category>?) {
                workshopRecyclerAdapter.clearAdapter()
                isLastPage = false
                isLoading = false
                categoryRecyclerAdapter.setCategories(t)
                progressBar.visibility = VISIBLE
            }

        })

        viewModel.getWorkshopPage().observe(this,object : Observer<WorkshopPage>{
            override fun onChanged(t: WorkshopPage?) {
                if (t != null) {
                    pageSize = (t.to-t.from)+1
                    isLastPage = t.next_page_url == null
                }
                isLoading = false
                progressBar.visibility = GONE
                workshopRecyclerAdapter.setWorkshop(t)
            }

        })

        viewModel.getNetworkError().observe(this,object : Observer<HashMap<String,Boolean>>{
            override fun onChanged(t: HashMap<String,Boolean>?) {
                if(t?.contains("category")!! && !t?.get("category")!!)
                {
                    progressBar.visibility = GONE
                    Toast.makeText(this@HomeActivity,"Network Error Occurred",Toast.LENGTH_LONG).show()
                }
                if (t?.contains("workshop")!! && !t?.get("workshop")!!)
                {
                    progressBar.visibility = GONE
                    Toast.makeText(this@HomeActivity,"Network Error Occurred",Toast.LENGTH_LONG).show()
                }
                if(t?.contains("category")!! && t?.contains("workshop")!! &&  t?.get("category")!! && t?.get("workshop")!!)
                {
                    progressBar.visibility = GONE
                }

            }

        })



    }




}
