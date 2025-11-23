package com.example.mahakumbhsafetyapp.ui.screens.login

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.SecondaryAccent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private val primaryYellow = SecondaryAccent
private val darkBlueGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF040B13), PrimaryBlue)
)

@Composable
fun LoginScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val prefs = context.getSharedPreferences("mahakumbh_prefs", Context.MODE_PRIVATE)

    var email by remember { mutableStateOf(prefs.getString("saved_email", "") ?: "") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(prefs.getBoolean("remember_me", false)) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Auto-login if user is already authenticated and remember me is enabled
    LaunchedEffect(Unit) {
        if (auth.currentUser != null && rememberMe) {
            navController.navigate(Routes.MAIN) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    val fieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = primaryYellow,
        unfocusedIndicatorColor = Color.LightGray,
        cursorColor = primaryYellow,
        focusedContainerColor = Color.White.copy(alpha = 0.9f),
        unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = darkBlueGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome Back!",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // --- EMAIL FIELD ---
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email", tint = primaryYellow) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )
            Spacer(Modifier.height(16.dp))

            // --- PASSWORD FIELD ---
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Lock", tint = primaryYellow) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, contentDescription = "Toggle password visibility", tint = Color.Gray)
                    }
                },
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )
            Spacer(Modifier.height(16.dp))

            // --- REMEMBER ME CHECKBOX ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(checkedColor = primaryYellow, uncheckedColor = Color.LightGray),
                    enabled = !isLoading
                )
                Text("Remember Me", color = Color.LightGray, fontSize = 14.sp)
            }

            // --- ERROR MESSAGE ---
            if (errorMessage.isNotEmpty()) {
                Text(
                    errorMessage,
                    color = Color(0xFFEF5350),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            // --- LOGIN BUTTON ---
            Button(
                onClick = {
                    errorMessage = ""
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true
                        scope.launch {
                            try {
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        isLoading = false
                                        if (task.isSuccessful) {
                                            // Save credentials if remember me is checked
                                            if (rememberMe) {
                                                prefs.edit().apply {
                                                    putString("saved_email", email)
                                                    putBoolean("remember_me", true)
                                                    apply()
                                                }
                                            } else {
                                                prefs.edit().apply {
                                                    remove("saved_email")
                                                    putBoolean("remember_me", false)
                                                    apply()
                                                }
                                            }
                                            navController.navigate(Routes.MAIN) {
                                                popUpTo(Routes.LOGIN) { inclusive = true }
                                            }
                                        } else {
                                            errorMessage = task.exception?.message ?: "Login failed"
                                        }
                                    }
                            } catch (e: Exception) {
                                isLoading = false
                                errorMessage = e.message ?: "An error occurred"
                            }
                        }
                    } else {
                        errorMessage = "Please fill in all fields"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryYellow),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?", color = Color.White)
                TextButton(onClick = { navController.navigate(Routes.SIGNUP) }) {
                    Text("Sign Up", color = primaryYellow, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}