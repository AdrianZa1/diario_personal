import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.io.FileOutputStream

class CameraViewModel : ViewModel() {
    private val _imagePath = MutableStateFlow<String?>(null)  // Almacena la ruta de la imagen guardada
    val imagePath: StateFlow<String?> get() = _imagePath

    // Función para guardar la imagen en la memoria interna
    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap) {
        // Crear un archivo en el directorio de la app
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, filename)

        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        }

        // Actualizar la ruta de la imagen en el ViewModel
        _imagePath.value = file.absolutePath
    }
}
