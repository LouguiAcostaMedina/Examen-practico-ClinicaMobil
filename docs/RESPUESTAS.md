# Respuestas al Examen Práctico - ClinicaMobil

**1. Cambios al consumir una API real en lugar de objetos en memoria**
Al centralizar datos, cambian o se agregan implementaciones en la capa `data` —como repositorios remotos, clientes HTTP (Ktor), DTOs y mapeadores— y se cambia el enlace en `di/AppModule.kt`. La capa de `domain` (entidades, casos de uso) y `presentation` permanecen intactas porque dependen exclusivamente de las abstracciones definidas en `domain/repository`.

**2. Ubicación de la regla de DNI (8 dígitos) y sus beneficios**
La regla del DNI de 8 dígitos vive en `RegistrarPacienteUseCase`. No se repite en la pantalla ni en el ViewModel para mantener una sola fuente de verdad auditable y pura. Si la entidad `Paciente` relajara esa regla (por ejemplo, para permitir DNIs extranjeros más adelante), el caso de uso seguiría rechazando DNIs inválidos locales mientras conserve la validación específica del negocio.

**3. Impacto de registrar repositorios como factory en lugar de single en Koin**
Si `PacienteRepositorioEnMemoria` fuera inyectado como `factory`, Koin podría entregar instancias diferentes a `RegistrarPacienteUseCase` y `ListarPacientesUseCase`. El usuario vería un registro exitoso (en una instancia de memoria), pero al recargar la pantalla la lista aparecería vacía (consultando otra instancia), y los IDs podrían reiniciarse causando colisiones. Como `single`, todos los casos de uso comparten y modifican la misma lista.

---

**Resultado de Pruebas**
```
> Task :shared:testAndroidHostTest
pe.edu.upeu.clinicamobil.domain.model.PacienteTest > rechazaNombreVacio PASSED
pe.edu.upeu.clinicamobil.domain.model.PacienteTest > rechazaEdadFueraDeRango PASSED
pe.edu.upeu.clinicamobil.domain.model.PacienteTest > esPediatricoVerdaderoCon17 PASSED
pe.edu.upeu.clinicamobil.domain.model.PacienteTest > esPediatricoFalsoCon18 PASSED
pe.edu.upeu.clinicamobil.domain.model.AtencionTest > rechazaCeroMinutos PASSED
pe.edu.upeu.clinicamobil.domain.model.AtencionTest > rechaza121Minutos PASSED
pe.edu.upeu.clinicamobil.domain.model.AtencionTest > costoDe30MinutosEs75 PASSED
pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCaseTest > pacienteValidoEIdAsignado PASSED
pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCaseTest > mensajesValidacionExactos PASSED
pe.edu.upeu.clinicamobil.domain.usecase.RegistrarPacienteUseCaseTest > falloDelRepositorioComoResultFailure PASSED
pe.edu.upeu.clinicamobil.domain.usecase.RegistrarMedicoUseCaseTest > validacionDeColegiaturaYEspecialidadYGuardadoNull PASSED
pe.edu.upeu.clinicamobil.data.repository.PacienteRepositorioEnMemoriaTest > idsCorrelativosYListadoEnOrden PASSED
pe.edu.upeu.clinicamobil.data.repository.MedicoRepositorioEnMemoriaTest > idsCorrelativosYListadoEnOrden PASSED
pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModelTest > arrancaEnSinPacientes PASSED
pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModelTest > muestraFormatoEdadYPeso PASSED
pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModelTest > pasaAError PASSED
pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModelTest > erroresDeValidacionEnFormulario PASSED
pe.edu.upeu.clinicamobil.presentation.paciente.PacienteViewModelTest > registrarLimpiaYRecarga PASSED
pe.edu.upeu.clinicamobil.di.AppModuleTest > resuelveImplementacionCorrectaYRepositorioEsUnico PASSED
pe.edu.upeu.clinicamobil.di.AppModuleTest > resuelveCasosDeUso PASSED
pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCaseTest > listaVacia PASSED
pe.edu.upeu.clinicamobil.domain.usecase.ListarPacientesUseCaseTest > listaConElementos PASSED

BUILD SUCCESSFUL in 15s
30 actionable tasks: 1 executed, 29 up-to-date
22 tests completed, 0 failed
```
