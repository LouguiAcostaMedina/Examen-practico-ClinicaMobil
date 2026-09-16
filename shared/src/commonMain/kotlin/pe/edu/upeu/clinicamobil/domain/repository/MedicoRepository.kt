package pe.edu.upeu.clinicamobil.domain.repository

import pe.edu.upeu.clinicamobil.domain.model.Medico

/**
 * Contrato para la persistencia del cuerpo médico.
 */
interface MedicoRepository {
    /**
     * Registra un nuevo médico en el cuerpo médico.
     */
    suspend fun registrar(medico: Medico): Medico

    /**
     * Devuelve el cuerpo médico completo.
     */
    suspend fun listar(): List<Medico>
}
