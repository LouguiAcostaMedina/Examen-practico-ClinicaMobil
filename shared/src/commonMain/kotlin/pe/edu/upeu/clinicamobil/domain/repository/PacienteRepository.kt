package pe.edu.upeu.clinicamobil.domain.repository

import pe.edu.upeu.clinicamobil.domain.model.Paciente

/**
 * Contrato para la persistencia de pacientes en el padrón.
 */
interface PacienteRepository {
    /**
     * Registra un nuevo paciente en el padrón.
     */
    suspend fun registrar(paciente: Paciente): Paciente

    /**
     * Devuelve el padrón completo de pacientes.
     */
    suspend fun listar(): List<Paciente>
}
