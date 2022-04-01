package com.dew.customers.domain

import reactor.core.publisher.Mono
import javax.validation.Valid

interface CustomerRepository {
    fun save(@Valid customer: Customer): Mono<Boolean>
}