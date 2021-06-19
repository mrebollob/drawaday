package com.mrebollob.drawaday.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.BuildConfig
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.components.InsetAwareTopAppBar
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.supportWideScreen

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

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
            onTermsClick = {
                openLink(
                    context = context,
                    url = "https://www.notion.so/Terms-of-Service-1f19ed4f2c274cc389eda46cc0be7934"
                )
            },
            onPrivacyClick = {
                openLink(
                    context = context,
                    url = "https://www.notion.so/Privacy-policy-c5f9aed5301842b8aa958d43f55a705a"
                )
            },
            onContactClick = {
                openLink(
                    context = context,
                    url = "https://www.notion.so/Knowledge-Center-c90ed4784ceb4758a09f38128c6407e2"
                )
            },
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colors.surface)
                .supportWideScreen()

        )
    }
}

private fun openLink(context: Context, url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

@Composable
private fun ProfileScreenContent(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { onContactClick() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(stringResource(id = R.string.profile_screen_contact))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onTermsClick() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(stringResource(id = R.string.profile_screen_terms))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onPrivacyClick() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(stringResource(id = R.string.profile_screen_privacy))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            stringResource(
                id = R.string.profile_screen_version,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE.toString()
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview("Profile screen")
@Preview("Profile screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Profile screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun ProfileUserNameScreenPreview() {
    DrawADayTheme {
        ProfileScreenContent(
            onTermsClick = { /*TODO*/ },
            onPrivacyClick = { /*TODO*/ },
            onContactClick = { /*TODO*/ },
        )
    }
}
