package com.dew

import com.dew.customers.domain.Customer
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import javax.validation.Valid

@Client("/customers")
interface CustomerClient {

    @Post
    fun save(@Valid customer: Customer): HttpStatus
}