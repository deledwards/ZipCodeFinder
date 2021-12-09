package com.deledwards.zipcodefinder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.deledwards.zipcodefinder.databinding.FragmentItemBinding
import com.deledwards.zipcodefinder.domain.model.ZipCode

/**
 * [ZipCodeRecyclerViewAdapter] that can display a [ZipCode].
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.code.text =  "Zip: ${item.code}"
        holder.city.text = "${item.city}, ${item.state}"
        holder.distance.text = "${item.distance.toString()} km distance"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val code: TextView = binding.code
        val city: TextView = binding.city
        val distance: TextView = binding.distance

        override fun toString(): String {
            return super.toString() + " '" + city.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(zipCodes: List<ZipCode>){
        values = zipCodes
        this.notifyDataSetChanged()
    }

}