package com.example.fecthapplication.mainModule.model

import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.fecthapplication.FetchApplication
import com.example.fecthapplication.common.entities.ItemEntity
import com.example.fecthapplication.common.utils.Constants


class MainInteractor {
    //    val items: LiveData<MutableList<ItemEntity>> = liveData {
////        kotlinx.coroutines.delay(1000) //temporal - pruebas
//        val storesLiveData = getItems()
//        emitSource(storesLiveData.map{ //map es para transformar la info
//                stores ->
//            stores.sortedBy { it.name }.toMutableList() // ordenar por orden alfabetico
//        })
//    }
//
//    fun getItems(): MutableLiveData<ItemEntity> {
//        val url = Constants.API_URL + Constants.GET_ALL_PATH
//        var itemList = MutableLiveData<ItemEntity>()
//        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
//
//            for (i in 0 until response.length()) {
//                val jsonObject = response.getJSONObject(i)
//                val id = jsonObject.getInt("id")
//                val listId = jsonObject.getInt("listId")
//                val name = jsonObject.getString("name")
//                if (!name.isNullOrBlank() && name != "null") {
//                    val item = ItemEntity(
//                        id,
//                        listId,
//                        name
//                    )
//                    itemList.add(item)
//                }
//            }
//            Log.i("LIST", itemList.toString())
////            callback(itemList)
//        }, {
//            it.printStackTrace()
//        })
//        FetchApplication.itemAPI.addToRequestQueue(jsonArrayRequest)
//        return itemList
//    }

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
//            Log.i("LIST", itemList.toString())
//            val itemsOrdenados : MutableList<ItemEntity>= itemList.sortedBy { it.name }
//            callback(itemList)
            val itemsSorted = itemList.sortedWith(compareBy(ItemEntity::listId, ItemEntity::name))
//            callback(itemList.sortedBy { it.name } as MutableList<ItemEntity>)
            callback(itemsSorted as MutableList<ItemEntity>)
        }, {
            it.printStackTrace()
            callback(itemList)
        })
        FetchApplication.itemAPI.addToRequestQueue(jsonArrayRequest)
    }
}