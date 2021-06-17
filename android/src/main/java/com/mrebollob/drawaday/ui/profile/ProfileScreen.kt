package com.mrebollob.drawaday.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.InsetAwareTopAppBar
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.supportWideScreen

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                elevation = 0.dp,
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_up)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ProfileScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .supportWideScreen()
                .background(MaterialTheme.colors.surface)
        )
    }
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text("Button")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text("Button")
        }
    }
}


@Preview("Profile screen")
@Preview("Profile screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Profile screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun ProfileUserNameScreenPreview() {
    DrawADayTheme {
        ProfileScreen(
            onBack = { /*TODO*/ },
        )
    }
}