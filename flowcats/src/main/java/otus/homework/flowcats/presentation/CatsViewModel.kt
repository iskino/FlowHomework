package otus.homework.flowcats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import otus.homework.flowcats.data.CatsRepositoryImpl
import otus.homework.flowcats.domain.CatsInteractor
import otus.homework.flowcats.domain.asState
import otus.homework.flowcats.domain.State

class CatsViewModel(
    catsInteractor: CatsInteractor
) : ViewModel() {

    companion object {
        private const val DEFAULT_TIMEOUT = 5000L
    }

    val catsState: StateFlow<Result> =
        catsInteractor.listenForCatFacts()
            .asState()
            .map { state ->
                when (state) {
                    is State.Error -> Result.Error(state.exception)
                    State.Loading -> Result.Loading
                    is State.Success -> Result.Success(state.data)
                }
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = Result.Loading,
                started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT)
            )
}

class CatsViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    private val catsRepository = CatsRepositoryImpl()
    private val catsInteractor = CatsInteractor(catsRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsInteractor) as T
}
