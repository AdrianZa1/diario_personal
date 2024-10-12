package com.example.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MenuDesplegableScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .padding(16.dp)
            .background(Color(0xFFE1F5FE))
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        // Sección de imagen y nombre del usuario
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
                    text = "Nombre Apellido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0277BD)
                )
                Text(
                    text = "yourname@gmail.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        MenuItem(
            icon = Icons.Filled.Folder,
            title = "Mis vivencias",
            onClick = { navController.navigate("vivencias") },
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            icon = Icons.Filled.Add,
            title = "Nueva vivencia",
            onClick = { navController.navigate("nuevaVivencia") },
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            icon = Icons.Filled.AccountCircle,
            title = "Perfil",
            onClick = { navController.navigate("userProfile/{userId}") }, // Navegación al perfil
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            icon = Icons.Filled.Settings,
            title = "Configuración",
            onClick = { navController.navigate("configuracion") }, // Navegación a la configuración
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            icon = Icons.Filled.ExitToApp,
            title = "Cerrar sesión",
            onClick = { navController.navigate("login") }, // Navegación para cerrar sesión
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )
    }
}


@Composable
fun MenuItem(icon: ImageVector, title: String, onClick: () -> Unit, iconTint: Color, textColor: Color) {
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
            tint = iconTint,  // Aplicamos color al icono
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,  // Aplicamos color al texto
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}
