package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue // CRITICAL FIX
import com.example.mahakumbhsafetyapp.ui.theme.LightBackground // CRITICAL FIX
import com.example.mahakumbhsafetyapp.ui.theme.DarkText // CRITICAL FIX
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data model for Firestore
data class Report(
    val id: String = UUID.randomUUID().toString(),
    val type: String = "",
    val item: String = "",
    val location: String = "",
    val contact: String = "",
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

class LostFoundViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val reportsCollection = db.collection("LostFoundReports")

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchReportsRealtime()
    }

    private fun fetchReportsRealtime() {
        _isLoading.value = true
        // Listen for all documents, sorted by timestamp descending
        reportsCollection
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                _isLoading.value = false
                if (e != null) {
                    println("Lost/Found Listener failed: $e")
                    return@addSnapshotListener
                }

                // Convert Firestore documents to Report data class objects
                val reportList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Report::class.java)
                } ?: emptyList()
                _reports.value = reportList
            }
    }

    // Function to submit a new report to Firestore
    fun submitReport(report: Report) {
        viewModelScope.launch {
            try {
                // Set the report using its unique ID
                reportsCollection.document(report.id).set(report).await()
                println("Report submitted successfully: ${report.id}")
            } catch (e: Exception) {
                println("Error submitting report: $e")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostFoundScreen(navController: NavController, viewModel: LostFoundViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Collect reports and loading state as Compose state
    val reports by viewModel.reports.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var reportType by remember { mutableStateOf("Lost Item") }
    var itemName by remember { mutableStateOf("") }
    var itemLocation by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val primaryBlue = PrimaryBlue
    val lightBackground = LightBackground

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lost & Found", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primaryBlue,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = lightBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // --- Report Form (Scrollable) ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Report a Lost or Found Item", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = DarkText)
                Divider(color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ReportTypeChip(
                        label = "Lost Item",
                        isSelected = reportType == "Lost Item",
                        onClick = { reportType = "Lost Item" },
                        modifier = Modifier.weight(1f)
                    )
                    ReportTypeChip(
                        label = "Found Item/Person",
                        isSelected = reportType == "Found Item/Person",
                        onClick = { reportType = "Found Item/Person" },
                        modifier = Modifier.weight(1f)
                    )
                }

                // --- Text Fields ---
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text(if (reportType.contains("Lost")) "Lost Item/Person Name" else "Found Item/Person Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = primaryBlue) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = itemLocation,
                    onValueChange = { itemLocation = it },
                    label = { Text("Last Seen/Found Location") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = primaryBlue) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("Your Contact Information (e.g., Phone/Email)") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = primaryBlue) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = itemDescription,
                    onValueChange = { itemDescription = it },
                    label = { Text("Detailed Description") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // --- Submit Button ---
                Button(
                    onClick = {
                        if (itemName.isNotBlank() && itemLocation.isNotBlank() && contactName.isNotBlank()) {
                            isSubmitting = true
                            val newReport = Report(
                                type = reportType,
                                item = itemName,
                                location = itemLocation,
                                contact = contactName,
                                description = itemDescription,
                                timestamp = System.currentTimeMillis()
                            )
                            viewModel.submitReport(newReport)

                            // Reset form fields
                            itemName = ""
                            itemLocation = ""
                            itemDescription = ""
                            contactName = ""
                            isSubmitting = false
                        }
                    },
                    enabled = !isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Submit Report", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Divider(color = Color.LightGray)
            }

            // --- Reports List ---
            Text("Recent Reports (${reports.size})", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = DarkText)

            if (isLoading) {
                // Center the loading indicator below the form
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryBlue)
                }
            } else if (reports.isEmpty()) {
                Text("No recent reports found.", modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally), color = Color.Gray)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reports) { report ->
                        ReportItemCard(report = report)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportTypeChip(label: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    Surface(
        color = if (isSelected) PrimaryBlue else Color.Transparent,
        contentColor = if (isSelected) Color.White else PrimaryBlue,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, PrimaryBlue),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Text(
            label,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        )
    }
}

@Composable
fun ReportItemCard(report: Report) {
    val dateFormatter = remember { SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()) }
    // Conditional styling based on report type
    val cardColor = if (report.type.contains("Lost")) Color(0xFFFBE9E7) else Color(0xFFE8F5E9) // Light Red/Green indicators
    val titleColor = if (report.type.contains("Lost")) Color(0xFFC62828) else Color(0xFF2E7D32)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${report.type}: ${report.item}",
                fontWeight = FontWeight.Bold,
                color = titleColor,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Text("Location: ${report.location}", style = MaterialTheme.typography.bodyMedium, color = DarkText)
            Text("Description: ${report.description}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Reported: ${dateFormatter.format(Date(report.timestamp))} | Contact: ${report.contact}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}