package otus.homework.flowcats.domain

import kotlinx.coroutines.flow.Flow
import otus.homework.flowcats.data.Fact

interface CatsRepository {
    fun listenForCatFacts(): Flow<Fact>
}