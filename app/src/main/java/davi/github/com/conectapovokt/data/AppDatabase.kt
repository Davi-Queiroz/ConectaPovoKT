package davi.github.com.conectapovokt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import davi.github.com.conectapovokt.model.Ocorrencia

@Database(entities = [Ocorrencia::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ocorrenciaDao(): OcorrenciaDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obter(context: Context): AppDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bairro_em_dia.db"
                ).build().also { instancia = it }
            }
    }
}
