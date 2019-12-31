package com.cabify.carpooling.data.entities

import com.cabify.carpooling.domain.Journey
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.domain.Persistable
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "journey")
data class JourneyEntity constructor(
        @Id private var id: Long,
        @NotNull var people: Int,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
        @JoinColumn(name = "car_id")
        var car: CarEntity? = null,
        @Column(name = "create_date")
        @CreationTimestamp
        @Basic
        val createDate: Instant? = null
) : Persistable<Long> {

    @Transient
    var new: Boolean = true

    constructor(id: Long, people: Int, car: CarEntity?, createDate: Instant?, new: Boolean) : this(id, people, car, createDate) {
        this.new = new
    }

    constructor(id: Long, people: Int, new: Boolean) : this(id, people) {
        this.new = new
    }

    constructor(id: Long, people: Int) : this(id, people, car = null, createDate = null, new = true)

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

fun JourneyEntity.toJourney(): Journey {
    return Journey(this.id, people, car?.toCar())
}


fun Journey.toJourneyEntity(): JourneyEntity {
    return JourneyEntity(this.id, people, this.car?.toCarEntity())
}