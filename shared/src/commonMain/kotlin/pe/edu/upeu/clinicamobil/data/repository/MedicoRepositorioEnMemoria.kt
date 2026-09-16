package pe.edu.upeu.clinicamobil.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pe.edu.upeu.clinicamobil.domain.model.Medico
import pe.edu.upeu.clinicamobil.domain.repository.MedicoRepository
import kotlin.random.Random

class MedicoRepositorioEnMemoria : MedicoRepository {
    private val medicos = mutableListOf<Medico>()
    private var siguienteId = 1L
    private val mutex = Mutex()

    override suspend fun registrar(medico: Medico): Medico {
        simularLatencia()
        return mutex.withLock {
            val nuevoMedico = medico.copy(id = siguienteId++)
            medicos.add(nuevoMedico)
            nuevoMedico
        }
    }

    override suspend fun listar(): List<Medico> {
        simularLatencia()
        return mutex.withLock {
            medicos.toList() // Se devuelve una copia
        }
    }

    private suspend fun simularLatencia() {
        delay(Random.nextLong(300, 800))
    }
}
