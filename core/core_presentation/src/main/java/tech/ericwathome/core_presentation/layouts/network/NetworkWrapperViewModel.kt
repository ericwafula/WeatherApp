package tech.ericwathome.core_presentation.layouts.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.ericwathome.core_domain.repository.NetworkStatusManager
import tech.ericwathome.core_domain.utils.NetworkStatus
import javax.inject.Inject

@HiltViewModel
class NetworkWrapperViewModel @Inject constructor(
    private val networkStatusManager: NetworkStatusManager
) : ViewModel() {

    var status: NetworkStatus by mutableStateOf(NetworkStatus.Connected)
        private set
    var isVisible: Boolean by mutableStateOf(false)
        private set

    init {
        setStatus()
    }

    fun onDismiss() {
        isVisible = false
    }

    @JvmName("initStatus")
    private fun setStatus() {
        viewModelScope.launch {
            networkStatusManager.getCurrentStatus().collect { networkStatus ->
                status = networkStatus

                isVisible = when (networkStatus) {
                    NetworkStatus.Connected -> {
                        false
                    }

                    NetworkStatus.Disconnected -> {
                        true
                    }

                    NetworkStatus.Disconnecting -> {
                        true
                    }
                }
            }
        }
    }

}