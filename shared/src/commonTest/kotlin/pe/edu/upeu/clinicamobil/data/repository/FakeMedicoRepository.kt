package pe.edu.upeu.clinicamobil.data.repository

import pe.edu.upeu.clinicamobil.domain.model.Medico
import pe.edu.upeu.clinicamobil.domain.repository.MedicoRepository

class FakeMedicoRepository(var shouldFail: Boolean = false) : MedicoRepository {
    private val medicos = mutableListOf<Medico>()
    private var siguienteId = 1L

    override suspend fun registrar(medico: Medico): Medico {
        if (shouldFail) throw Exception("Error forzado en FakeMedicoRepository")
        val nuevoMedico = medico.copy(id = siguienteId++)
        medicos.add(nuevoMedico)
        return nuevoMedico
    }

    override suspend fun listar(): List<Medico> {
        if (shouldFail) throw Exception("Error forzado en FakeMedicoRepository")
        return medicos.toList()
    }
}
