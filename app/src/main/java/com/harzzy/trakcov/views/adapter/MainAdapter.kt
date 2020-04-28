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
import com.harzzy.trakcov.databinding.ItemStateItemBinding



class MainAdapter(val context : Context, val clickListener: StateClickListener) : ListAdapter<State, MainAdapter.MyViewHolder>(MainDiffCallback()) {

    class MyViewHolder(val binding: ItemStateItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(state: State, clickListener: StateClickListener) {
            binding.itemCardView.setOnClickListener {
                clickListener.onClick(state)
            }
            binding.stateName.text = state.state
            binding.activeCasesWorld.text = state.casesOnAdmission.toString()
            binding.casesWorld.text = state.confirmedCases.toString()
            binding.recoveredCasesWorld.text = state.discharged.toString()
            binding.deathCasesWorld.text = state.death.toString()

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

class StateClickListener(val clickListener: (state: State) -> Unit){
    fun onClick(state: State) = clickListener(state)
}

class MainDiffCallback : DiffUtil.ItemCallback<State>(){
    override fun areItemsTheSame(oldItem: State, newItem: State): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: State, newItem: State): Boolean {
        return oldItem == newItem
    }
}
