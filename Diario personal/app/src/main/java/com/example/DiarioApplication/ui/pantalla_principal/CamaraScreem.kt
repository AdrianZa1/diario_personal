package com.example.DiarioApplication.ui.pantalla_principal

import CameraGalleryViewModel
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun CameraGalleryScreen(viewModel: CameraGalleryViewModel = viewModel()) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers for Camera and Gallery Intents
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = saveImageToGallery(context, bitmap) // Convert the Bitmap to Uri
            viewModel.setImageUri(uri)
        } else {
            Toast.makeText(context, "Error taking photo", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImageUri(uri)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (hasPermissions(context)) {
                    cameraLauncher.launch(null)
                } else {
                    requestPermissions(context)
                }
            }
        ) {
            Text(text = "Abrir Cámara")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (hasPermissions(context)) {
                    galleryLauncher.launch("image/*")
                } else {
                    requestPermissions(context)
                }
            }
        ) {
            Text(text = "Abrir Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la imagen seleccionada o capturada
        viewModel.imageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// Función para solicitar permisos
fun hasPermissions(context: Context): Boolean {
    val cameraPermission = Manifest.permission.CAMERA
    val readStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
    return context.checkSelfPermission(cameraPermission) == Activity.PERMISSION_GRANTED &&
            context.checkSelfPermission(readStoragePermission) == Activity.PERMISSION_GRANTED
}

fun requestPermissions(context: Context) {
    Toast.makeText(context, "Por favor habilita los permisos de cámara y galería.", Toast.LENGTH_SHORT).show()
}

// Guardar la imagen en la galería (si decides guardar el bitmap de la cámara)
fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    return uri
}
