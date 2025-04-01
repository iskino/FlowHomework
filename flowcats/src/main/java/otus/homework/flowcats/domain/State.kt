package otus.homework.flowcats.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L
private const val RETRY_ATTEMPT_COUNT = 3

sealed interface State<out T> {
    data object Loading : State<Nothing>
    data class Success<T>(val data: T) : State<T>
    data class Error(val exception: Throwable? = null) : State<Nothing>
}

fun <T: Any> Flow<T>.asState(): Flow<State<T>> {
    return this
        .map<T, State<T>> { State.Success(it) }
        .onStart { emit(State.Loading) }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { emit(State.Error(it)) }
}