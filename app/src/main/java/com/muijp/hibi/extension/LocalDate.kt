package com.muijp.hibi.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val LocalDate.formattedDate: String
    get() = this.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))