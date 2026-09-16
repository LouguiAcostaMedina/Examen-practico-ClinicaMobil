package pe.edu.upeu.clinicamobil.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module
import pe.edu.upeu.clinicamobil.data.repository.MedicoRepositorioEnMemoria
import pe.edu.upeu.clinicamobil.data.repository.PacienteRepositorioEnMemoria
import pe.edu.upeu.clinicamobil.domain.repository.MedicoRepository
import pe.edu.upeu.clinicamobil.domain.repository.PacienteRepository
import pe.edu.upeu.clinicamobil.domain.usecase.ListarMedicosUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarMedicoUseCase
import pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCase
import pe.edu.upeu.clinicamobil.presentation.medico.MedicoViewModel
import pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModel

val dataModule = module {
    singleOf(::PacienteRepositorioEnMemoria) { bind<PacienteRepository>() }
    singleOf(::MedicoRepositorioEnMemoria) { bind<MedicoRepository>() }
}

val domainModule = module {
    factoryOf(::RegistrarPacienteUseCase)
    factoryOf(::ListarPacientesUseCase)
    factoryOf(::RegistrarMedicoUseCase)
    factoryOf(::ListarMedicosUseCase)
}

val presentationModule = module {
    factoryOf(::PacienteViewModel)
    factoryOf(::MedicoViewModel)
}

expect val platformModule: Module

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presentationModule,
            platformModule
        )
    }
}
