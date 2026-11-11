package davi.github.com.conectapovokt.model

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

    /** Próximo passo do acompanhamento; Resolvida volta a Aberta (reabrir). */
    fun proximo(): Status = when (this) {
        ABERTA -> EM_ANDAMENTO
        EM_ANDAMENTO -> RESOLVIDA
        RESOLVIDA -> ABERTA
    }
}

data class Ocorrencia(
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val categoria: Categoria,
    val endereco: String,
    val status: Status = Status.ABERTA,
    val dataCriacao: String
)
