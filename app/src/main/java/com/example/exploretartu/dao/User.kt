package com.example.exploretartu.dao

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val currentTask: Task
)
{ constructor(): this(
    "",
    "",
    "",
    Task()
)
}