package com.example.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventory.ui.AppViewModelProvider

@Composable
fun MenuDesplegableScreen(
    navController: NavHostController,
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userName by menuViewModel.userName.collectAsState()
    val userEmail by menuViewModel.userEmail.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .padding(16.dp)
            .background(Color(0xFFE1F5FE))
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Sección de imagen y datos del usuario
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(60.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.background(Color(0xFF81D4FA), shape = CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = userName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0277BD)
                )
                Text(
                    text = userEmail,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Elementos del menú
        MenuItem(
            icon = Icons.Filled.Folder,
            title = "Mis vivencias",
            onClick = { navController.navigate("vivencias") },  // Ruta corregida
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MenuItem(
            icon = Icons.Filled.Add,
            title = "Nueva vivencia",
            onClick = { navController.navigate("nuevaVivencia") }, // Ruta corregida
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MenuItem(
            icon = Icons.Filled.AccountCircle,
            title = "Perfil",
            onClick = { navController.navigate("userProfile/${menuViewModel.userName.value}") }, // Pasamos el userId
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MenuItem(
            icon = Icons.Filled.ExitToApp,
            title = "Cerrar sesión",
            onClick = {
                menuViewModel.onCerrarSesionClick()
                navController.navigate("login")
            },
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    iconTint: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}
