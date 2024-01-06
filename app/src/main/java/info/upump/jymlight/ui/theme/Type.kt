package info.upump.jymlight.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import info.upump.jymlight.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
val MyTextLabel12: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
val MyTextLabel16: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color =  MaterialTheme.colorScheme.onTertiary
        )
    }

val MyTextHeader16: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

val MyTextTitleLabel16: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

val MyTextTitleHeaderl16: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
val MyTextTitleLabel16WithColor: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )
    }

val MyTextTitleLabelWithColor: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = 12.sp,
        )
    }


val MyTextTitleLabel20: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

val MyWatchTitleLabel20: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

/*
val MyOutlineTextTitleLabel20Text: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
                fontSize = 20.sp,
                drawStyle = Stroke(
                    miter = 5f, width = 4f, join = StrokeJoin.Round
                ), color = colorResource(id = R.color.colorBackgroundCardView)
            )
    }
*/
val MyOutlineTextTitleLabel20Text: TextStyle
    @Composable
    get() {
        return TextStyle.Default.copy(
            fontSize = 24.sp,
            shadow = Shadow(color = Color.Black, offset = Offset(4f, 4f), blurRadius = 8f),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
