package davi.github.com.conectapovokt.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import davi.github.com.conectapovokt.ui.OcorrenciaViewModel
import davi.github.com.conectapovokt.ui.screens.CadastroScreen
import davi.github.com.conectapovokt.ui.screens.InicioScreen
import davi.github.com.conectapovokt.ui.screens.ListaScreen

object Rotas {
    const val INICIO = "inicio"
    const val LISTA = "lista"
    const val CADASTRO = "cadastro"
}

private data class ItemMenu(val rota: String, val rotulo: String, val icone: ImageVector)

private val itensMenu = listOf(
    ItemMenu(Rotas.INICIO, "Início", Icons.Filled.Home),
    ItemMenu(Rotas.LISTA, "Ocorrências", Icons.Filled.List),
    ItemMenu(Rotas.CADASTRO, "Registrar", Icons.Filled.Add)
)

@Composable
fun AppNavigation(viewModel: OcorrenciaViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val rotaAtual = backStack?.destination?.route

    val ocorrencias by viewModel.ocorrencias.collectAsState()
    val mensagem by viewModel.mensagem.collectAsState()
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(mensagem) {
        mensagem?.let {
            snackbarState.showSnackbar(it)
            viewModel.limparMensagem()
        }
    }

    fun irPara(rota: String) {
        navController.navigate(rota) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarState) },
        bottomBar = {
            NavigationBar {
                itensMenu.forEach { item ->
                    NavigationBarItem(
                        selected = rotaAtual == item.rota,
                        onClick = { irPara(item.rota) },
                        icon = { Icon(item.icone, contentDescription = item.rotulo) },
                        label = { Text(item.rotulo) }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Só a parte de baixo: o cabeçalho de cada tela cuida da barra de status.
        NavHost(
            navController = navController,
            startDestination = Rotas.INICIO,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Rotas.INICIO) {
                InicioScreen(
                    ocorrencias = ocorrencias,
                    onRegistrar = { irPara(Rotas.CADASTRO) },
                    onVerLista = { irPara(Rotas.LISTA) }
                )
            }
            composable(Rotas.LISTA) {
                ListaScreen(
                    ocorrencias = ocorrencias,
                    onAvancarStatus = viewModel::avancarStatus,
                    onExcluir = viewModel::excluir
                )
            }
            composable(Rotas.CADASTRO) {
                CadastroScreen(
                    onSalvar = { titulo, descricao, categoria, endereco ->
                        viewModel.salvar(titulo, descricao, categoria, endereco)
                        irPara(Rotas.LISTA)
                    }
                )
            }
        }
    }
}
