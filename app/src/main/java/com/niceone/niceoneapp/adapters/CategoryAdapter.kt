package com.niceone.niceoneapp.adapters

import android.app.Activity
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.niceone.niceoneapp.R
import com.niceone.niceoneapp.models.Category
import com.niceone.niceoneapp.viewmodels.HomeViewModel
import com.squareup.picasso.Picasso


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private var activity: Activity
    private var categoryList: List<Category>? = null
    private var viewModel:HomeViewModel

    constructor(activity: Activity,viewModel: HomeViewModel) {
        this.activity = activity
        this.viewModel = viewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val item = categoryList?.get(position)
        Picasso.get()
            .load(item?.logo)
            .resize(90, 90)
            .centerCrop()
            .into(holder.icon)
        holder.name.text = item?.title
        updateStyle(holder.icon, holder.name, position)
        holder.icon.setOnClickListener({
            updateSelectedPosition(position)
        })

    }

    fun setCategories(result: List<Category>?) {
        if (result == null)
            return
        categoryList = result
        notifyDataSetChanged()
    }


    fun updateSelectedPosition(position: Int) {

        for (i in 0..(categoryList?.size ?: 1) - 1) {
            if (i == position) {
                categoryList?.get(i)?.isSelected = true

            } else {

                categoryList?.get(i)?.isSelected = false
            }
        }

        viewModel.setCategories(categoryList, categoryList?.get(position)?.id.toString())
    }

    fun updateStyle(icon: ImageView, name: TextView, position: Int) {
        if (categoryList?.get(position)?.isSelected == true) {
            icon.setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_IN)
            icon.setBackgroundResource(R.drawable.circle_solid_shape)
            name.setTextColor(ContextCompat.getColor(activity, R.color.darkBlue))
        } else {
            icon.setColorFilter(ContextCompat.getColor(activity, R.color.gray), PorterDuff.Mode.SRC_IN)
            icon.setBackgroundResource(R.drawable.circle_border_shape)
            name.setTextColor(ContextCompat.getColor(activity, R.color.gray))
        }
    }

    class CategoryViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var icon: ImageView
        var name: TextView

        init {
            icon = view.findViewById(R.id.category_icon)
            name = view.findViewById(R.id.category_name)

        }


    }
}
