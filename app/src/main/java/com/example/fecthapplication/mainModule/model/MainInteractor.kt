package com.example.fecthapplication.mainModule.model

import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.fecthapplication.FetchApplication
import com.example.fecthapplication.common.entities.ItemEntity
import com.example.fecthapplication.common.utils.Constants


class MainInteractor {
    fun getItemsAPI(callback: (MutableList<ItemEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_PATH
        var itemList = mutableListOf<ItemEntity>()
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val listId = jsonObject.getInt("listId")
                val name = jsonObject.getString("name")
                if (!name.isNullOrBlank() && name != "null") {
                    val item = ItemEntity(
                        id,
                        listId,
                        name
                    )
                    itemList.add(item)
                }
            }
            val itemsSorted = itemList.sortedWith(compareBy(ItemEntity::listId, ItemEntity::name))
            callback(itemsSorted as MutableList<ItemEntity>)
        }, {
            it.printStackTrace()
            callback(itemList)
        })
        FetchApplication.itemAPI.addToRequestQueue(jsonArrayRequest)
    }
}