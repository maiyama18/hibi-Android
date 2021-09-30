package com.muijp.hibi.extension

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val ZonedDateTime.formattedDateTime: String
    get() = this.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))

val ZonedDateTime.formattedTime: String
    get() = this.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
