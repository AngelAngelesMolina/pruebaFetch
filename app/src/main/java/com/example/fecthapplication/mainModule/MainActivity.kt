package com.example.fecthapplication.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fecthapplication.common.utils.Category
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
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java) //inicializarlo
        mMainViewModel.getItems().observe(
            //watcher, cada que cambia se actualiza con ese metodo
            this,
        ) {items->
            mAdapter.setItems(items)
//            mBinding.progressBar.visibility = if (stores.isEmpty()) View.VISIBLE else View.GONE
            //todo: validacion cuando viene vacio pero si hay respuesta
        }
        mMainViewModel.isShowProgress().observe(this) { isShowProgress ->
            mBinding.progressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView() {
        mAdapterC = CategoryAdapter(categories)
//        mAdapterC = CategoryAdapter(categories){ position -> updateCategories(position) }
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
//    private fun updateCategories(position: Int) {
//        categories[position].isSelected = !categories[position].isSelected;
//        CategoryAdapter.notifyItemChanged(position);
//        updateData();
//    }
//    private fun updateData() {
//        val selectedCategories: List<Category> = categories.filter { it.isSelected }
//        val newTasks =
//            tasks.filter { selectedCategories.contains(it.category) } //filtrar por categorias
//        tasksAdapter.tasks = newTasks;
//        tasksAdapter.notifyDataSetChanged();
//    }
}