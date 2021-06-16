package com.mrebollob.drawaday.ui.home.feed

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrebollob.drawaday.R
import com.mrebollob.drawaday.shared.domain.model.User
import com.mrebollob.drawaday.ui.theme.CustomBrown500
import com.mrebollob.drawaday.ui.theme.CustomWhite1
import com.mrebollob.drawaday.ui.theme.DrawADayTheme
import com.mrebollob.drawaday.utils.TestDataUtils
import java.util.*


@Composable
fun UserGreetingsRow(
    user: User?,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = getUserGreetingsByTime(Calendar.getInstance())),
                style = MaterialTheme.typography.subtitle2,
                color = CustomWhite1
            )
            Text(
                text = user?.name?.capitalize(Locale.getDefault()) ?: "",
                style = MaterialTheme.typography.h5,
                color = CustomWhite1
            )
        }

        Card(
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .clickable { onProfileClick() },
            shape = CircleShape,
            elevation = 4.dp,
            backgroundColor = CustomBrown500
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = stringResource(id = R.string.home_screen_user_profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            )
        }
    }
}

@StringRes
private fun getUserGreetingsByTime(calendar: Calendar): Int =
    when (calendar[Calendar.HOUR_OF_DAY]) {
        in 0..11 -> {
            R.string.home_screen_user_greetings_morning
        }
        in 12..16 -> {
            R.string.home_screen_user_greetings_afternoon
        }
        in 17..20 -> {
            R.string.home_screen_user_greetings_evening
        }
        in 21..23 -> {
            R.string.home_screen_user_greetings_night
        }
        else -> {
            R.string.home_screen_user_greetings_morning
        }
    }

@Preview("User greetings row")
@Composable
fun UserGreetingsRowPreview() {
    val user = TestDataUtils.getTestUser("demo")
    DrawADayTheme {
        Surface {
            UserGreetingsRow(
                user = user,
                onProfileClick = { /*TODO*/ }
            )
        }
    }
}
