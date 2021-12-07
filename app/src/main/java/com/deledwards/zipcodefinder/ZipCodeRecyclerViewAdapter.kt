package com.deledwards.zipcodefinder

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.deledwards.zipcodefinder.databinding.FragmentItemBinding
import com.deledwards.zipcodefinder.domain.model.ZipCode

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ZipCodeRecyclerViewAdapter(
    private var values: List<ZipCode>
) : RecyclerView.Adapter<ZipCodeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.code.text = item.code
        holder.city.text = item.city
        holder.state.text = item.state.name
        holder.distance.text = item.distance.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val code: TextView = binding.code
        val city: TextView = binding.city
        val state: TextView = binding.state
        val distance: TextView = binding.distance

        override fun toString(): String {
            return super.toString() + " '" + city.text + "'"
        }
    }

    fun update(zipCodes: List<ZipCode>){
        values = zipCodes
        this.notifyDataSetChanged()
    }

}