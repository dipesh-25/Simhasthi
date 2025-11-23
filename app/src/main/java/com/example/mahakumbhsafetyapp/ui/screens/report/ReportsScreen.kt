package com.example.mahakumbhsafetyapp.ui.screens.report

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import coil.compose.AsyncImage

data class LostItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val contact: String = "",
    val photoUrl: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() { // Tab screen - no NavController parameter

    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    var items by remember { mutableStateOf<List<LostItem>>(emptyList()) }

    // Load items from Firestore
    LaunchedEffect(true) {
        db.collection("lost_items").addSnapshotListener { snap, _ ->
            if (snap != null) {
                items = snap.documents.map { d ->
                    LostItem(
                        id = d.id,
                        name = d.getString("name") ?: "",
                        description = d.getString("description") ?: "",
                        contact = d.getString("contact") ?: "",
                        photoUrl = d.getString("photoUrl")
                    )
                }
            }
        }
    }

    // Image picker launcher
    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    val primaryGold = Color(0xFFFFD54F)
    val cardBackground = Color(0xFF1A2C38)
    val inputBackground = Color(0xFF0A1820)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lost & Found Reports", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF040B11))
            )
        },
        containerColor = Color(0xFF040B11)
    ) { padding ->

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --------- INPUT SECTION (Report Lost) ---------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp).fillMaxWidth()) {
                        Text(
                            "Report a Lost Person/Item",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryGold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Name
                        OutlinedTextField(
                            value = name, onValueChange = { name = it },
                            label = { Text("Name of Person/Item", color = Color.White) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors( // <-- FIXED
                                focusedContainerColor = inputBackground,
                                unfocusedContainerColor = inputBackground,
                                focusedBorderColor = primaryGold,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = primaryGold,
                                unfocusedLabelColor = Color.White
                            )
                        )
                        Spacer(Modifier.height(8.dp))

                        // Description
                        OutlinedTextField(
                            value = description, onValueChange = { description = it },
                            label = { Text("Description", color = Color.White) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors( // <-- FIXED
                                focusedContainerColor = inputBackground,
                                unfocusedContainerColor = inputBackground,
                                focusedBorderColor = primaryGold,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = primaryGold,
                                unfocusedLabelColor = Color.White
                            )
                        )
                        Spacer(Modifier.height(8.dp))

                        // Contact
                        OutlinedTextField(
                            value = contact, onValueChange = { contact = it },
                            label = { Text("Contact Number", color = Color.White) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors( // <-- FIXED
                                focusedContainerColor = inputBackground,
                                unfocusedContainerColor = inputBackground,
                                focusedBorderColor = primaryGold,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = primaryGold,
                                unfocusedLabelColor = Color.White
                            )
                        )
                        Spacer(Modifier.height(12.dp))

                        // Image Picker
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(onClick = { pickImage.launch("image/*") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004488))) {
                                Text(if (imageUri != null) "Change Photo" else "Select Photo")
                            }
                            Spacer(Modifier.width(10.dp))
                            if (imageUri != null) {
                                Text("Photo Selected", color = Color.Green)
                            } else {
                                Text("No photo selected", color = Color.Gray)
                            }
                        }
                        Spacer(Modifier.height(12.dp))

                        // Submit Button
                        Button(
                            onClick = {
                                isUploading = true
                                if (imageUri != null) {
                                    uploadImageAndSave(
                                        storage, db, imageUri!!, name, description, contact
                                    ) {
                                        isUploading = false
                                        name = ""; description = ""; contact = ""; imageUri = null
                                    }
                                } else {
                                    saveLostItem(db, name, description, contact, null)
                                    isUploading = false
                                    name = ""; description = ""; contact = ""; imageUri = null
                                }
                            },
                            enabled = name.isNotBlank() && contact.isNotBlank() && !isUploading,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryGold)
                        ) {
                            if (isUploading) {
                                CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                            } else {
                                Text("Submit Report", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    "Recent Reports by Community",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp)
                )
            }

            // --------- DISPLAY REPORTS ---------
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("Name: ${item.name}", fontWeight = FontWeight.Bold, color = primaryGold)
                        Text("Description: ${item.description}", color = Color.White)
                        Text("Contact: ${item.contact}", color = Color.White)

                        if (item.photoUrl != null) {
                            Spacer(Modifier.height(8.dp))
                            Text("Photo attached:", color = Color.Gray, fontSize = 12.sp)
                            AsyncImage(
                                model = item.photoUrl,
                                contentDescription = "Lost item photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(top = 4.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun uploadImageAndSave(
    storage: FirebaseStorage,
    db: FirebaseFirestore,
    uri: Uri,
    name: String,
    description: String,
    contact: String,
    onDone: () -> Unit
) {
    val ref = storage.reference.child("lost_images/${System.currentTimeMillis()}.jpg")
    ref.putFile(uri)
        .addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { url ->
                saveLostItem(db, name, description, contact, url.toString())
                onDone()
            }
        }
}

private fun saveLostItem(
    db: FirebaseFirestore,
    name: String,
    desc: String,
    contact: String,
    photo: String?
) {
    val item = hashMapOf(
        "name" to name,
        "description" to desc,
        "contact" to contact,
        "photoUrl" to photo
    )
    db.collection("lost_items").add(item)
}