package jp.co.archive_asia.onedaycouplediary.view.util

object WriteUtils {

    fun verifyData(title: String, content: String): Boolean {
        return !(title.isEmpty() || content.isEmpty())
    }

}