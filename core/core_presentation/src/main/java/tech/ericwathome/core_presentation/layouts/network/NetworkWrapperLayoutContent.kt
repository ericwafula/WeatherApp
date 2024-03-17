package tech.ericwathome.core_presentation.layouts.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.ericwathome.core_domain.utils.NetworkStatus
import tech.ericwathome.core_presentation.R
import tech.ericwathome.droplets.theme.WeatherAppTheme

@Composable
fun NetworkWrapperLayout(
    viewModel: NetworkWrapperViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    NetworkWrapperLayoutContent(
        networkStatus = viewModel.status,
        isVisible = viewModel.isVisible,
        onClickDismiss = viewModel::onDismiss,
        content = content
    )
}

@Composable
fun NetworkWrapperLayoutContent(
    networkStatus: NetworkStatus,
    isVisible: Boolean,
    onClickDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val backGroundColor = when (networkStatus) {
        NetworkStatus.Connected -> {
            MaterialTheme.colorScheme.primary
        }

        NetworkStatus.Disconnected -> {
            MaterialTheme.colorScheme.error
        }

        NetworkStatus.Disconnecting -> {
            MaterialTheme.colorScheme.error
        }
    }

    val contentColor = when (networkStatus) {
        NetworkStatus.Connected -> {
            MaterialTheme.colorScheme.onPrimary
        }

        NetworkStatus.Disconnected -> {
            MaterialTheme.colorScheme.onError
        }

        NetworkStatus.Disconnecting -> {
            MaterialTheme.colorScheme.onError
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (isVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(backGroundColor)
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.no_internet),
                        color = contentColor,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextButton(modifier = Modifier.align(Alignment.End), onClick = onClickDismiss) {
                        Text(
                            text = stringResource(id = R.string.dismiss),
                            color = contentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NetworkWrapperLayoutPreview() {
    WeatherAppTheme {
        NetworkWrapperLayoutContent(
            networkStatus = NetworkStatus.Disconnected,
            isVisible = true,
            onClickDismiss = { }
        ) {

        }
    }
}