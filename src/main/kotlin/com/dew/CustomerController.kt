package com.dew

import com.dew.customers.application.CustomerResponse
import com.dew.customers.application.CustomerService
import com.dew.customers.application.create.CreateCustomerCommand
import com.dew.customers.application.update.UpdateCustomerCommand
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import reactor.core.publisher.Mono
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Controller("/customers")
@Secured(SecurityRule.IS_AUTHENTICATED)
open class CustomerController(private val customerService: CustomerService) {

    @Post
    open fun save(@Valid command: CreateCustomerCommand): Mono<HttpStatus> {
        return customerService.save(command).map { added: Boolean ->
            if (added) HttpStatus.CREATED else HttpStatus.CONFLICT
        }
    }

    @Get("{id}")
    open fun findById(@NotBlank id: String): Mono<HttpResponse<CustomerResponse>> {
        return customerService.findById(id)
            .map { customer -> if (customer != null) HttpResponse.ok(customer) else HttpResponse.notFound() }
    }

    @Put("{id}")
    open fun update(
        @NotBlank id: String,
        @Valid command: UpdateCustomerCommand
    ): Mono<HttpResponse<UpdateCustomerCommand>> {
        return customerService.update(id, command).map { updated: Boolean ->
            if (updated) HttpResponse.ok(command) else HttpResponse.notFound()
        }
    }
}