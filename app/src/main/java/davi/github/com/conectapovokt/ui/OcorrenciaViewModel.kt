package davi.github.com.conectapovokt.ui

import androidx.lifecycle.ViewModel
import davi.github.com.conectapovokt.model.Categoria
import davi.github.com.conectapovokt.model.Ocorrencia
import davi.github.com.conectapovokt.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class OcorrenciaViewModel : ViewModel() {

    private val _ocorrencias = MutableStateFlow(exemplos())
    val ocorrencias: StateFlow<List<Ocorrencia>> = _ocorrencias.asStateFlow()

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    fun salvar(titulo: String, descricao: String, categoria: Categoria, endereco: String) {
        val nova = Ocorrencia(
            id = (_ocorrencias.value.maxOfOrNull { it.id } ?: 0) + 1,
            titulo = titulo.trim(),
            descricao = descricao.trim(),
            categoria = categoria,
            endereco = endereco.trim(),
            dataCriacao = hoje()
        )
        _ocorrencias.update { listOf(nova) + it }
        _mensagem.value = "Ocorrência registrada"
    }

    fun avancarStatus(id: Int) {
        _ocorrencias.update { lista ->
            lista.map { if (it.id == id) it.copy(status = it.status.proximo()) else it }
        }
        _mensagem.value = "Status atualizado"
    }

    fun excluir(id: Int) {
        _ocorrencias.update { lista -> lista.filterNot { it.id == id } }
        _mensagem.value = "Ocorrência excluída"
    }

    fun limparMensagem() {
        _mensagem.value = null
    }

    private fun hoje(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date())

    private fun exemplos() = listOf(
        Ocorrencia(
            id = 3, titulo = "Buraco profundo na faixa da direita",
            descricao = "Já causou dois pneus furados na semana.",
            categoria = Categoria.BURACO, endereco = "Rua das Acácias, 120",
            status = Status.ABERTA, dataCriacao = "09/11/2026"
        ),
        Ocorrencia(
            id = 2, titulo = "Poste apagado em frente à praça",
            descricao = "Trecho fica totalmente escuro à noite.",
            categoria = Categoria.ILUMINACAO, endereco = "Praça Central, s/n",
            status = Status.EM_ANDAMENTO, dataCriacao = "06/11/2026"
        ),
        Ocorrencia(
            id = 1, titulo = "Calçada quebrada perto do ponto de ônibus",
            descricao = "Difícil para cadeirantes e idosos.",
            categoria = Categoria.CALCADA, endereco = "Av. Brasil, 845",
            status = Status.RESOLVIDA, dataCriacao = "28/10/2026"
        )
    )
}
