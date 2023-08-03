package com.broken.screen.crack.prank.crackedscreen.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.broken.screen.crack.prank.crackedscreen.R
import com.broken.screen.crack.prank.crackedscreen.databinding.RowPrankTypeBinding
import com.broken.screen.crack.prank.crackedscreen.ui.commen.PrankType

class PrankAdupter(val list: List<PrankModel>, val onClick:(PrankModel)->Unit) : RecyclerView.Adapter<PrankAdupter.PrankViewHolder>() {
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
        holder.prankTypeRow.root.setOnClickListener {
            onClick(prank)
        }
        val context = holder.prankTypeRow.tvPrankName.context
        when(prank.type){
            PrankType.BROKEN_SCREEN_PRANK ->{
                Glide.with(context).load(R.drawable.crack_icon).into(holder.prankTypeRow.ivPrankImage)
//                Glide.with(context).load(R.drawable.fram).apply(RequestOptions().centerCrop())
//                    .into(holder.prankTypeRow.ivPrankImage2)
            }
        }
    }
}