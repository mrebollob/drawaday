package com.mrebollob.drawaday.ui.home.feed

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils

@Composable
fun DrawImageCardTop(
    drawing: DrawImage,
    onDrawingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.home_screen_today_category),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(vertical = 4.dp)
                .wrapContentHeight()
        )
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = 8.dp,
            modifier = modifier
                .clickable { onDrawingClick(drawing.id) },
            content = {
                Image(
                    painter = rememberCoilPainter(
                        request = drawing.drawing,
                        previewPlaceholder = R.drawable.placeholder_1,
                    ),
                    contentDescription = drawing.title,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .aspectRatio(1.45f)
                        .fillMaxWidth()
                )
            }
        )
    }
}

@Preview("Default colors")
@Preview("Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Font scaling 1.5", fontScale = 1.5f)
@Preview("Large screen", device = Devices.PIXEL_C)
@Composable
fun DrawImageCardTopPreview() {
    val drawing = TestDataUtils.getTestDrawImage("#1")
    DrawADayTheme {
        Surface {
            DrawImageCardTop(
                drawing = drawing,
                onDrawingClick = { /* TODO */ }
            )
        }
    }
}
