package jp.co.archive_asia.onedaycouplediary.firestore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val id: String = "",
    val email: String = ""
) : Parcelable