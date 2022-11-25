package jp.co.archive_asia.onedaycouplediary.firestore.response

sealed class EmptyResult(
    val message: String? = null
) {
    class Success() : EmptyResult()
    class Error(message: String) : EmptyResult(message)
}