package pe.edu.upeu.clinicamobil.data.repository

import pe.edu.upeu.clinicamobil.domain.model.Paciente
import pe.edu.upeu.clinicamobil.domain.repository.PacienteRepository

class FakePacienteRepository(var shouldFail: Boolean = false) : PacienteRepository {
    private val pacientes = mutableListOf<Paciente>()
    private var siguienteId = 1L

    override suspend fun registrar(paciente: Paciente): Paciente {
        if (shouldFail) throw Exception("Error forzado en FakePacienteRepository")
        val nuevoPaciente = paciente.copy(id = siguienteId++)
        pacientes.add(nuevoPaciente)
        return nuevoPaciente
    }

    override suspend fun listar(): List<Paciente> {
        if (shouldFail) throw Exception("Error forzado en FakePacienteRepository")
        return pacientes.toList()
    }
}
