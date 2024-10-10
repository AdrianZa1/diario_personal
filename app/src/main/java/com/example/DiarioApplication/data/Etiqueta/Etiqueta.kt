package com.example.DiarioApplication.data.Etiqueta

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etiquetas")
data class Etiqueta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val descripcion: String? = null
)
