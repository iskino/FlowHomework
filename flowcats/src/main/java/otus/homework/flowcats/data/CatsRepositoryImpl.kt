package otus.homework.flowcats.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.domain.CatsRepository

class CatsRepositoryImpl : CatsRepository {

    companion object {
        const val REFRESH_INTERVAL_MS: Long = 5000L
    }

    private val catsService = DiContainer().service

    override fun listenForCatFacts() = flow {
        while (true) {
            emit(catsService.getCatFact())
            delay(REFRESH_INTERVAL_MS)
        }
    }
}