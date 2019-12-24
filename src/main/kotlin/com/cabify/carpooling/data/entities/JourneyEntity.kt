package com.cabify.carpooling.data.entities

import com.cabify.carpooling.domain.Journey
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.domain.Persistable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity(name = "journey")
data class JourneyEntity constructor(
        @Id private var id: Long,
        @NotNull var people: Int,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "car_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        var car: CarEntity?) : Persistable<Long> {

    @Transient
    var new: Boolean = true

    constructor(id: Long, people: Int, car: CarEntity?, new: Boolean) : this(id, people, car) {
        this.new = new
    }

    override fun getId(): Long {
        return this.id
    }

    override fun isNew(): Boolean {
        return new
    }

}

fun JourneyEntity.toJourney(): Journey {
    return Journey(this.id, people, car?.toCar())
}


fun Journey.toJourneyEntity(): JourneyEntity {
    return JourneyEntity(this.id, people, this.car?.toCarEntity())
}