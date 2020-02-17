package com.spicysoft.currencyexchange.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spicysoft.currencyexchange.BR
import com.spicysoft.currencyexchange.R
import com.spicysoft.currencyexchange.model.dto.ExchangeRate

class ExchangeRateAdapter: ListAdapter<ExchangeRate, ExchangeRateAdapter.ExchageRateViewHolder>(
    DIFF_UTILL)  {

    companion object {

        private val DIFF_UTILL = object : DiffUtil.ItemCallback<ExchangeRate>() {

            override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
                return oldItem.shortName == newItem.shortName
            }

            override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchageRateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
            R.layout.layout_currency_rate, parent, false)
        return ExchageRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchageRateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExchageRateViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(rate: ExchangeRate) {
            binding.setVariable(BR.rate, rate)
        }
    }

}