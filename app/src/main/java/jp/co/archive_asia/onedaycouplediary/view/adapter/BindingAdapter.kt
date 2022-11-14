package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.model.Write
import java.time.LocalDate

@BindingAdapter("Check")
fun setReadCheck(imgView: ImageView, calDay: Write?) {
    val today = LocalDate.now().dayOfMonth
    if (calDay != null) {
        imgView.visibility = View.VISIBLE
        if (today == calDay.title.toInt()) imgView.setBackgroundResource(R.drawable.ic_baseline_fiber_manual_record_24)


    }
}
