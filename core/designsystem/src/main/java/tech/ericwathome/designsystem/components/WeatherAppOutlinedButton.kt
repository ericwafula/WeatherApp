package tech.ericwathome.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.ericwathome.designsystem.R
import tech.ericwathome.designsystem.WeatherAppTheme

@Composable
fun WeatherAppOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    val shape = remember { RoundedCornerShape(12.dp) }

    Box(
        modifier =
            modifier
                .height(56.dp)
                .clip(shape)
                .clickable(onClick = onClick)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                ),
        )
    }
}

@Preview
@Composable
private fun WeatherAppOutlinedButtonPreview() {
    WeatherAppTheme {
        WeatherAppOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            text = stringResource(R.string.continue_text),
        )
    }
}