package tech.ericwathome.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import tech.ericwathome.designsystem.WeatherAppTheme
import tech.ericwathome.designsystem.assets.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppToolbarLayout(
    title: String,
    actions: @Composable RowScope.() -> Unit = { },
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                    )
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                actions = actions,
            )
        },
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) { content() }
    }
}

@Preview
@Composable
private fun WeatherAppToolbarLayoutPreview() {
    WeatherAppTheme {
        WeatherAppToolbarLayout(
            title = "Paris, France",
            actions = {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = AppIcons.SearchOutlined,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }
            },
        ) {
            Text(
                text = "Test",
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
            Text(
                text = "Test",
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }
    }
}