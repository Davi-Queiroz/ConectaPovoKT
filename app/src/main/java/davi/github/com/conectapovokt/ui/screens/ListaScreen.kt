package davi.github.com.conectapovokt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import davi.github.com.conectapovokt.model.Ocorrencia
import davi.github.com.conectapovokt.model.Status

@Composable
fun ListaScreen(
    ocorrencias: List<Ocorrencia>,
    onAvancarStatus: (Int) -> Unit,
    onExcluir: (Int) -> Unit
) {
    var filtro by rememberSaveable { mutableStateOf<Status?>(null) }
    var paraExcluir by remember { mutableStateOf<Ocorrencia?>(null) }

    val visiveis = if (filtro == null) ocorrencias else ocorrencias.filter { it.status == filtro }

    Column(Modifier.fillMaxSize()) {
        CabecalhoTela(
            titulo = "Ocorrências",
            subtitulo = "${ocorrencias.size} registradas no bairro"
        )

        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = filtro == null,
                    onClick = { filtro = null },
                    label = { Text("Todas") }
                )
            }
            items(Status.entries.toList()) { status ->
                FilterChip(
                    selected = filtro == status,
                    onClick = { filtro = status },
                    label = { Text(status.rotulo) }
                )
            }
        }

        if (visiveis.isEmpty()) {
            EstadoVazio(
                titulo = if (filtro == null) "Nenhuma ocorrência ainda" else "Nada neste filtro",
                texto = if (filtro == null) "Toque em Registrar para avisar sobre um problema."
                else "Não há ocorrências com o status ${filtro?.rotulo?.lowercase()}."
            )
        } else {
            LazyColumn(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(visiveis, key = { it.id }) { item ->
                    CartaoOcorrencia(
                        ocorrencia = item,
                        onAvancarStatus = { onAvancarStatus(item.id) },
                        onExcluir = { paraExcluir = item }
                    )
                }
            }
        }
    }

    paraExcluir?.let { alvo ->
        AlertDialog(
            onDismissRequest = { paraExcluir = null },
            title = { Text("Excluir ocorrência?") },
            text = { Text("\"${alvo.titulo}\" será removida da lista.") },
            confirmButton = {
                TextButton(onClick = { onExcluir(alvo.id); paraExcluir = null }) {
                    Text("Excluir", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { paraExcluir = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun CartaoOcorrencia(
    ocorrencia: Ocorrencia,
    onAvancarStatus: () -> Unit,
    onExcluir: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                IconeCategoria(ocorrencia.categoria)
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    Text(ocorrencia.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(
                        ocorrencia.endereco,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                SeloStatus(ocorrencia.status)
            }
            if (ocorrencia.descricao.isNotBlank()) {
                Text(
                    ocorrencia.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${ocorrencia.categoria.rotulo} · ${ocorrencia.dataCriacao}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onAvancarStatus) {
                    Text("Marcar como ${ocorrencia.status.proximo().rotulo.lowercase()}")
                }
                IconButton(onClick = onExcluir) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Excluir",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
