package com.thirtythreeapps.brokenscreenprank.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.RowPrankTypeBinding

class PrankAdupter(val list: List<PrankModel>) : RecyclerView.Adapter<PrankAdupter.PrankViewHolder>() {
    inner class PrankViewHolder(val prankTypeRow: RowPrankTypeBinding) :
        ViewHolder(prankTypeRow.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrankViewHolder {
       return PrankViewHolder(RowPrankTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: PrankViewHolder, position: Int) {
        val prank = list.get(holder.adapterPosition)
        holder.prankTypeRow.tvPrankName.text = prank.title
        val context = holder.prankTypeRow.tvPrankName.context
        when(prank.type){
            PrankType.BROKEN_SCREEN_PRANK ->{
                Glide.with(context).load(R.drawable.home_broken_screen_prank).into(holder.prankTypeRow.ivPrankImage)
            }
        }
    }
}