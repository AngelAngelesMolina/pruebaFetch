package com.example.fecthapplication.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fecthapplication.R
import com.example.fecthapplication.common.entities.Category
import com.example.fecthapplication.databinding.ItemCategoryBinding

class CategoryAdapter(
    val categories: List<Category>,
    private val onCategoryClickListener: (Int) -> Unit
//    private val onCategoryClickListener: (Int) -> Unit
//    private val onItemSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var mContext: Context // m-> se refiere a que esta variable es miembro de la clase

    //    private val categories: List<Category> = listOf(
//        Category.Uno, Category.Dos, Category.Tres, Category.Cuatro
//    )
    //VIEWHOLDER
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCategoryBinding.bind(view) //habilitar el viewbinding con los item del rv
        fun render(category: Category, onCategoryClickListener: (Int) -> Unit) {
            val color = if (category.isSelected) {
                R.color.fetch_background_card
            } else {
                R.color.fetch_background_disabled
            }
            binding.viewContainer.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.viewContainer.context,
                    color
                )
            ) //asinar background
            itemView.setOnClickListener {
                onCategoryClickListener(layoutPosition) // obtener la posicion
            }
            val categoryName = binding.root.context.getString(R.string.category_name_format, category.value)
            binding.tvCategoryName.text = categoryName

            val dividerColor = when (category) {
                Category.Uno -> R.color.group_color_one
                Category.Dos -> R.color.group_color_two
                Category.Tres -> R.color.group_color_three
                Category.Cuatro -> R.color.group_color_four
            }
            binding.divider.setBackgroundColor(ContextCompat.getColor(binding.divider.context, dividerColor))
        }
    }

    override fun getItemCount(): Int = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false);
        return ViewHolder(view);
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(categories[position], onCategoryClickListener);
    }
}
