package pe.edu.upeu.clinicamobil.presentation.paciente

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
fun PacienteScreen(
    viewModel: PacienteViewModel,
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
                Text("Registrar Paciente", style = MaterialTheme.typography.titleMedium)
                
                ValidatedTextField(
                    value = uiState.formulario.nombre,
                    onValueChange = { viewModel.onNombreChange(it) },
                    label = "Nombre",
                    error = uiState.formulario.errorNombre,
                    enabled = !uiState.registrando
                )
                
                ValidatedTextField(
                    value = uiState.formulario.dni,
                    onValueChange = { viewModel.onDniChange(it) },
                    label = "DNI",
                    error = uiState.formulario.errorDni,
                    keyboardType = KeyboardType.Number,
                    enabled = !uiState.registrando
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ValidatedTextField(
                        value = uiState.formulario.edad,
                        onValueChange = { viewModel.onEdadChange(it) },
                        label = "Edad",
                        error = uiState.formulario.errorEdad,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.registrando
                    )
                    
                    ValidatedTextField(
                        value = uiState.formulario.peso,
                        onValueChange = { viewModel.onPesoChange(it) },
                        label = "Peso (kg)",
                        error = uiState.formulario.errorPeso,
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.registrando
                    )
                }

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
                is PacienteFase.Cargando -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cargando pacientes...")
                    }
                }
                is PacienteFase.SinPacientes -> {
                    EstadoVacio(
                        icono = Icons.Default.Person,
                        titulo = "Sin pacientes",
                        descripcion = "Aún no hay pacientes registrados en el padrón."
                    )
                }
                is PacienteFase.Error -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        EstadoVacio(
                            icono = Icons.Default.Warning,
                            titulo = "Error",
                            descripcion = fase.mensaje,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = { viewModel.cargarPacientes() },
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                is PacienteFase.ConPacientes -> {
                    Column {
                        val conteo = if (fase.pacientes.size == 1) "1 paciente" else "${fase.pacientes.size} pacientes"
                        Text(
                            text = conteo,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(fase.pacientes) { pacienteUi ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = pacienteUi.nombre,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(
                                                text = "DNI: ${pacienteUi.dni}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = pacienteUi.edadPesoSubtitulo,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        if (pacienteUi.esPediatrico) {
                                            Badge {
                                                Text("Pediátrico", modifier = Modifier.padding(horizontal = 4.dp))
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
    }
}
