package com.example.fecthapplication.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fecthapplication.R
import com.example.fecthapplication.common.utils.Category
import com.example.fecthapplication.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>
//    private val onItemSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var mContext: Context // m-> se refiere a que esta variable es miembro de la clase

    //VIEWHOLDER
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCategoryBinding.bind(view) //habilitar el viewbinding con los item del rv
    }

    override fun getItemCount(): Int = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false);
        return ViewHolder(view);
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = if (categories[position].isSelected) {
            R.color.fetch_background_card
        } else {
            R.color.fetch_background_disabled
        }
        holder.binding.viewContainer.setCardBackgroundColor(
            ContextCompat.getColor(
                mContext,
                color
            )
        ) //asinar background
//        holder.itemView.setOnClickListener {
//            onItemSelected(holder.layoutPosition) // obtener la posicion
//        }
        when (categories[position]) {
            Category.Cuatro -> {
                holder.binding.tvCategoryName.text = "ListId: 4"
                setColorDivider(holder, "group_color_four")
            }

            Category.Dos -> {
                holder.binding.tvCategoryName.text = "ListId: 2"
                setColorDivider(holder, "group_color_two")
            }

            Category.Tres -> {
                holder.binding.tvCategoryName.text = "ListId: 3"
                setColorDivider(holder, "group_color_three")
            }

            Category.Uno -> {
                holder.binding.tvCategoryName.text = "ListId: 1"
                setColorDivider(holder, "group_color_one")
            }
        }

    }

    private fun setColorDivider(holder: ViewHolder, color: String) {
        val colorResourceId = mContext.resources.getIdentifier(color, "color", mContext.packageName)
        if (colorResourceId != 0) {
            holder.binding.divider.setBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    colorResourceId
                )
            )
        }
    }
}
