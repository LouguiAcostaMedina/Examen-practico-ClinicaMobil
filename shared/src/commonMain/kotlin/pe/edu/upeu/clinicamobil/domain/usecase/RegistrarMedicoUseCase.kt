package pe.edu.upeu.clinicamobil.domain.usecase

import pe.edu.upeu.clinicamobil.domain.model.Medico
import pe.edu.upeu.clinicamobil.domain.repository.MedicoRepository

class RegistrarMedicoUseCase(
    private val repository: MedicoRepository
) {
    suspend operator fun invoke(
        nombre: String,
        colegiatura: String,
        especialidad: String
    ): Result<Medico> = resultadoDe {
        val errores = ErroresDeMedico(
            errorNombre = validarNombre(nombre),
            errorColegiatura = validarColegiatura(colegiatura),
            errorEspecialidad = validarEspecialidad(especialidad)
        )

        if (errores.errorNombre != null || errores.errorColegiatura != null || errores.errorEspecialidad != null) {
            throw MedicoInvalidoException(errores)
        }

        val especialidadFinal = especialidad.trim().ifEmpty { null }

        val medicoValidado = Medico(
            id = 0L,
            nombre = nombre.trim(),
            colegiatura = colegiatura.trim(),
            especialidad = especialidadFinal
        )

        repository.registrar(medicoValidado)
    }

    private fun validarNombre(nombre: String): String? {
        if (nombre.trim().isEmpty()) return "El nombre es obligatorio"
        return null
    }

    private fun validarColegiatura(colegiatura: String): String? {
        val colegiaturaTrim = colegiatura.trim()
        if (colegiaturaTrim.isEmpty()) return "La colegiatura es obligatoria"
        if (!colegiaturaTrim.matches(Regex("^[0-9]{5,6}$"))) return "La colegiatura debe tener entre 5 y 6 dígitos"
        return null
    }

    private fun validarEspecialidad(especialidad: String): String? {
        val especialidadTrim = especialidad.trim()
        if (especialidadTrim.isNotEmpty() && especialidadTrim.length < 3) {
            return "La especialidad debe tener al menos 3 caracteres"
        }
        return null
    }
}
