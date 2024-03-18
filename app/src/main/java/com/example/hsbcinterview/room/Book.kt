package com.example.hsbcinterview.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: Int,
    val author: String,
    val title: String,
    var details: String
)