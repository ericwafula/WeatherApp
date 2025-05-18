package tech.ericwathome.designsystem.assets

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import tech.ericwathome.designsystem.R

object AppIcons {
    val SearchOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_search)

    val CloseOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_close)

    val WindOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_wind)

    val DropletOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_droplet)

    val EyeOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_eye)

    val FrownOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_eye)
}