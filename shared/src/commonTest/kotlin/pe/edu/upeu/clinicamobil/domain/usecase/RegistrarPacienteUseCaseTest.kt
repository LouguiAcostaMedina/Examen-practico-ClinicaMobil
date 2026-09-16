package pe.edu.upeu.clinicamobil.domain.usecase

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.clinicamobil.data.repository.FakePacienteRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegistrarPacienteUseCaseTest {

    @Test
    fun pacienteValidoEIdAsignado() = runTest {
        val repo = FakePacienteRepository()
        val useCase = RegistrarPacienteUseCase(repo)
        
        val resultado = useCase("Juan", "12345678", "30", "70.0")
        assertTrue(resultado.isSuccess)
        val paciente = resultado.getOrNull()
        assertTrue(paciente != null)
        assertEquals(1L, paciente.id)
    }

    @Test
    fun mensajesValidacionExactos() = runTest {
        val repo = FakePacienteRepository()
        val useCase = RegistrarPacienteUseCase(repo)
        
        val resultado = useCase(" ", "123", "abc", "-5")
        assertTrue(resultado.isFailure)
        val error = resultado.exceptionOrNull() as PacienteInvalidoException
        
        assertEquals("El nombre es obligatorio", error.errores.errorNombre)
        assertEquals("El DNI debe tener 8 dígitos", error.errores.errorDni)
        assertEquals("La edad debe ser un número entero", error.errores.errorEdad)
        assertEquals("El peso debe ser mayor a 0", error.errores.errorPeso)

        val resDniVacio = useCase("Juan", "", "30", "70.0")
        val errorDni = resDniVacio.exceptionOrNull() as PacienteInvalidoException
        assertEquals("El DNI es obligatorio", errorDni.errores.errorDni)
    }

    @Test
    fun falloDelRepositorioComoResultFailure() = runTest {
        val repo = FakePacienteRepository(shouldFail = true)
        val useCase = RegistrarPacienteUseCase(repo)
        
        val resultado = useCase("Juan", "12345678", "30", "70.0")
        assertTrue(resultado.isFailure)
        assertEquals("Error forzado en FakePacienteRepository", resultado.exceptionOrNull()?.message)
    }
}
