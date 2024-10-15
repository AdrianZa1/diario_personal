import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.net.Uri

class CameraGalleryViewModel : ViewModel() {
    var imageUri: Uri? = null
        private set

    fun setImageUri(uri: Uri?) {
        viewModelScope.launch {
            imageUri = uri
        }
    }
}
