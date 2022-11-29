package jp.co.archive_asia.onedaycouplediary.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diary(
    var user_id: String = "",
    var title: String = "",
    var content: String = "",
    var date: Long = 0,
    var color: ColorSelect = ColorSelect.MAIN_PINK
): Parcelable