package com.harzzy.trakcov.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.databinding.ItemStateItemBinding

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class CountryAdapter(val context : Context, val clickListener: CountryClickListener) : ListAdapter<InternationalResponseItem, CountryAdapter.MyViewHolder>(CountryDiffCallback()) {

    class MyViewHolder(val binding: ItemStateItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(state: InternationalResponseItem, clickListener: CountryClickListener) {
            binding.itemCardView.setOnClickListener {
                clickListener.onClick(state)
            }
            binding.activeCasesWorld.text = state.active.toString()
            binding.casesWorld.text = state.cases.toString()
            binding.recoveredCasesWorld.text = state.recovered.toString()
            binding.deathCasesWorld.text = state.deaths.toString()
            binding.stateName.text = state.country
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }
    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStateItemBinding.inflate(layoutInflater,parent,false)
        return MyViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id = getItem(position)
        holder.bind(id, clickListener)
    }
}

class CountryClickListener(val clickListener: (state: InternationalResponseItem) -> Unit){
    fun onClick(state: InternationalResponseItem) = clickListener(state)
}

class CountryDiffCallback : DiffUtil.ItemCallback<InternationalResponseItem>(){
    override fun areItemsTheSame(oldItem: InternationalResponseItem, newItem: InternationalResponseItem): Boolean {
        return oldItem.cases == newItem.cases
    }

    override fun areContentsTheSame(oldItem: InternationalResponseItem, newItem: InternationalResponseItem): Boolean {
        return oldItem == newItem
    }
}
