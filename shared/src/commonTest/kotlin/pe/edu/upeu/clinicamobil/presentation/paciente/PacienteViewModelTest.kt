package pe.edu.upeu.clinicamobil.presentation.paciente

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pe.edu.upeu.clinicamobil.data.repository.FakePacienteRepository
import pe.edu.upeu.clinicamobil.domain.model.Paciente
import pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PacienteViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun arrancaEnSinPacientes() = runTest {
        val repo = FakePacienteRepository()
        val registrar = RegistrarPacienteUseCase(repo)
        val listar = ListarPacientesUseCase(repo)
        val viewModel = PacienteViewModel(registrar, listar)

        assertTrue(viewModel.uiState.value.fase is PacienteFase.SinPacientes)
    }

    @Test
    fun muestraFormatoEdadYPeso() = runTest {
        val repo = FakePacienteRepository()
        repo.registrar(Paciente(1L, "Juan", "12345678", 34, 70.5))
        val registrar = RegistrarPacienteUseCase(repo)
        val listar = ListarPacientesUseCase(repo)
        val viewModel = PacienteViewModel(registrar, listar)

        val fase = viewModel.uiState.value.fase
        assertTrue(fase is PacienteFase.ConPacientes)
        assertEquals("34 años · 70.5 kg", fase.pacientes[0].edadPesoSubtitulo)
    }

    @Test
    fun pasaAError() = runTest {
        val repo = FakePacienteRepository(shouldFail = true)
        val registrar = RegistrarPacienteUseCase(repo)
        val listar = ListarPacientesUseCase(repo)
        val viewModel = PacienteViewModel(registrar, listar)

        assertTrue(viewModel.uiState.value.fase is PacienteFase.Error)
    }

    @Test
    fun erroresDeValidacionEnFormulario() = runTest {
        val repo = FakePacienteRepository()
        val registrar = RegistrarPacienteUseCase(repo)
        val listar = ListarPacientesUseCase(repo)
        val viewModel = PacienteViewModel(registrar, listar)

        viewModel.onNombreChange("")
        viewModel.registrar()

        assertEquals("El nombre es obligatorio", viewModel.uiState.value.formulario.errorNombre)
    }

    @Test
    fun registrarLimpiaYRecarga() = runTest {
        val repo = FakePacienteRepository()
        val registrar = RegistrarPacienteUseCase(repo)
        val listar = ListarPacientesUseCase(repo)
        val viewModel = PacienteViewModel(registrar, listar)

        viewModel.onNombreChange("Juan")
        viewModel.onDniChange("12345678")
        viewModel.onEdadChange("30")
        viewModel.onPesoChange("70.0")
        viewModel.registrar()

        assertEquals("", viewModel.uiState.value.formulario.nombre)
        assertTrue(viewModel.uiState.value.fase is PacienteFase.ConPacientes)
        assertEquals("Paciente \"Juan\" registrado correctamente.", viewModel.uiState.value.mensajeExito)
    }
}
