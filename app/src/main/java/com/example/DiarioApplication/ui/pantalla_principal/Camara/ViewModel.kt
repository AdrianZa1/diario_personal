import android.app.Activity
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel = viewModel(), onImageCaptured: () -> Unit) {
    val context = LocalContext.current

    // Lanzador para abrir la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                cameraViewModel.saveImageToInternalStorage(context, bitmap)  // Guardar imagen
            }
        }
    )

    // Lanzador para capturar la imagen con la cámara
    val captureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap: Bitmap? ->
            bitmap?.let {
                cameraViewModel.saveImageToInternalStorage(context, it)  // Guardar imagen
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Botones superpuestos para capturar o seleccionar una imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón para cerrar la cámara
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Cerrar",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable { (context as? Activity)?.finish() }
                )

                // Botón para abrir la galería
                Icon(
                    imageVector = Icons.Filled.Photo,
                    contentDescription = "Abrir Galería",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable { galleryLauncher.launch("image/*") }
                )
            }

            // Botón para capturar la imagen
            Button(
                onClick = { captureLauncher.launch(null) },
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Camera, contentDescription = "Tomar Foto")
            }
        }
    }
}
