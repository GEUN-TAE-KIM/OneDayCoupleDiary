package jp.co.archive_asia.onedaycouplediary.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diary(

    var id: String = "",
    var title: String = "",
    var content: String = "",
    var date: Long,
    var color: ColorSelect

): Parcelable