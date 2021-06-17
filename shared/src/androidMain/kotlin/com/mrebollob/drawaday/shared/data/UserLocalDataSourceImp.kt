package com.mrebollob.drawaday.shared.data

import android.content.Context
import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource
import com.soywiz.klock.DateTime

class UserLocalDataSourceImp(
    context: Context
) : UserLocalDataSource {

    private val sharedPref = context.getSharedPreferences(
        USER_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE
    )

    override fun setStartDate(date: DateTime) {
        with(sharedPref.edit()) {
            putString(USER_START_DATE_KEY, date.unixMillis.toString())
            apply()
        }
    }

    override fun getStartDate(): DateTime? {
        val unixMillis: Double? = try {
            sharedPref.getString(USER_START_DATE_KEY, null)?.toDouble()
        } catch (exception: NumberFormatException) {
            null
        }

        return if (unixMillis != null) {
            DateTime(unixMillis)
        } else {
            null
        }
    }

    companion object {
        private const val USER_START_DATE_KEY = "user_start_date_key"
        private const val USER_PREFERENCES_FILE_KEY = "user_preferences_file_key"
    }
}
