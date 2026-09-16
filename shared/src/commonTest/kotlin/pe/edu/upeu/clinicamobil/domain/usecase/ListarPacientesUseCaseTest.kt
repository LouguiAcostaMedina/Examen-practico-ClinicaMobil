package pe.edu.upeu.clinicamobil.domain.usecase

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.clinicamobil.data.repository.FakePacienteRepository
import pe.edu.upeu.clinicamobil.domain.model.Paciente
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListarPacientesUseCaseTest {
    @Test
    fun listaVacia() = runTest {
        val repo = FakePacienteRepository()
        val useCase = ListarPacientesUseCase(repo)
        val res = useCase()
        assertTrue(res.isSuccess)
        assertEquals(0, res.getOrNull()?.size)
    }

    @Test
    fun listaConElementos() = runTest {
        val repo = FakePacienteRepository()
        val useCase = ListarPacientesUseCase(repo)
        repo.registrar(Paciente(0, "Juan", "12345678", 30, 70.0))
        val res = useCase()
        assertTrue(res.isSuccess)
        assertEquals(1, res.getOrNull()?.size)
    }
}
