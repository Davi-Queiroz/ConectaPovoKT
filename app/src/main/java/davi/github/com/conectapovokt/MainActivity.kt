package davi.github.com.conectapovokt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import davi.github.com.conectapovokt.ui.navigation.AppNavigation
import davi.github.com.conectapovokt.ui.theme.ConectaPovoKTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConectaPovoKTTheme {
                AppNavigation()
            }
        }
    }
}
