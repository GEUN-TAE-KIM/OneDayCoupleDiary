package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import jp.co.archive_asia.onedaycouplediary.model.Diary

@BindingAdapter("Check")
fun setReadCheck(imgView: ImageView, calDay: Diary?) {
    if (calDay != null) {
        imgView.visibility = View.VISIBLE
    }
}
