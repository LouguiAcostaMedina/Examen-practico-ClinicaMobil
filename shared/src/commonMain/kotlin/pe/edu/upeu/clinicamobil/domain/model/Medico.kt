package pe.edu.upeu.clinicamobil.domain.model

data class Medico(
    val id: Long,
    val nombre: String,
    val colegiatura: String,
    val especialidad: String?
) {
    init {
        require(nombre.isNotBlank()) { "El nombre es obligatorio" }
        require(colegiatura.isNotBlank()) { "La colegiatura es obligatoria" }
        if (especialidad != null) {
            require(especialidad.isNotBlank()) { "La especialidad debe tener al menos 3 caracteres" }
        }
    }
}
