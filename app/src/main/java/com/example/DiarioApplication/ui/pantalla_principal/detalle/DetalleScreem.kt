import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.DiarioApplication.ui.NoteViewModel
import com.example.inventory.ui.AppViewModelProvider

@Composable
fun NoteScreen(
    onHomeClick: () -> Unit,
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val note = viewModel.latestNote.collectAsState().value
    val imageFile = viewModel.latestImageFile.collectAsState().value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageFile != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageFile),
                    contentDescription = "Imagen más reciente",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                BasicText("No hay imagen disponible")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (note != null) {
                BasicText(text = note.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                BasicText(text = note.content, style = MaterialTheme.typography.bodyMedium)
            } else {
                BasicText("No hay notas disponibles")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para regresar al Home
            IconButton(onClick = onHomeClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Home", tint = Color(0xFF607D8B))
            }
        }
    }
}
