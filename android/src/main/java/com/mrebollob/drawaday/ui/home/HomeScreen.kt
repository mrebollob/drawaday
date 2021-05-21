package com.mrebollob.drawaday.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.domain.model.DrawImage
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    navigateToDrawImage: (DrawImage) -> Unit,
) {
    val homeViewModel = getViewModel<HomeViewModel>()
    val drawImages = homeViewModel.drawImages.collectAsState()

    HomeScreen(
        navigateToDrawImage = navigateToDrawImage,
        drawImages = drawImages.value
    )
}

@Composable
fun HomeScreen(
    drawImages: List<DrawImage>,
    navigateToDrawImage: (DrawImage) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Draw a day") })
        }) {
        LazyColumn {
            items(drawImages) { drawImage ->
                PersonView(drawImage, navigateToDrawImage)
            }
        }
    }
}

@Composable
fun PersonView(
    drawImage: DrawImage,
    navigateToDrawImage: (person: DrawImage) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { navigateToDrawImage(drawImage) })
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberCoilPainter(
                drawImage.drawing,
                fadeIn = true
            ),
            modifier = Modifier.size(60.dp),
            contentDescription = drawImage.title,
        )
        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(text = drawImage.title, style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Composable
fun PreviewHomeScreen() {
    val drawImages: List<DrawImage> = listOf(
        DrawImage(
            id = "test",
            title = "test",
            drawing = "test",
            source = "test",
            publishDate = LocalDate.of(2021, 5, 12),
        )
    )


    DrawADayTheme {
        HomeScreen(
            drawImages = drawImages,
            navigateToDrawImage = { /*TODO*/ }
        )
    }
}
