package com.muijp.hibi.provider

import android.content.Context

class StringProvider(private val context: Context) {
    fun getString(id: Int): String {
        return context.getString(id)
    }
}