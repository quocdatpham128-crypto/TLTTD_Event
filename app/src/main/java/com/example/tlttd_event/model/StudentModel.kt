package com.example.tlttd_event.model

import java.io.Serializable

data class StudentModel(
    val fullName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val socialNetworks: String
) : Serializable