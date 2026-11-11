package davi.github.com.conectapovokt.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import davi.github.com.conectapovokt.data.AppDatabase
import davi.github.com.conectapovokt.data.OcorrenciaRepository
import davi.github.com.conectapovokt.model.Categoria
import davi.github.com.conectapovokt.model.Ocorrencia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OcorrenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        OcorrenciaRepository(AppDatabase.obter(application).ocorrenciaDao())

    val ocorrencias: StateFlow<List<Ocorrencia>> = repository.ocorrencias
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _mensagem = MutableStateFlow<String?>(null)
    val mensagem: StateFlow<String?> = _mensagem.asStateFlow()

    fun salvar(titulo: String, descricao: String, categoria: Categoria, endereco: String) {
        viewModelScope.launch {
            repository.inserir(
                Ocorrencia(
                    titulo = titulo.trim(),
                    descricao = descricao.trim(),
                    categoria = categoria,
                    endereco = endereco.trim(),
                    dataCriacao = hoje()
                )
            )
            _mensagem.value = "Ocorrência registrada"
        }
    }

    fun avancarStatus(id: Int) {
        val atual = ocorrencias.value.firstOrNull { it.id == id } ?: return
        viewModelScope.launch {
            repository.atualizar(atual.copy(status = atual.status.proximo()))
            _mensagem.value = "Status atualizado"
        }
    }

    fun excluir(id: Int) {
        val atual = ocorrencias.value.firstOrNull { it.id == id } ?: return
        viewModelScope.launch {
            repository.excluir(atual)
            _mensagem.value = "Ocorrência excluída"
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }

    private fun hoje(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date())
}
