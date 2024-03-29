package com.ringov.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Url(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var url: String = "",
    var isPublic: Boolean = false
)
