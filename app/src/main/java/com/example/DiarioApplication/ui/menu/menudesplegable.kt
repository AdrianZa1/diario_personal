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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MenuDesplegableScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()           // Ocupará todo el alto
            .fillMaxWidth(0.75f)        // Solo ocupa el 50% del ancho
            .padding(16.dp)
            .background(Color(0xFFE1F5FE)) // Fondo celeste claro
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Sección de imagen y nombre del usuario
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Imagen del perfil
            Box(
                modifier = Modifier
                    .size(60.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.AccountCircle, // Icono predeterminado de perfil
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.background(Color(0xFF81D4FA), shape = CircleShape) // Fondo celeste en la imagen
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Nombre y correo
            Column {
                Text(
                    text = "Nombre Apellido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0277BD) // Color de texto celeste oscuro
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
            icon = Icons.Filled.Folder,  // Icono de carpeta
            title = "Mis vivencias",
            onClick = { /* Acción para abrir mis vivencias */ },
            iconTint = Color(0xFF0288D1),  // Color de icono celeste
            textColor = Color(0xFF01579B)  // Color de texto celeste oscuro
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Nueva vivencia"
        MenuItem(
            icon = Icons.Filled.Add,  // Icono para añadir una nueva vivencia
            title = "Nueva vivencia",
            onClick = { /* Acción para añadir una nueva vivencia */ },
            iconTint = Color(0xFF0288D1), // Color de icono celeste
            textColor = Color(0xFF01579B)  // Color de texto celeste oscuro
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Perfil"
        MenuItem(
            icon = Icons.Filled.AccountCircle,  // Icono de perfil
            title = "Perfil",
            onClick = { /* Acción para abrir el perfil */ },
            iconTint = Color(0xFF0288D1), // Color de icono celeste
            textColor = Color(0xFF01579B)  // Color de texto celeste oscuro
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Cerrar sesión"
        MenuItem(
            icon = Icons.Filled.ExitToApp,  // Icono para cerrar sesión
            title = "Cerrar sesión",
            onClick = { /* Acción para cerrar sesión */ },
            iconTint = Color(0xFF0288D1), // Color de icono celeste
            textColor = Color(0xFF01579B)  // Color de texto celeste oscuro
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

@Preview(showBackground = true)
@Composable
fun MenuDesplegableScreenPreview() {
    MenuDesplegableScreen()
}
