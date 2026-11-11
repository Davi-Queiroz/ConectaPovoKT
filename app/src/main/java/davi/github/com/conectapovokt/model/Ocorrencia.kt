package davi.github.com.conectapovokt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Categoria(val rotulo: String) {
    BURACO("Buraco"),
    ILUMINACAO("Iluminação"),
    LIXO("Lixo e entulho"),
    CALCADA("Calçada"),
    OUTRO("Outro")
}

enum class Status(val rotulo: String) {
    ABERTA("Aberta"),
    EM_ANDAMENTO("Em andamento"),
    RESOLVIDA("Resolvida");

    fun proximo(): Status = when (this) {
        ABERTA -> EM_ANDAMENTO
        EM_ANDAMENTO -> RESOLVIDA
        RESOLVIDA -> ABERTA
    }
}

@Entity(tableName = "ocorrencias")
data class Ocorrencia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val categoria: Categoria,
    val endereco: String,
    val status: Status = Status.ABERTA,
    val dataCriacao: String
)
