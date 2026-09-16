package pe.edu.upeu.clinicamobil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.upeu.clinicamobil.presentation.components.EstadoVacio
import pe.edu.upeu.clinicamobil.presentation.medico.MedicoScreen
import pe.edu.upeu.clinicamobil.presentation.paciente.PacienteScreen
import pe.edu.upeu.clinicamobil.presentation.theme.ClinicaMobilTheme

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Inicio : Screen("inicio", "Inicio", Icons.Default.Home)
    data object Pacientes : Screen("pacientes", "Pacientes", Icons.Default.Person)
    data object Medicos : Screen("medicos", "Médicos", Icons.Default.LocalHospital)
    data object Historias : Screen("historias", "Historias Clínicas", Icons.Default.Assignment)
}

val DESTINOS = listOf(
    Screen.Inicio,
    Screen.Pacientes,
    Screen.Medicos,
    Screen.Historias
)

val screenSaver = Saver<Screen, String>(
    save = { it.route },
    restore = { route -> DESTINOS.find { it.route == route } ?: Screen.Inicio }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    KoinContext {
        var isDarkMode by rememberSaveable { mutableStateOf(false) }

        ClinicaMobilTheme(darkTheme = isDarkMode) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var pantallaActual by rememberSaveable(stateSaver = screenSaver) { mutableStateOf(Screen.Inicio) }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalHospital,
                                contentDescription = "Logo",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "ClinicaMobil",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Divider()
                        DESTINOS.forEach { destino ->
                            NavigationDrawerItem(
                                icon = { Icon(destino.icon, contentDescription = null) },
                                label = { Text(destino.title) },
                                selected = destino == pantallaActual,
                                onClick = {
                                    pantallaActual = destino
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Divider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Modo Oscuro")
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { isDarkMode = it }
                            )
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(pantallaActual.title) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when (pantallaActual) {
                            is Screen.Inicio -> InicioScreen(onNavegar = { pantallaActual = it })
                            is Screen.Pacientes -> PacienteScreen(viewModel = koinViewModel())
                            is Screen.Medicos -> MedicoScreen(viewModel = koinViewModel())
                            is Screen.Historias -> HistoriasScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InicioScreen(onNavegar: (Screen) -> Unit) {
    val accesosRapidos = listOf(
        Screen.Pacientes,
        Screen.Medicos,
        Screen.Historias
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Icon(
            imageVector = Icons.Default.LocalHospital,
            contentDescription = "ClinicaMobil",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "ClinicaMobil",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Salud al alcance de tu mano",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "¿Qué puedes hacer?",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(accesosRapidos) { acceso ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onNavegar(acceso) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(acceso.icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = when(acceso) {
                                Screen.Pacientes -> "Registrar pacientes"
                                Screen.Medicos -> "Registrar médicos"
                                Screen.Historias -> "Revisar historias clínicas"
                                else -> acceso.title
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoriasScreen() {
    EstadoVacio(
        icono = Icons.Default.Assignment,
        titulo = "Historias clínicas en construcción",
        descripcion = "Este módulo se encuentra en desarrollo y estará disponible en una próxima versión."
    )
}
