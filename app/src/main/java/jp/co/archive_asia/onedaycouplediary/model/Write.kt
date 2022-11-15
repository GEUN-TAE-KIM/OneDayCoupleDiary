package jp.co.archive_asia.onedaycouplediary.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.util.*

@Entity
@Parcelize
data class Write(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    var date: Long, // "yyyy/MM/dd"
    //var dateMonth: MonthDay

): Parcelable