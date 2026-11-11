package davi.github.com.conectapovokt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import davi.github.com.conectapovokt.model.Categoria

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CadastroScreen(
    onSalvar: (titulo: String, descricao: String, categoria: Categoria, endereco: String) -> Unit
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var descricao by rememberSaveable { mutableStateOf("") }
    var endereco by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf(Categoria.BURACO) }
    var tentouSalvar by rememberSaveable { mutableStateOf(false) }

    val erroTitulo = tentouSalvar && titulo.trim().length < 3
    val erroEndereco = tentouSalvar && endereco.isBlank()

    Column(
        Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        CabecalhoTela(
            titulo = "Registrar problema",
            subtitulo = "Quanto mais detalhes, mais rápido é resolver."
        )

        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("O que está acontecendo?", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Categoria.entries.forEach { cat ->
                    FilterChip(
                        selected = categoria == cat,
                        onClick = { categoria = cat },
                        label = { Text(cat.rotulo) }
                    )
                }
            }

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título *") },
                isError = erroTitulo,
                supportingText = { if (erroTitulo) Text("Informe um título com pelo menos 3 letras.") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço ou ponto de referência *") },
                isError = erroEndereco,
                supportingText = { if (erroEndereco) Text("Informe onde fica o problema.") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição (opcional)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    tentouSalvar = true
                    if (titulo.trim().length >= 3 && endereco.isNotBlank()) {
                        onSalvar(titulo, descricao, categoria, endereco)
                        titulo = ""; descricao = ""; endereco = ""
                        categoria = Categoria.BURACO
                        tentouSalvar = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Salvar ocorrência")
            }
        }
    }
}
