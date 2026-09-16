package pe.edu.upeu.clinicamobil.domain.usecase

data class ErroresDeMedico(
    val errorNombre: String? = null,
    val errorColegiatura: String? = null,
    val errorEspecialidad: String? = null
)

class MedicoInvalidoException(val errores: ErroresDeMedico) : Exception("Error de validación del médico")
