package jp.co.archive_asia.onedaycouplediary.firestore.response

sealed class ResultStatus<T> {
    data class Success<T>(val data: T) : ResultStatus<T>()
    data class Error<T>(val message: String) : ResultStatus<T>()
}
