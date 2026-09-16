package pe.edu.upeu.clinicamobil.presentation.paciente

import pe.edu.upeu.clinicamobil.domain.model.Paciente

data class FormularioPaciente(
    val nombre: String = "",
    val dni: String = "",
    val edad: String = "",
    val peso: String = "",
    val errorNombre: String? = null,
    val errorDni: String? = null,
    val errorEdad: String? = null,
    val errorPeso: String? = null
)

sealed interface PacienteFase {
    data object Cargando : PacienteFase
    data object SinPacientes : PacienteFase
    data class ConPacientes(val pacientes: List<PacienteUi>) : PacienteFase
    data class Error(val mensaje: String) : PacienteFase
}

data class PacienteUiState(
    val fase: PacienteFase = PacienteFase.Cargando,
    val formulario: FormularioPaciente = FormularioPaciente(),
    val registrando: Boolean = false,
    val mensajeExito: String? = null
)

data class PacienteUi(
    val id: Long,
    val nombre: String,
    val dni: String,
    val edadPesoSubtitulo: String,
    val esPediatrico: Boolean
)

// Helper en Kotlin común para un solo decimal sin usar String.format
fun formatDecimal(value: Double): String {
    val integerPart = value.toLong()
    val fractionalPart = ((value - integerPart) * 10).toLong()
    return "$integerPart.$fractionalPart"
}

fun Paciente.aUi(): PacienteUi {
    val edadTexto = if (this.edad == 1) "1 año" else "${this.edad} años"
    val pesoTexto = "${formatDecimal(this.peso)} kg"
    
    return PacienteUi(
        id = this.id,
        nombre = this.nombre,
        dni = this.dni,
        edadPesoSubtitulo = "$edadTexto · $pesoTexto",
        esPediatrico = this.esPediatrico
    )
}
