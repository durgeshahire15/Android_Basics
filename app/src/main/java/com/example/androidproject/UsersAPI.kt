package com.example.androidproject

import retrofit2.Response
import retrofit2.http.GET

interface UsersAPI {
    @GET("/users")
    suspend fun getUsers():Response<UsersList>
}