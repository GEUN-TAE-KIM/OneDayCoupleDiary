package jp.co.archive_asia.onedaycouplediary.view.util

import jp.co.archive_asia.onedaycouplediary.model.ColorSelect

object DiaryUtils {

    fun verifyData(title: String, content: String): Boolean {
        return !(title.isEmpty() || content.isEmpty())
    }

    fun parsePriority(colorSelect: String): ColorSelect {
        return when(colorSelect){
            "MAIN_PINK" -> { ColorSelect.MAIN_PINK }
            "PINK_100" -> { ColorSelect.PINK_100 }
            "PINK_200" -> { ColorSelect.PINK_200 }
            "PINK_300" -> { ColorSelect.PINK_300 }
            "PINK_400" -> { ColorSelect.PINK_400 }
            else -> ColorSelect.PINK_400
        }
    }

}