package pe.edu.upeu.clinicamobil.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import pe.edu.upeu.clinicamobil.data.repository.MedicoRepositorioEnMemoria
import pe.edu.upeu.clinicamobil.data.repository.PacienteRepositorioEnMemoria
import pe.edu.upeu.clinicamobil.domain.repository.MedicoRepository
import pe.edu.upeu.clinicamobil.domain.repository.PacienteRepository
import pe.edu.upeu.clinicamobil.domain.usecase.ListarMedicosUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarMedicoUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AppModuleTest : KoinTest {

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                dataModule,
                domainModule,
                presentationModule,
                module { /* Empty platformModule for test */ }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun resuelveImplementacionCorrectaYRepositorioEsUnico() {
        val repo1 = get<PacienteRepository>()
        val repo2 = get<PacienteRepository>()
        
        assertTrue(repo1 is PacienteRepositorioEnMemoria)
        assertTrue(repo1 === repo2) // Mismo objeto = Single
        
        val medicoRepo1 = get<MedicoRepository>()
        val medicoRepo2 = get<MedicoRepository>()
        
        assertTrue(medicoRepo1 is MedicoRepositorioEnMemoria)
        assertTrue(medicoRepo1 === medicoRepo2)
    }

    @Test
    fun resuelveCasosDeUso() {
        val registrarP = get<RegistrarPacienteUseCase>()
        val listarP = get<ListarPacientesUseCase>()
        val registrarM = get<RegistrarMedicoUseCase>()
        val listarM = get<ListarMedicosUseCase>()
        
        assertTrue(registrarP != null)
        assertTrue(listarP != null)
        assertTrue(registrarM != null)
        assertTrue(listarM != null)
    }
}
