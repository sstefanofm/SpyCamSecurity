package com.example.spycamsecurity.domain

data class CamWrapper(
    var url: String,
    val userType: UserType,
    val camera: Camera = Camera()
)
