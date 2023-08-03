package com.broken.screen.crack.prank.crackedscreen.ui.commen

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.broken.screen.crack.prank.crackedscreen.R

class RateMeDialogFragment : DialogFragment() {
    private var totalRating  = 0f
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.rate_me_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btRate = view.findViewById<TextView>(R.id.btRateMe)
        val ratingBart = view.findViewById<RatingBar>(R.id.ratingBar)
        ratingBart.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
           totalRating = rating
        }
        btRate.setOnClickListener {
           if (totalRating>3.0){
               try {
                   startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context?.packageName}")))
               } catch (e: ActivityNotFoundException) {
                   startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context?.packageName}")))
               }
           }else{
               try {
                   val intent = Intent(Intent.ACTION_SENDTO)
                   intent.data = Uri.parse("mailto:") // only email apps should handle this
                   intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.feed_back_emil)))
                   intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback ${getString(R.string.app_name)}")
                   startActivity(intent)
               }catch (e:Exception){

               }
           }
        }
    }
}