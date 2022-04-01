package com.dew.customers.domain

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

@Introspected
data class Customer @Creator @BsonCreator constructor(
    @field:BsonProperty("id") @param:BsonProperty("id") val id: String,
    @field:BsonProperty("name") @param:BsonProperty("name") val name: String,
    @field:BsonProperty("lastName") @param:BsonProperty("lastName") val lastName: String,
    @field:BsonProperty("phoneNumber") @param:BsonProperty("phoneNumber") var phoneNumber: String?,
    @field:BsonProperty("email") @param:BsonProperty("email") var email: String?,
) {
    constructor(id: String, name: String, lastName: String) : this(id, name, lastName, null, null)
}
