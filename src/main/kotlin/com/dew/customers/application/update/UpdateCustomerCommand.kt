package com.dew.customers.application.update

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class UpdateCustomerCommand(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val lastName: String,
    
    var phoneNumber: String?,
    var email: String?,
)
