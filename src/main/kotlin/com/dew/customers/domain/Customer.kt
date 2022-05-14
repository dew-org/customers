package com.dew.customers.domain

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import java.time.Clock
import java.time.Instant
import java.util.Date

@Introspected
@ReflectiveAccess
data class Customer @Creator @BsonCreator constructor(
    @field:BsonProperty("_id")
    @param:BsonProperty("_id")
    @field:BsonId val
    id: String,

    @field:BsonProperty("name")
    @param:BsonProperty("name")
    val name: String,

    @field:BsonProperty("lastName")
    @param:BsonProperty("lastName")
    val lastName: String,

    @field:BsonProperty("phoneNumber")
    @param:BsonProperty("phoneNumber")
    var phoneNumber: String?,

    @field:BsonProperty("email")
    @param:BsonProperty("email")
    var email: String?,

    @field:BsonProperty("createdAt")
    @param:BsonProperty("createdAt")
    val createdAt: Date = Date.from(Instant.now(Clock.systemUTC()))
) {

    @field:BsonProperty("updatedAt")
    var updatedAt: Date? = null
}
