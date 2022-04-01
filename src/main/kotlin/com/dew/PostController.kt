package com.dew

import com.dew.customers.domain.Customer
import com.dew.customers.domain.CustomerRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.CONFLICT
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import reactor.core.publisher.Mono
import javax.validation.Valid

@Controller("/customers")
open class PostController(private val customerRepository: CustomerRepository) {

    @Post
    open fun save(@Valid customer: Customer): Mono<HttpStatus> {
        return customerRepository.save(customer).map { added: Boolean ->
            if (added) CREATED else CONFLICT
        }
    }
}