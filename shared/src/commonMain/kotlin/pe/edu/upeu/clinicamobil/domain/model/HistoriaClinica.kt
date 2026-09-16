package pe.edu.upeu.clinicamobil.domain.model

sealed interface EstadoHistoria {
    data object Abierta : EstadoHistoria
    data object EnTratamiento : EstadoHistoria
    data object Alta : EstadoHistoria
    data class Derivada(val especialidad: String) : EstadoHistoria
}

data class HistoriaClinica(
    val id: Long,
    val paciente: Paciente,
    val atenciones: List<Atencion>,
    val estado: EstadoHistoria
)
