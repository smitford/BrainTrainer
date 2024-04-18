package main.work.braintrainercompose.settings.domain.api

interface DataSenderRepo {
    fun sendUrl(url: String)
}