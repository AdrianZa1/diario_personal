package com.example.DiarioApplication.data.Etiqueta

class EtiquetaRepository(private val etiquetaDao: EtiquetaDao) {

    suspend fun insertarEtiqueta(etiqueta: Etiqueta) {
        etiquetaDao.insertarEtiqueta(etiqueta)
    }

    suspend fun obtenerTodasLasEtiquetas(): List<Etiqueta> {
        return etiquetaDao.obtenerTodasLasEtiquetas()
    }

    suspend fun actualizarEtiqueta(etiqueta: Etiqueta) {
        etiquetaDao.actualizarEtiqueta(etiqueta)
    }

    suspend fun eliminarEtiqueta(etiqueta: Etiqueta) {
        etiquetaDao.eliminarEtiqueta(etiqueta)
    }
}
