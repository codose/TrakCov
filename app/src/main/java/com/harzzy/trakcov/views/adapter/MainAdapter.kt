package com.harzzy.trakcov.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harzzy.trakcov.api.response.state.State
import com.harzzy.trakcov.api.response.state.nigeria.Data
import com.harzzy.trakcov.databinding.ItemStateItemBinding

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */


class MainAdapter(val context : Context, val clickListener: StateClickListener) : ListAdapter<Data, MainAdapter.MyViewHolder>(MainDiffCallback()) {

    class MyViewHolder(val binding: ItemStateItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(state: Data, clickListener: StateClickListener) {
            binding.itemCardView.setOnClickListener {
                clickListener.onClick(state)
            }
            val recovered = state.recovered ?: 0
            val death = state.dead ?: 0
            val active = state.confirmed - (death + recovered)
            binding.stateName.text = state.location
            binding.activeCasesWorld.text = active.toString()
            binding.casesWorld.text = state.confirmed.toString()
            binding.recoveredCasesWorld.text = recovered.toString()
            binding.deathCasesWorld.text = state.dead.toString()
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

class StateClickListener(val clickListener: (state: Data) -> Unit){
    fun onClick(state: Data) = clickListener(state)
}

class MainDiffCallback : DiffUtil.ItemCallback<Data>(){
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.location == newItem.location
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}
