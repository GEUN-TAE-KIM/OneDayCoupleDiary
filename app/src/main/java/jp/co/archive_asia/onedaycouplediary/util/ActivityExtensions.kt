package jp.co.archive_asia.onedaycouplediary.util

import android.app.Activity
import android.content.Intent

fun <T> Activity.startActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
}