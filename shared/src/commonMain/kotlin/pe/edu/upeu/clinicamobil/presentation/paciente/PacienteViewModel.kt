package pe.edu.upeu.clinicamobil.presentation.paciente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.PacienteInvalidoException
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCase

class PacienteViewModel(
    private val registrarPaciente: RegistrarPacienteUseCase,
    private val listarPacientes: ListarPacientesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PacienteUiState())
    val uiState: StateFlow<PacienteUiState> = _uiState.asStateFlow()

    init {
        cargarPacientes()
    }

    fun cargarPacientes() {
        _uiState.update { it.copy(fase = PacienteFase.Cargando) }
        viewModelScope.launch {
            val resultado = listarPacientes()
            resultado.fold(
                onSuccess = { pacientes ->
                    if (pacientes.isEmpty()) {
                        _uiState.update { it.copy(fase = PacienteFase.SinPacientes) }
                    } else {
                        _uiState.update { it.copy(fase = PacienteFase.ConPacientes(pacientes.map { p -> p.aUi() })) }
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(fase = PacienteFase.Error("No se pudo cargar el padrón de pacientes.")) }
                }
            )
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(nombre = nombre, errorNombre = null)) }
    }

    fun onDniChange(dni: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(dni = dni, errorDni = null)) }
    }

    fun onEdadChange(edad: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(edad = edad, errorEdad = null)) }
    }

    fun onPesoChange(peso: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(peso = peso, errorPeso = null)) }
    }

    fun onMensajeExitoDismiss() {
        _uiState.update { it.copy(mensajeExito = null) }
    }

    fun registrar() {
        if (_uiState.value.registrando) return
        val formulario = _uiState.value.formulario
        _uiState.update { it.copy(registrando = true) }

        viewModelScope.launch {
            val resultado = registrarPaciente(
                nombre = formulario.nombre,
                dni = formulario.dni,
                edad = formulario.edad,
                peso = formulario.peso
            )

            resultado.fold(
                onSuccess = { paciente ->
                    _uiState.update {
                        it.copy(
                            registrando = false,
                            formulario = FormularioPaciente(),
                            mensajeExito = "Paciente \"${paciente.nombre}\" registrado correctamente."
                        )
                    }
                    cargarPacientes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(registrando = false) }
                    if (error is PacienteInvalidoException) {
                        _uiState.update {
                            it.copy(
                                formulario = it.formulario.copy(
                                    errorNombre = error.errores.errorNombre,
                                    errorDni = error.errores.errorDni,
                                    errorEdad = error.errores.errorEdad,
                                    errorPeso = error.errores.errorPeso
                                )
                            )
                        }
                    } else {
                        _uiState.update { it.copy(fase = PacienteFase.Error("Error desconocido al registrar")) }
                    }
                }
            )
        }
    }
}
