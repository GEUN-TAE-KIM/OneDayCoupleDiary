package jp.co.archive_asia.onedaycouplediary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Write(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String

)