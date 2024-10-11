import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun NoteScreen(
    onHomeClick: () -> Unit,
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val note = viewModel.latestNote.collectAsState().value
    val imageFile = viewModel.latestImageFile.collectAsState().value

    // Estados locales para manejar los valores de edición de la nota
    var title by remember { mutableStateOf(TextFieldValue(note?.title ?: "")) }
    var content by remember { mutableStateOf(TextFieldValue(note?.content ?: "")) }
    var isEditing by remember { mutableStateOf(false) } // Controla si estamos en modo de edición

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
                if (isEditing) {
                    // Campos de texto para editar el título y el contenido
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    // Mostrar el título y el contenido de la nota sin editar
                    BasicText(text = note.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicText(text = note.content, style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botón para alternar entre el modo de edición y vista
                    Button(onClick = { isEditing = !isEditing }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Editar Nota")
                        Spacer(modifier = Modifier.width(8.dp))
                        BasicText(if (isEditing) "Cancelar" else "Editar Nota")
                    }

                    // Botón para guardar los cambios si estamos en modo de edición
                    if (isEditing) {
                        Button(onClick = {
                            viewModel.editarNota(note.id, title.text, content.text)
                            isEditing = false // Salir del modo de edición después de guardar
                        }) {
                            BasicText("Guardar Cambios")
                        }
                    }
                }
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

