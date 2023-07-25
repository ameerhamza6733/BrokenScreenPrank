package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.thirtythreeapps.brokenscreenprank.databinding.RowCrackPreviewBinding

class EffectListAdapter(val list: List<PrankDetail>,val onClick:(PrankDetail)->Unit)  : Adapter<EffectListAdapter.EffectListViewHolder>() {
    inner class EffectListViewHolder(val rowCrackPreviewBinding: RowCrackPreviewBinding): ViewHolder(rowCrackPreviewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectListViewHolder {
        return EffectListViewHolder(RowCrackPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EffectListViewHolder, position: Int) {
        val effectModel = list.get(holder.adapterPosition)
        Glide.with(holder.itemView.context).asBitmap().load(effectModel.effectPreviewDrawableImage).into(holder.rowCrackPreviewBinding.ivCrackPreview)
        holder.rowCrackPreviewBinding.root.setOnClickListener {
            onClick(effectModel)
        }
    }
}