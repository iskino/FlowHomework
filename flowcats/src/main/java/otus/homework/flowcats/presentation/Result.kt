package otus.homework.flowcats.presentation

import otus.homework.flowcats.data.Fact

sealed interface Result {
    data object Loading : Result
    data class Success(val data: Fact) : Result
    data class Error(val throwable: Throwable? = null) : Result
}