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
(Reemplazar con salida real de pruebas)
```
