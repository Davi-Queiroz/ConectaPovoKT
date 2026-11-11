package davi.github.com.conectapovokt.data

import davi.github.com.conectapovokt.model.Ocorrencia
import kotlinx.coroutines.flow.Flow

class OcorrenciaRepository(private val dao: OcorrenciaDao) {
    val ocorrencias: Flow<List<Ocorrencia>> = dao.listar()

    suspend fun inserir(ocorrencia: Ocorrencia) = dao.inserir(ocorrencia)
    suspend fun atualizar(ocorrencia: Ocorrencia) = dao.atualizar(ocorrencia)
    suspend fun excluir(ocorrencia: Ocorrencia) = dao.excluir(ocorrencia)
}
