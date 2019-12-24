package com.cabify.carpooling.data.entities

import com.cabify.carpooling.domain.Car
import org.springframework.data.domain.Persistable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "car")
data class CarEntity constructor(
        @NotNull @Id private var id: Long,
        @NotNull var seats: Int
) : Persistable<Long> {

    @Transient
    var new: Boolean = true

    constructor(id: Long, seats: Int, new: Boolean) : this(id, seats) {
        this.new = new
    }

    override fun getId(): Long {
        return this.id
    }

    override fun isNew(): Boolean {
        return new
    }

}

fun CarEntity.toCar(): Car {
    return Car(this.id, seats)
}

fun Car.toCarEntity(): CarEntity {
    return CarEntity(this.id, seats)
}