package com.example.DiarioApplication.data.Etiqueta

class OfflineEtiquetaRepository(private val etiquetaRepository: EtiquetaRepository) {

    suspend fun agregarEtiqueta(etiqueta: Etiqueta) {
        etiquetaRepository.insertarEtiqueta(etiqueta)
    }

    suspend fun obtenerEtiquetas(): List<Etiqueta> {
        return etiquetaRepository.obtenerTodasLasEtiquetas()
    }

    suspend fun actualizarEtiqueta(etiqueta: Etiqueta) {
        etiquetaRepository.actualizarEtiqueta(etiqueta)
    }

    suspend fun eliminarEtiqueta(etiqueta: Etiqueta) {
        etiquetaRepository.eliminarEtiqueta(etiqueta)
    }

}
