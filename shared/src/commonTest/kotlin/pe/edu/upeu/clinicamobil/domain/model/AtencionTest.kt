package pe.edu.upeu.clinicamobil.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AtencionTest {
    private val medico = Medico(1L, "Dr. House", "12345", null)

    @Test
    fun rechazaCeroMinutos() {
        assertFailsWith<IllegalArgumentException> {
            Atencion(medico, "Dolor", 0)
        }
    }

    @Test
    fun rechaza121Minutos() {
        assertFailsWith<IllegalArgumentException> {
            Atencion(medico, "Dolor", 121)
        }
    }

    @Test
    fun costoDe30MinutosEs75() {
        val atencion = Atencion(medico, "Dolor", 30)
        assertEquals(75.0, atencion.costo())
    }
}
