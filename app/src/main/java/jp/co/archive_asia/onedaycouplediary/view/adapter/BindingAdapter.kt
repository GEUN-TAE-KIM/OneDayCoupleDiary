package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import jp.co.archive_asia.onedaycouplediary.model.CalendarData

@BindingAdapter("setDate")
fun TextView.setDate(item: CalendarData?) {
    item?.let { text = it.dayOfMonth.toString() }
}
