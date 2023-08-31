package com.example.fecthapplication

import android.app.Application
import com.example.fecthapplication.common.api.ItemApi

class FetchApplication : Application() {
    companion object {
        lateinit var itemAPI: ItemApi
    }

    override fun onCreate() {
        super.onCreate()
//VOLLEY
        itemAPI = ItemApi.getInstance(this)
    }
}