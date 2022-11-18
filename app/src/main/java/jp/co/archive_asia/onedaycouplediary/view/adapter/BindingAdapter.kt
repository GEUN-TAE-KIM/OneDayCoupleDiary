package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.model.ColorSelect
import jp.co.archive_asia.onedaycouplediary.model.ColorSelect.*
import jp.co.archive_asia.onedaycouplediary.model.Diary

@BindingAdapter("Check")
fun setReadCheck(imgView: ImageView, calDay: Diary?) {
    if (calDay != null) {
        imgView.visibility = View.VISIBLE
    }
}

@BindingAdapter("ColorSelect")
fun parsePriorityColor(cardView: CardView, color: ColorSelect?){
    when(color){
        MAIN_PINK -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.main_pink)) }
        PINK_100 -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink_100)) }
        PINK_200 -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink_200)) }
        PINK_300 -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink_300)) }
        PINK_400 -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink_400)) }

        else -> {}
    }
}

@BindingAdapter("test")
fun test(imgView: ImageView, color: ColorSelect?) {
    when(color) {
        MAIN_PINK -> {imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)}
        PINK_100 -> {imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)}
        PINK_200 -> {imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)}
        PINK_300 -> {imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)}
        PINK_400 -> {imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)}
        else -> {}
    }

}
