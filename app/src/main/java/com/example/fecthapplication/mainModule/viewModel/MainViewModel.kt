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
     var categories: MutableList<Category>
     var categorias: MutableLiveData<List<Category>> = MutableLiveData()
    var filteredItems: MutableLiveData<List<ItemEntity>> = MutableLiveData()

    init { //intanciar o iniciar los elementos
        itemList = mutableListOf()
        categories = mutableListOf(Category.Uno,Category.Dos,Category.Tres,Category.Cuatro )
//        val initializedCategories = listOf(
//            Category.Uno,
//            Category.Dos,
//            Category.Tres,
//            Category.Cuatro
//        )
//        categorias.value = initializedCategories
    }

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val items: MutableLiveData<List<ItemEntity>> by lazy {
        MutableLiveData<List<ItemEntity>>()
    }
     fun updateCategories(adapterC:Adapter<CategoryAdapter.ViewHolder>, position: Int) {
        categories[position].isSelected = !categories[position].isSelected;
        adapterC.notifyItemChanged(position);
//        updateDatos();
    }
//    private fun updateDatos() {
//        val selectedCategories: List<Category> = categories.filter { it.isSelected }
//        val newTasks =
//            mAdapter.items.filter {item->
//                selectedCategories.any { category ->
////                    it.value == item.listId
//                    when (category) {
//                        Category.Uno -> item.listId == 1
//                        Category.Dos -> item.listId == 2
//                        Category.Tres -> item.listId == 3
//                        Category.Cuatro -> item.listId == 4
//                    }
//                }
//            }
//        mAdapter.setAllItems(newTasks)
////        mAdapter.itemsSorted = newTasks as MutableList<ItemEntity>
//        mAdapter.notifyDataSetChanged()
//    }
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

    fun onCategoryClicked(position: Int) {
    Log.i("Clickeado en categoria", categorias.value.toString())
        val updatedCategories = categorias.value?.toMutableList()
        updatedCategories?.apply {
            this[position].isSelected = !this[position].isSelected
            categorias.value = this
        }
        updateData()
    }

    // Función para filtrar tareas por categoría
    private fun updateData() {
        val selectedCategories = categorias.value?.filter { it.isSelected } ?: emptyList()
        val newItems =
            itemList.filter { item ->
                selectedCategories.any {
                    it.value == item.listId
                }
            }
        filteredItems.value = newItems
    }

}