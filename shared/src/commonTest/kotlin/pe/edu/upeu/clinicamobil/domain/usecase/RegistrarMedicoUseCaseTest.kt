package pe.edu.upeu.clinicamobil.domain.usecase

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.clinicamobil.data.repository.FakeMedicoRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegistrarMedicoUseCaseTest {

    @Test
    fun validacionDeColegiaturaYEspecialidadYGuardadoNull() = runTest {
        val repo = FakeMedicoRepository()
        val useCase = RegistrarMedicoUseCase(repo)

        // Colegiatura de 4 dígitos y especialidad de 2 caracteres
        val resultado = useCase("Dr. House", "1234", "Ab")
        assertTrue(resultado.isFailure)
        val error = resultado.exceptionOrNull() as MedicoInvalidoException
        assertEquals("La colegiatura debe tener entre 5 y 6 dígitos", error.errores.errorColegiatura)
        assertEquals("La especialidad debe tener al menos 3 caracteres", error.errores.errorEspecialidad)

        // Blanco guardado como null
        val resultadoValido = useCase("Dr. House", "12345", "   ")
        assertTrue(resultadoValido.isSuccess)
        val medico = resultadoValido.getOrNull()!!
        assertEquals(null, medico.especialidad)
    }
}
