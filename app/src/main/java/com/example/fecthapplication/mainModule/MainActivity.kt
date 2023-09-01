package com.example.fecthapplication.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fecthapplication.common.entities.Category
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
        mAdapterC = CategoryAdapter(categories) { position -> updateCategories(position) }
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
        categories[position].isSelected = !categories[position].isSelected;
        mAdapterC.notifyItemChanged(position);
        updateDatos();
    }

    private fun updateDatos() {
        val selectedCategories: List<Category> = categories.filter { it.isSelected }
        val newTasks =
            mAdapter.items.filter {item->
                selectedCategories.any { category ->
//                    it.value == item.listId
                    when (category) {
                        Category.Uno -> item.listId == 1
                        Category.Dos -> item.listId == 2
                        Category.Tres -> item.listId == 3
                        Category.Cuatro -> item.listId == 4
                    }
//                    when (category.value) {
//                        1 -> item.listId == 1
//                        2 -> item.listId == 2
//                        3 -> item.listId == 3
//                        4 -> item.listId == 4
//                    }
                }
            }
        mAdapter.setAllItems(newTasks)
        mAdapter.notifyDataSetChanged()
    }
}