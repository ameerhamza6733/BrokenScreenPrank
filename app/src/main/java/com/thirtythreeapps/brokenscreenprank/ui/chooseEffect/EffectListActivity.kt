package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityEffectListBinding
import com.thirtythreeapps.brokenscreenprank.databinding.RowCrackPreviewBinding

class EffectListActivity : AppCompatActivity() {
    private val viewModel: EffectListActivityViewModel by viewModels()
    private lateinit var binding : ActivityEffectListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEffectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutToolbar.tvToolBarTitle.text = getString(R.string.crack_effec)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = EffectListAdapter(viewModel.getCrackEffect())
    }
}