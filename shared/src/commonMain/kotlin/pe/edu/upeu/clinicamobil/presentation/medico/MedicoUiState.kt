package pe.edu.upeu.clinicamobil.presentation.medico

import pe.edu.upeu.clinicamobil.domain.model.Medico

data class FormularioMedico(
    val nombre: String = "",
    val colegiatura: String = "",
    val especialidad: String = "",
    val errorNombre: String? = null,
    val errorColegiatura: String? = null,
    val errorEspecialidad: String? = null
)

sealed interface MedicoFase {
    data object Cargando : MedicoFase
    data object SinMedicos : MedicoFase
    data class ConMedicos(val medicos: List<MedicoUi>) : MedicoFase
    data class Error(val mensaje: String) : MedicoFase
}

data class MedicoUiState(
    val fase: MedicoFase = MedicoFase.Cargando,
    val formulario: FormularioMedico = FormularioMedico(),
    val registrando: Boolean = false,
    val mensajeExito: String? = null
)

data class MedicoUi(
    val id: Long,
    val nombre: String,
    val subtitulo: String
)

fun Medico.aUi(): MedicoUi {
    val especialidadTexto = if (this.especialidad.isNullOrBlank()) "Medicina general" else this.especialidad
    return MedicoUi(
        id = this.id,
        nombre = this.nombre,
        subtitulo = "CMP ${this.colegiatura} · $especialidadTexto"
    )
}
