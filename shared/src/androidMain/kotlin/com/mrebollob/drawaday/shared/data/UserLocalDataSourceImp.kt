package com.mrebollob.drawaday.shared.data

import android.content.Context
import com.mrebollob.drawaday.shared.data.local.UserLocalDataSource

class UserLocalDataSourceImp(
    context: Context
) : UserLocalDataSource {

    private val sharedPref = context.getSharedPreferences(
        USER_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE
    )

    override fun getUserName(): String {
        return sharedPref.getString(USER_NAME_KEY, "") ?: ""
    }

    override fun saveUserName(userName: String) {
        with(sharedPref.edit()) {
            putString(USER_NAME_KEY, userName)
            apply()
        }
    }

    override fun getIsNewUser(): Boolean = sharedPref.getBoolean(IS_NEW_USER_KEY, true)

    override fun setIsNewUser(isNewUser: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(IS_NEW_USER_KEY, isNewUser)
            apply()
        }
    }

    companion object {
        private const val USER_NAME_KEY = "user_name_key"
        private const val IS_NEW_USER_KEY = "is_new_user_key"
        private const val USER_PREFERENCES_FILE_KEY = "user_preferences_file_key"
    }
}
