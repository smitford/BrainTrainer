package main.work.braintrainercompose.settings.domain.use_case

import main.work.braintrainercompose.settings.domain.UrlSenderUseCase
import main.work.braintrainercompose.settings.domain.api.DataSenderRepo

class UrlSenderImp(private val repository: DataSenderRepo) : UrlSenderUseCase {
    override fun execute(url: String) {
        repository.sendUrl(url = url)
    }
}