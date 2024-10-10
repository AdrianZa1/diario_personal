package com.example.DiarioApplication.data.Etiqueta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface EtiquetaDao {
    @Insert
    suspend fun insertarEtiqueta(etiqueta: Etiqueta)

    @Query("SELECT * FROM etiquetas ORDER BY nombre ASC")
    suspend fun obtenerTodasLasEtiquetas(): List<Etiqueta>

    @Update
    suspend fun actualizarEtiqueta(etiqueta: Etiqueta)

    @Delete
    suspend fun eliminarEtiqueta(etiqueta: Etiqueta)
}
