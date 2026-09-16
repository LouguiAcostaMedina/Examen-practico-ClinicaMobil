package pe.edu.upeu.clinicamobil.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class PacienteTest {
    @Test
    fun rechazaNombreVacio() {
        assertFailsWith<IllegalArgumentException> {
            Paciente(1L, "", "12345678", 30, 70.0)
        }
    }

    @Test
    fun rechazaEdadFueraDeRango() {
        assertFailsWith<IllegalArgumentException> {
            Paciente(1L, "Juan", "12345678", -1, 70.0)
        }
        assertFailsWith<IllegalArgumentException> {
            Paciente(1L, "Juan", "12345678", 121, 70.0)
        }
    }

    @Test
    fun esPediatricoVerdaderoCon17() {
        val paciente = Paciente(1L, "Juan", "12345678", 17, 70.0)
        assertTrue(paciente.esPediatrico)
    }

    @Test
    fun esPediatricoFalsoCon18() {
        val paciente = Paciente(1L, "Juan", "12345678", 18, 70.0)
        assertFalse(paciente.esPediatrico)
    }
}
