package com.samueljuma.petapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cat")
data class CatEntity(
    @PrimaryKey
    val id: Int,
    val owner: String,
    val tags: List<String>,
    val createdAt: String,
    val updatedAt: String
)