package com.muijp.hibi.provider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(id: Int): String {
        return context.getString(id)
    }
}