package davi.github.com.conectapovokt.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import davi.github.com.conectapovokt.model.Ocorrencia
import kotlinx.coroutines.flow.Flow

@Dao
interface OcorrenciaDao {
    @Query("SELECT * FROM ocorrencias ORDER BY id DESC")
    fun listar(): Flow<List<Ocorrencia>>

    @Insert
    suspend fun inserir(ocorrencia: Ocorrencia)

    @Update
    suspend fun atualizar(ocorrencia: Ocorrencia)

    @Delete
    suspend fun excluir(ocorrencia: Ocorrencia)
}
