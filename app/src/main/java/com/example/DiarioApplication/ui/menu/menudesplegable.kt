package com.example.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController

@Composable
fun MenuDesplegableScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.50f)
            .padding(16.dp)
            .background(Color(0xFFE1F5FE))
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Sección de imagen y nombre del usuario
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(60.dp)) {
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

        // Botón "Mis Vivencias"
        MenuItem(
            icon = Icons.Filled.FolderOpen,
            title = "Mis Vivencias",
            onClick = { navController.navigate("vivencias") }, // Navegación a "Vivencias"
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Nueva Vivencia"
        MenuItem(
            icon = Icons.Filled.Add,
            title = "Nueva Vivencia",
            onClick = { navController.navigate("nuevaVivencia") }, // Navegación a "Home"
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Perfil" (puedes agregar la lógica si tienes un perfil)
        MenuItem(
            icon = Icons.Filled.AccountCircle,
            title = "Perfil",
            onClick = { navController.navigate("perfil") }, // Placeholder si tienes una pantalla de perfil
            iconTint = Color(0xFF0288D1),
            textColor = Color(0xFF01579B)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Cerrar Sesión"
        MenuItem(
            icon = Icons.Filled.ExitToApp,
            title = "Cerrar Sesión",
            onClick = { navController.navigate("login") }, // Navegación a "Login"
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
