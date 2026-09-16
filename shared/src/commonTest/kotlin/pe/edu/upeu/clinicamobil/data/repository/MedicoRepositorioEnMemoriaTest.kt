package pe.edu.upeu.clinicamobil.data.repository

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.clinicamobil.domain.model.Medico
import kotlin.test.Test
import kotlin.test.assertEquals

class MedicoRepositorioEnMemoriaTest {
    @Test
    fun idsCorrelativosYListadoEnOrden() = runTest {
        val repo = MedicoRepositorioEnMemoria()
        val medico1 = Medico(0, "Dr. A", "11111", null)
        val medico2 = Medico(0, "Dr. B", "22222", "Pediatria")

        val guardado1 = repo.registrar(medico1)
        val guardado2 = repo.registrar(medico2)

        assertEquals(1L, guardado1.id)
        assertEquals(2L, guardado2.id)

        val listado = repo.listar()
        assertEquals(2, listado.size)
        assertEquals(1L, listado[0].id)
        assertEquals(2L, listado[1].id)
    }
}
