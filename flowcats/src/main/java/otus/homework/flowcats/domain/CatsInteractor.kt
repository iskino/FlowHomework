package otus.homework.flowcats.domain

class CatsInteractor(
    private val catsRepository: CatsRepository
) {

    fun listenForCatFacts() = catsRepository.listenForCatFacts()
}