package com.example.fecthapplication.mainModule.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.fecthapplication.common.entities.Category
import com.example.fecthapplication.common.entities.ItemEntity
import com.example.fecthapplication.common.utils.Constants
import com.example.fecthapplication.mainModule.adapter.CategoryAdapter
import com.example.fecthapplication.mainModule.adapter.ItemAdapter
import com.example.fecthapplication.mainModule.model.MainInteractor

class MainViewModel : ViewModel() {
    private var interactor: MainInteractor = MainInteractor()
    private var itemList: MutableList<ItemEntity> //todo: delete

    init {
        itemList = mutableListOf()
    }

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val items: MutableLiveData<List<ItemEntity>> by lazy {
        MutableLiveData<List<ItemEntity>>()
    }
    fun getItems(): LiveData<List<ItemEntity>> {
        //metodo que ocupa la vista para pintar las stores
        return items.also {
            //ejecuta otras instrucciones
            loadAllItems()
        }
    }


    private fun loadAllItems() {
        showProgress.value = Constants.SHOW
        interactor.getItemsAPI {
            showProgress.value = Constants.HIDE //esconder el progress
            items.value = it
            itemList = it
        }
    }

    fun isShowProgress(): LiveData<Boolean> {
        return showProgress
    }

}