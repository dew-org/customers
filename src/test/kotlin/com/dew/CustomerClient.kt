package com.dew

import com.dew.customers.application.CustomerResponse
import com.dew.customers.application.create.CreateCustomerCommand
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Client("/customers")
interface CustomerClient {

    @Post
    fun save(@Valid command: CreateCustomerCommand): HttpStatus

    @Get("{id}")
    fun findById(@NotBlank id: String): HttpResponse<CustomerResponse>
}