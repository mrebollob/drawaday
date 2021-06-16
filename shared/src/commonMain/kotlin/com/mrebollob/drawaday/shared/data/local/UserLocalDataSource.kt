package com.mrebollob.drawaday.shared.data.local

interface UserLocalDataSource {

    fun getUserName(): String

    fun saveUserName(userName: String)

    fun getIsNewUser(): Boolean

    fun setIsNewUser(isNewUser: Boolean)
}