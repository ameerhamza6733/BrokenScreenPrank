package com.broken.screen.crack.prank.crackedscreen.ui.chooseEffect

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.broken.screen.crack.prank.crackedscreen.R

class HelpDialogeFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.help_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch = view.findViewById<SwitchCompat>(R.id.switch1)
        val btGotIt = view.findViewById<TextView>(R.id.btGotIt)
        btGotIt.setOnClickListener {
            dismiss()
        }
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
           if (isVisible){
               switch.isChecked = false
           }
        },1000)
    }
}