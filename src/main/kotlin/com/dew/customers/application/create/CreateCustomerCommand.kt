package com.dew.customers.application.create

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class CreateCustomerCommand(
    @field:NotBlank val id: String,
    @field:NotBlank val name: String,
    @field:NotBlank val lastName: String,
    var phoneNumber: String?,
    var email: String?,
) {
    constructor(id: String, name: String, lastName: String) : this(id, name, lastName, null, null)
}
