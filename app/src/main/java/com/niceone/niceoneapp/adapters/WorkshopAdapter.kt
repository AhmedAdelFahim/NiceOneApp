package com.niceone.niceoneapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niceone.niceoneapp.R
import com.niceone.niceoneapp.models.Workshop
import com.niceone.niceoneapp.models.WorkshopPage
import com.squareup.picasso.Picasso

class WorkshopAdapter : RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder> {

    private var workshopPage:WorkshopPage? = null

    private val itemClickListener : listItemClickListener
    constructor( itemClickListener: listItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.garages_item, parent, false)
        return WorkshopViewHolder(view, itemClickListener)

    }

    override fun getItemCount(): Int {
        return workshopPage?.data?.size ?: 0
    }


    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        var item = workshopPage?.data?.get(position)

        Picasso.get()
            .load(item?.logo)
            .resize(90, 90)
            .centerCrop()
            .into(holder.logo)

        holder.phone.text = item?.primary_phone
        holder.address.text = item?.address
        holder.title.text = item?.title
    }

    fun setWorkshop(result: WorkshopPage?) {
        if (result == null)
            return
        if(workshopPage == null)
            workshopPage = result
        else
            workshopPage?.data?.addAll(result.data)
        notifyDataSetChanged()
    }

    fun clearAdapter(){

        workshopPage?.data?.clear()
        workshopPage = null
    }

    fun getWorkshopItem(index:Int):Workshop {
        return workshopPage?.data?.get(index)!!
    }

    class WorkshopViewHolder(view: View, itemClickListener: listItemClickListener) :
        RecyclerView.ViewHolder(view),View.OnClickListener {


        var logo : ImageView
        var title : TextView
        var phone : TextView
        var address : TextView
        var itemClickListener: listItemClickListener

        init {
            logo = view.findViewById(R.id.garage_logo)
            title = view.findViewById(R.id.garage_title)
            phone = view.findViewById(R.id.phone)
            address = view.findViewById(R.id.address)
            view.setOnClickListener(this)
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View?) {

            val clickedPosition = adapterPosition
           itemClickListener.onListItemClick(clickedPosition)
        }

    }

    interface listItemClickListener {

        fun onListItemClick(clickedItemIndex: Int)
    }


}