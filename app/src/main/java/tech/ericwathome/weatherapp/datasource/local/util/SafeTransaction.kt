@file:Keep

package tech.ericwathome.weatherapp.datasource.local.util

import android.database.sqlite.SQLiteFullException
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.ericwathome.weatherapp.domain.util.DataError
import tech.ericwathome.weatherapp.domain.util.EmptyResult
import tech.ericwathome.weatherapp.domain.util.Result

suspend fun <T> safeTransaction(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T,
): EmptyResult<DataError.Local> {
    return withContext(dispatcher) {
        try {
            block()
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}