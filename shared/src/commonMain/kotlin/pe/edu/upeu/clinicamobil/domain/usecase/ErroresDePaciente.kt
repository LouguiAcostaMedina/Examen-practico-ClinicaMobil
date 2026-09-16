package pe.edu.upeu.clinicamobil.domain.usecase

data class ErroresDePaciente(
    val errorNombre: String? = null,
    val errorDni: String? = null,
    val errorEdad: String? = null,
    val errorPeso: String? = null
)

class PacienteInvalidoException(val errores: ErroresDePaciente) : Exception("Error de validación del paciente")
