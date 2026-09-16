package pe.edu.upeu.clinicamobil.domain.model

data class Paciente(
    val id: Long,
    val nombre: String,
    val dni: String,
    val edad: Int,
    val peso: Double
) {
    init {
        require(nombre.isNotBlank()) { "El nombre es obligatorio" }
        require(dni.isNotBlank()) { "El DNI es obligatorio" }
        require(edad in 0..EDAD_MAXIMA) { "La edad debe estar entre 0 y $EDAD_MAXIMA" }
        require(peso > 0) { "El peso debe ser mayor a 0" }
    }

    val esPediatrico: Boolean get() = edad < EDAD_ADULTO

    companion object {
        const val EDAD_ADULTO = 18
        const val EDAD_MAXIMA = 120
    }
}
