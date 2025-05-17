package tech.ericwathome.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.ericwathome.designsystem.R
import tech.ericwathome.designsystem.WeatherAppTheme

@Composable
fun WeatherAppToggleChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    withRadioButton: Boolean = true,
    isSelected: Boolean = false,
    text: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        if (withRadioButton) {
            WeatherAppRadioButton(
                onClick = { },
                isSelected = isSelected
            )
        }
    }
}

@Preview
@Composable
private fun WeatherAppToggleChipPreview() {
    WeatherAppTheme {
        WeatherAppToggleChip(
            modifier = Modifier.fillMaxWidth(),
            isSelected = true,
            onClick = { },
            text = stringResource(R.string.nairobi_kenya)
        )
    }
}