package tech.ericwathome.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.ericwathome.designsystem.R
import tech.ericwathome.designsystem.WeatherAppTheme
import tech.ericwathome.designsystem.assets.AppIcons

@Composable
fun WeatherAppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    leadingIcon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    var isFocused by remember { mutableStateOf(false) }
    val outlineColor =
        if (isFocused) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.secondary.copy(0.4f)
        }
    val backgroundColor =
        if (isFocused) {
            MaterialTheme.colorScheme.secondary.copy(0.05f)
        } else {
            Color.Transparent
        }
    val shape = remember { RoundedCornerShape(12.dp) }

    BasicTextField(
        modifier =
            modifier
                .height(56.dp)
                .onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
        decorationBox = { innerBox ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor, shape = shape)
                        .border(width = 1.dp, color = outlineColor, shape = shape)
                        .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingIcon?.let {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (value.isBlank() && !isFocused) {
                            Text(
                                text = hint,
                                style =
                                    MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(0.4f),
                                    ),
                            )
                        }
                        innerBox()
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun WeatherAppTextFieldPreview() {
    WeatherAppTheme {
        WeatherAppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = { },
            hint = stringResource(R.string.hint_enter_city_name),
            leadingIcon = AppIcons.SearchOutlined,
        )
    }
}