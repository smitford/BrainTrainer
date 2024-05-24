package main.work.braintrainercompose.settings.data

import android.content.Context
import android.content.Intent
import main.work.braintrainercompose.settings.domain.api.DataSenderRepo

class DataSenderRepoImp(private val context: Context) : DataSenderRepo {
    override fun sendUrl(url: String) {
        try {
            Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
                context.startActivity(this, null)
            }
        } catch (e: Exception) {
            throw Exception("No intent")
        }

    }
}