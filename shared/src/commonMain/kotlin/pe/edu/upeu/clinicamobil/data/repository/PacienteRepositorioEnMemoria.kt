package pe.edu.upeu.clinicamobil.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pe.edu.upeu.clinicamobil.domain.model.Paciente
import pe.edu.upeu.clinicamobil.domain.repository.PacienteRepository
import kotlin.random.Random

class PacienteRepositorioEnMemoria : PacienteRepository {
    private val pacientes = mutableListOf<Paciente>()
    private var siguienteId = 1L
    private val mutex = Mutex()

    override suspend fun registrar(paciente: Paciente): Paciente {
        simularLatencia()
        return mutex.withLock {
            val nuevoPaciente = paciente.copy(id = siguienteId++)
            pacientes.add(nuevoPaciente)
            nuevoPaciente
        }
    }

    override suspend fun listar(): List<Paciente> {
        simularLatencia()
        return mutex.withLock {
            pacientes.toList() // Se devuelve una copia para preservar inmutabilidad
        }
    }

    private suspend fun simularLatencia() {
        delay(Random.nextLong(300, 800))
    }
}
