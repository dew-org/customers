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
    HttpStatus save(@Valid CreateCustomerCommand command)

    @Get("{id}")
    HttpResponse<CustomerResponse> findById(@NotBlank String id)
}
