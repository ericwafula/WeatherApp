package tech.ericwathome.weatherapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import tech.ericwathome.weatherapp.BuildConfig
import tech.ericwathome.weatherapp.domain.util.DataError
import timber.log.Timber
import tech.ericwathome.core.domain.util.Result as DataResult

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): DataResult<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
): DataResult<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): DataResult<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): DataResult<T, DataError.Network> {
    val response =
        try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            Timber.e(e)
            return DataResult.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Timber.e(e)
            e.printStackTrace()
            return DataResult.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.e(e)
            e.printStackTrace()
            return DataResult.Error(DataError.Network.UNKNOWN)
        }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): DataResult<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> DataResult.Success(response.body<T>())
        401 -> DataResult.Error(DataError.Network.UNAUTHORIZED)
        408 -> DataResult.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> DataResult.Error(DataError.Network.CONFLICT)
        413 -> DataResult.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> DataResult.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> DataResult.Error(DataError.Network.SERVER_ERROR)
        else -> DataResult.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.CITY_API) -> route
        route.contains(BuildConfig.OPEN_WEATHER_BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.OPEN_WEATHER_BASE_URL + route
        else -> BuildConfig.OPEN_WEATHER_BASE_URL + "/$route"
    }
}