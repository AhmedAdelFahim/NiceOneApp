package com.niceone.niceoneapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.niceone.niceoneapp.R
import com.niceone.niceoneapp.models.Workshop
import com.squareup.picasso.Picasso

class GarageDetailsActivity : AppCompatActivity() {

    lateinit var logo : ImageView
    lateinit var title : TextView
    lateinit var phone1 : TextView
    lateinit var phone2 : TextView
    lateinit var phone3 : TextView
    lateinit var address : TextView
    lateinit var workshop:Workshop
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage_details)
        logo = findViewById(R.id.garage_logo)
        title = findViewById(R.id.garage_title)
        phone1 = findViewById(R.id.phone1)
        phone2 = findViewById(R.id.phone2)
        phone3 = findViewById(R.id.phone3)
        address = findViewById(R.id.address)

        workshop = intent.getSerializableExtra("workshop") as Workshop

        Picasso.get()
            .load(workshop.logo)
            .resize(90, 90)
            .centerCrop()
            .into(logo)

        title.text = workshop.title
        phone1.text = workshop.primary_phone
        phone2.text = workshop.secondry_phone
        phone3.text = workshop.third_phone
        address.text = workshop.address

    }
}
