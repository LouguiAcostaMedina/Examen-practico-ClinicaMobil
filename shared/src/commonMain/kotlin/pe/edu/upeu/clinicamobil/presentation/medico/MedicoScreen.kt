package pe.edu.upeu.clinicamobil.presentation.medico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pe.edu.upeu.clinicamobil.presentation.components.EstadoVacio
import pe.edu.upeu.clinicamobil.presentation.components.MensajeExito
import pe.edu.upeu.clinicamobil.presentation.components.ValidatedTextField

@Composable
fun MedicoScreen(
    viewModel: MedicoViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Formulario
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Registrar Médico", style = MaterialTheme.typography.titleMedium)
                
                ValidatedTextField(
                    value = uiState.formulario.nombre,
                    onValueChange = { viewModel.onNombreChange(it) },
                    label = "Nombre",
                    error = uiState.formulario.errorNombre,
                    enabled = !uiState.registrando
                )
                
                ValidatedTextField(
                    value = uiState.formulario.colegiatura,
                    onValueChange = { viewModel.onColegiaturaChange(it) },
                    label = "Colegiatura (CMP)",
                    error = uiState.formulario.errorColegiatura,
                    keyboardType = KeyboardType.Number,
                    enabled = !uiState.registrando
                )
                
                ValidatedTextField(
                    value = uiState.formulario.especialidad,
                    onValueChange = { viewModel.onEspecialidadChange(it) },
                    label = "Especialidad (Opcional)",
                    error = uiState.formulario.errorEspecialidad,
                    enabled = !uiState.registrando
                )

                Button(
                    onClick = { viewModel.registrar() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.registrando
                ) {
                    Text(if (uiState.registrando) "Registrando…" else "Registrar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.mensajeExito != null) {
            MensajeExito(
                mensaje = uiState.mensajeExito!!,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Fases
        Box(modifier = Modifier.fillMaxSize()) {
            when (val fase = uiState.fase) {
                is MedicoFase.Cargando -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cargando médicos...")
                    }
                }
                is MedicoFase.SinMedicos -> {
                    EstadoVacio(
                        icono = Icons.Default.Person,
                        titulo = "Sin médicos",
                        descripcion = "Aún no hay médicos registrados en el padrón."
                    )
                }
                is MedicoFase.Error -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        EstadoVacio(
                            icono = Icons.Default.Warning,
                            titulo = "Error",
                            descripcion = fase.mensaje,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = { viewModel.cargarMedicos() },
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                is MedicoFase.ConMedicos -> {
                    Column {
                        val conteo = if (fase.medicos.size == 1) "1 médico" else "${fase.medicos.size} médicos"
                        Text(
                            text = conteo,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(fase.medicos) { medicoUi ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = medicoUi.nombre,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = medicoUi.subtitulo,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
