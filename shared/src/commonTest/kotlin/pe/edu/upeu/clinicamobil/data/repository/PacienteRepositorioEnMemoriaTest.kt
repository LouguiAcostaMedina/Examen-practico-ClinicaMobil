package pe.edu.upeu.clinicamobil.data.repository

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.clinicamobil.domain.model.Paciente
import kotlin.test.Test
import kotlin.test.assertEquals

class PacienteRepositorioEnMemoriaTest {

    @Test
    fun idsCorrelativosYListadoEnOrden() = runTest {
        val repo = PacienteRepositorioEnMemoria()
        val paciente1 = Paciente(0, "Juan", "12345678", 30, 70.0)
        val paciente2 = Paciente(0, "Maria", "87654321", 25, 60.0)

        val guardado1 = repo.registrar(paciente1)
        val guardado2 = repo.registrar(paciente2)

        assertEquals(1L, guardado1.id)
        assertEquals(2L, guardado2.id)

        val listado = repo.listar()
        assertEquals(2, listado.size)
        assertEquals(1L, listado[0].id)
        assertEquals(2L, listado[1].id)
    }
}
