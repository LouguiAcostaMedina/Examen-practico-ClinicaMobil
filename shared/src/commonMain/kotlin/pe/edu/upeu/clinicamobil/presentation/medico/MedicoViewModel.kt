package pe.edu.upeu.clinicamobil.presentation.medico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upeu.clinicamobil.domain.usecase.ListarMedicosUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.MedicoInvalidoException
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarMedicoUseCase

class MedicoViewModel(
    private val registrarMedico: RegistrarMedicoUseCase,
    private val listarMedicos: ListarMedicosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MedicoUiState())
    val uiState: StateFlow<MedicoUiState> = _uiState.asStateFlow()

    init {
        cargarMedicos()
    }

    fun cargarMedicos() {
        _uiState.update { it.copy(fase = MedicoFase.Cargando) }
        viewModelScope.launch {
            val resultado = listarMedicos()
            resultado.fold(
                onSuccess = { medicos ->
                    if (medicos.isEmpty()) {
                        _uiState.update { it.copy(fase = MedicoFase.SinMedicos) }
                    } else {
                        _uiState.update { it.copy(fase = MedicoFase.ConMedicos(medicos.map { m -> m.aUi() })) }
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(fase = MedicoFase.Error("No se pudo cargar el cuerpo médico.")) }
                }
            )
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(nombre = nombre, errorNombre = null)) }
    }

    fun onColegiaturaChange(colegiatura: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(colegiatura = colegiatura, errorColegiatura = null)) }
    }

    fun onEspecialidadChange(especialidad: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(especialidad = especialidad, errorEspecialidad = null)) }
    }

    fun onMensajeExitoDismiss() {
        _uiState.update { it.copy(mensajeExito = null) }
    }

    fun registrar() {
        if (_uiState.value.registrando) return
        val formulario = _uiState.value.formulario
        _uiState.update { it.copy(registrando = true) }

        viewModelScope.launch {
            val resultado = registrarMedico(
                nombre = formulario.nombre,
                colegiatura = formulario.colegiatura,
                especialidad = formulario.especialidad
            )

            resultado.fold(
                onSuccess = { medico ->
                    _uiState.update {
                        it.copy(
                            registrando = false,
                            formulario = FormularioMedico(),
                            mensajeExito = "Médico \"${medico.nombre}\" registrado correctamente."
                        )
                    }
                    cargarMedicos()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(registrando = false) }
                    if (error is MedicoInvalidoException) {
                        _uiState.update {
                            it.copy(
                                formulario = it.formulario.copy(
                                    errorNombre = error.errores.errorNombre,
                                    errorColegiatura = error.errores.errorColegiatura,
                                    errorEspecialidad = error.errores.errorEspecialidad
                                )
                            )
                        }
                    } else {
                        _uiState.update { it.copy(fase = MedicoFase.Error("Error desconocido al registrar")) }
                    }
                }
            )
        }
    }
}
