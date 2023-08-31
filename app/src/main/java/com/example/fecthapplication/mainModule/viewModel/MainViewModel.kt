package com.example.fecthapplication.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fecthapplication.common.ItemEntity
import com.example.fecthapplication.common.utils.Constants
import com.example.fecthapplication.mainModule.model.MainInteractor

class MainViewModel : ViewModel() {
    private var interactor: MainInteractor
    private var itemList: MutableList<ItemEntity> //todo: delete
//    private var stores:MutableLiveData<List<StoreEntity>>

    init { //intanciar o iniciar los elementos
        interactor = MainInteractor()
        itemList = mutableListOf()
    }
    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val items: MutableLiveData<MutableList<ItemEntity>> by lazy {
        MutableLiveData<MutableList<ItemEntity>>().also {
            loadItems() //     DESCOMENTAR CON CORUTINES
        }
    } //se almacenaran las tiendas del modelo
    fun getItems(): LiveData<MutableList<ItemEntity>> {
        //metodo que ocupa la vista para pintar las stores
        return items.also {
            //ejecuta otras instrucciones
            loadItems()
        }
    }

    fun isShowProgress(): LiveData<Boolean> {
        return showProgress
    }

    private fun loadItems() {
        showProgress.value = Constants.SHOW
        interactor.getItemsAPI {
            showProgress.value = Constants.HIDE //esconder el progress
            items.value = it
            itemList = it
        }
    }
}