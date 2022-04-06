package com.dew.customers.application

import java.util.*

data class CustomerResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val phoneNumber: String?,
    val email: String?,
    val createdAt: Date,
    var updatedAt: Date?
)
