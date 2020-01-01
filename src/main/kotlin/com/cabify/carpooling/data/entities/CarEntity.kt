package com.cabify.carpooling.data.entities

import com.cabify.carpooling.domain.Car
import org.springframework.data.domain.Persistable
import javax.persistence.*
import javax.validation.constraints.NotNull
import kotlin.jvm.Transient

@Entity
@Table(name = "car")
data class CarEntity constructor(
        @Id private var id: Long,
        @NotNull var seats: Int
) : Persistable<Long> {

    @Transient
    var new: Boolean = true

    override fun getId(): Long {
        return this.id
    }

    override fun isNew(): Boolean {
        return new
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        this.new = false
    }
}

fun CarEntity.toCar(): Car {
    return Car(this.id, seats)
}

fun Car.toCarEntity(): CarEntity {
    return CarEntity(this.id, seats)
}