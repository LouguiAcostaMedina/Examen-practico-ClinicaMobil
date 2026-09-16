package pe.edu.upeu.clinicamobil.domain.usecase

import pe.edu.upeu.clinicamobil.domain.model.Paciente
import pe.edu.upeu.clinicamobil.domain.repository.PacienteRepository

class RegistrarPacienteUseCase(
    private val repository: PacienteRepository
) {
    suspend operator fun invoke(
        nombre: String,
        dni: String,
        edad: String,
        peso: String
    ): Result<Paciente> = resultadoDe {
        val errores = ErroresDePaciente(
            errorNombre = validarNombre(nombre),
            errorDni = validarDni(dni),
            errorEdad = validarEdad(edad),
            errorPeso = validarPeso(peso)
        )

        if (errores.errorNombre != null || errores.errorDni != null || 
            errores.errorEdad != null || errores.errorPeso != null) {
            throw PacienteInvalidoException(errores)
        }

        val pacienteValidado = Paciente(
            id = 0L,
            nombre = nombre.trim(),
            dni = dni.trim(),
            edad = edad.trim().toInt(),
            peso = peso.trim().toDouble()
        )

        // Se envía al repositorio para guardar
        // Para que se evalúe dentro de la misma co-rutina y Result
        val guardado = repository.registrar(pacienteValidado)
        guardado
    }

    private fun validarNombre(nombre: String): String? {
        if (nombre.trim().isEmpty()) return "El nombre es obligatorio"
        return null
    }

    private fun validarDni(dni: String): String? {
        val dniTrim = dni.trim()
        if (dniTrim.isEmpty()) return "El DNI es obligatorio"
        if (!dniTrim.matches(Regex("^[0-9]{8}$"))) return "El DNI debe tener 8 dígitos"
        return null
    }

    private fun validarEdad(edadStr: String): String? {
        val edadTrim = edadStr.trim()
        if (edadTrim.isEmpty()) return "La edad es obligatoria"
        val edad = edadTrim.toIntOrNull() ?: return "La edad debe ser un número entero"
        if (edad !in 0..120) return "La edad debe estar entre 0 y 120"
        return null
    }

    private fun validarPeso(pesoStr: String): String? {
        val pesoTrim = pesoStr.trim()
        if (pesoTrim.isEmpty()) return "El peso es obligatorio"
        val peso = pesoTrim.toDoubleOrNull()
        if (peso == null || peso.isNaN() || peso.isInfinite()) return "El peso debe ser un número válido"
        if (peso <= 0) return "El peso debe ser mayor a 0"
        return null
    }
}
