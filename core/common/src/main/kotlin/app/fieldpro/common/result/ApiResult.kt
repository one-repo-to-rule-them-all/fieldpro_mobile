package app.fieldpro.common.result

/**
 * Result envelope used by every repository call.
 *
 * - [Success] — a valid, typed response from the backend.
 * - [Error]   — the backend returned a structured error ([code], [message], [httpStatus]).
 * - [NetworkError] — the call never made a round trip (DNS, TLS, socket, timeout).
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(
        val code: String,
        val message: String,
        val httpStatus: Int,
    ) : ApiResult<Nothing>

    data class NetworkError(val cause: Throwable) : ApiResult<Nothing>
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success -> ApiResult.Success(transform(data))
    is ApiResult.Error -> this
    is ApiResult.NetworkError -> this
}

inline fun <T> ApiResult<T>.onSuccess(block: (T) -> Unit): ApiResult<T> = also {
    if (this is ApiResult.Success) block(data)
}

inline fun <T> ApiResult<T>.onError(block: (ApiResult.Error) -> Unit): ApiResult<T> = also {
    if (this is ApiResult.Error) block(this)
}
