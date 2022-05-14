package com.dew.customers.domain

import reactor.core.publisher.Mono
import javax.validation.Valid
import javax.validation.constraints.NotBlank

interface CustomerRepository {
    fun save(@Valid customer: Customer): Mono<Boolean>

    fun findById(@NotBlank id: String): Mono<Customer>

    fun update(@Valid customer: Customer): Mono<Boolean>
}