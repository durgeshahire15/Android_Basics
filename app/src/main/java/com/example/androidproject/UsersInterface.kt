package com.example.androidproject

import retrofit2.Response
import retrofit2.http.GET

interface UsersInterface {
    @GET("/users")
    suspend fun getUsers():Response<UsersList>
}