package com.dew.customers.application

import java.time.Instant

data class CustomerResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val phoneNumber: String?,
    val email: String?,
    val createdAt: Instant,
    var updatedAt: Instant?
)
