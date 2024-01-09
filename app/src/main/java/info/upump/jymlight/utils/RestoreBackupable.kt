package info.upump.jymlight.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

interface RestoreBackupable {
    suspend fun getSendIntent(context: Context): Intent
    suspend fun restore(uri: Uri, context: Context, _stateLoading: MutableStateFlow<Boolean>)
}