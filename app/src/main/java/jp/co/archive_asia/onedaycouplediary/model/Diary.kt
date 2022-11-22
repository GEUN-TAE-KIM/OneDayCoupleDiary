package jp.co.archive_asia.onedaycouplediary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Diary(

    val id: String = "",
    val title: String = "",
    val content: String = "",
    var diary_id: String = "",
    /*var date: Long,
    var color: ColorSelect*/

): Parcelable