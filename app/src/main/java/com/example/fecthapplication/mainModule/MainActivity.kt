package com.example.fecthapplication.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fecthapplication.common.entities.Category
import com.example.fecthapplication.common.entities.ItemEntity
import com.example.fecthapplication.databinding.ActivityMainBinding
import com.example.fecthapplication.mainModule.adapter.CategoryAdapter
import com.example.fecthapplication.mainModule.adapter.ItemAdapter
import com.example.fecthapplication.mainModule.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private val categories = listOf<Category>(
        Category.Uno,
        Category.Dos,
        Category.Tres,
        Category.Cuatro
    )

    //DATA
    private lateinit var mAdapter: ItemAdapter;
    private lateinit var mLinearLayout: LinearLayoutManager

    //CATEGORIES
    private lateinit var mAdapterC: CategoryAdapter;
    private lateinit var mLinearLayoutC: LinearLayoutManager

    //MVVM
    private lateinit var mMainViewModel: MainViewModel
    var originalData: List<ItemEntity> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java] //inicializarlo
        mMainViewModel.getItems().observe(
            //watcher, cada que cambia se actualiza con ese metodo
            this,
        ) { items ->
            originalData = items
            mAdapter.setAllItems(items)
        }
        mMainViewModel.isShowProgress().observe(this) { isShowProgress ->
            mBinding.progressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        }
        mMainViewModel.filteredItems.observe(this) { newItems ->
            mAdapter.setAllItems(newItems)
//            taskAdapter.notifyDataSetChanged()
        }
//        mMainViewModel.categories = categories as MutableLiveData<List<Category>>
    }

    private fun setupRecyclerView() {
//        mAdapterC = CategoryAdapter(categories)
//        mAdapterC = CategoryAdapter(categories){ position ->
//            mMainViewModel.onCategoryClicked(position)
//        }
//        mAdapterC = CategoryAdapter() { position -> mMainViewModel.updateCategories(mAdapterC ,position) }
//        mAdapterC = CategoryAdapter(categories) { position -> updateCategories(position) }
        mAdapterC = CategoryAdapter() { position -> updateCategories(position) } //buenooooooo
        mLinearLayoutC = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mAdapter = ItemAdapter(mutableListOf())
        mLinearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mBinding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutC
            adapter = mAdapterC
        }
        mBinding.rvData.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout
            adapter = mAdapter
        }
    }

    private fun updateCategories(position: Int) {
        mAdapterC.categories[position].isSelected = !mAdapterC.categories[position].isSelected;
        mAdapterC.notifyItemChanged(position);
        update();
    }

    private fun update() {
        val selectedCategories: List<Category> = categories.filter { it.isSelected }
        if (selectedCategories.isEmpty()) {
            // Si no hay categorías seleccionadas, restaura los datos originales
            mAdapter.setAllItems(originalData)
        } else {
            val newTasks = mAdapter.items.filter { item ->
                // Verifica si el item pertenece a todas las categorías seleccionadas
                selectedCategories.all { category ->
                    when (category) {
                        Category.Uno -> item.listId == 1
                        Category.Dos -> item.listId == 2
                        Category.Tres -> item.listId == 3
                        Category.Cuatro -> item.listId == 4
                    }
                }
            }
            mAdapter.setAllItems(newTasks)
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun updateData2() {
        val selectedCategories: List<Category> = categories.filter { it.isSelected }

        if (selectedCategories.isEmpty()) {
            mAdapter.setAllItems(originalData)
        } else {
            val selectedListIds = selectedCategories.mapNotNull { category ->
                when (category) {
                    Category.Uno -> 1
                    Category.Dos -> 2
                    Category.Tres -> 3
                    Category.Cuatro -> 4
                    // Agrega más casos para otras categorías si es necesario
                    else -> null // Ignorar categorías desconocidas
                }
            }.toSet()

            var newTasks = mAdapter.items.filter { item ->
                selectedListIds.contains(item.listId)
            }
            mAdapter.setAllItems(newTasks)
        }
        mAdapter.notifyDataSetChanged()
    }
}
