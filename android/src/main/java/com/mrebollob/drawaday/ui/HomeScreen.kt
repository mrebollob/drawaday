package com.mrebollob.drawaday.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import com.mrebollob.drawaday.domain.model.DrawImage
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(paddingValues: PaddingValues, imageSelected: (image: DrawImage) -> Unit) {
    val homeViewModel = getViewModel<HomeViewModel>()
    val drawImages = homeViewModel.drawImages.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Draw a day") })
        }) {
        LazyColumn(contentPadding = paddingValues) {
            items(drawImages.value) { drawImage ->

                PersonView(drawImage, imageSelected)
            }
        }
    }
}


@Composable
fun PersonView(
    drawImage: DrawImage,
    imageSelected: (image: DrawImage) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { imageSelected(drawImage) })
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberCoilPainter(
                request = drawImage.image,
                shouldRefetchOnSizeChange = { _, _ -> false },
            ),
            contentDescription = drawImage.title,
            modifier = Modifier.size(60.dp),
        )

        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(text = drawImage.title, style = TextStyle(fontSize = 20.sp))
        }
    }
}
