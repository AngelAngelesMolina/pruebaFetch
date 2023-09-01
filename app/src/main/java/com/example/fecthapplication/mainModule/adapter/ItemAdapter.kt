package com.example.fecthapplication.mainModule.adapter

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fecthapplication.R
import com.example.fecthapplication.common.entities.ItemEntity
import com.example.fecthapplication.databinding.ItemDataBinding

class ItemAdapter(
    var items: MutableList<ItemEntity>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private lateinit var mContext: Context // m-> se refiere a que esta variable es miembro de la clase
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDataBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            binding.tvId.text = "ID: " + item.id.toString()
            binding.tvName.text = "Nombre: " + item.name
            when (item.listId) {
                1 -> setColor(this, "group_color_one")
                2 -> setColor(this, "group_color_two")
                3 -> setColor(this, "group_color_three")
                4 -> setColor(this, "group_color_four")
            }
        }
    }

    private fun setColor(holder: ViewHolder, color: String) {
        val colorResourceId = mContext.resources.getIdentifier(color, "color", mContext.packageName)
        if (colorResourceId != 0) {
            holder.binding.flId.setBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    colorResourceId
                )
            )
        }
    }

    fun setAllItems(items: List<ItemEntity>) {
        this.items = items as MutableList<ItemEntity>
        notifyDataSetChanged()
    }
}
