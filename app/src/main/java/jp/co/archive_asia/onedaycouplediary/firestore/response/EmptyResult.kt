package jp.co.archive_asia.onedaycouplediary.firestore.response

sealed class EmptyResult {
    object Success : EmptyResult()
    data class Error(val message: String) : EmptyResult()
}