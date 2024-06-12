package com.example.clothing_store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.clothing_store.R
import com.example.clothing_store.model.Item

class ItemAdapter(private val data: List<Item?>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = data[position]
        holder.itemName.text = items!!.name
        holder.itemPrice.text = "Цена: ${items.price}р."

        items.image.let {
            holder.itemImage.load(it) {
//                placeholder(R.drawable.placeholder_image) // Можно установить изображение-плейсхолдер(во время загрузки)
//                error(R.drawable.error_image) // Можно установить изображение для ошибки загрузки
            }
        }
    }

}