package com.mrebollob.drawaday.shared.data.local

interface UserLocalDataSource {
    fun getIsNewUser(): Boolean

    fun setIsNewUser(isNewUser: Boolean)
}