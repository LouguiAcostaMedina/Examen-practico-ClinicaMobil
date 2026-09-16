package pe.edu.upeu.clinicamobil.domain.usecase

import kotlinx.coroutines.CancellationException

/**
 * Función de ayuda para ejecutar bloques de negocio, atrapar excepciones y
 * relanzar CancellationException para no romper la concurrencia estructurada.
 */
inline fun <T> resultadoDe(bloque: () -> T): Result<T> {
    return try {
        Result.success(bloque())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
