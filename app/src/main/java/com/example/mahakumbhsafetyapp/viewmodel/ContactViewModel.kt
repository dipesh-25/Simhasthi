package com.example.mahakumbhsafetyapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mahakumbhsafetyapp.data.EmergencyContact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

// --- ENVIRONMENT VARIABLES (Hardcoded placeholders for environment dependency) ---
// In a running environment, these would be provided externally (e.g., from __app_id)
private const val appId = "default-app-id"
private val initialAuthToken: String? = null // Assume token is provided if available

class ContactViewModel : ViewModel() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var userId: String = ""
    private var firestoreListener: ListenerRegistration? = null

    // State list for UI observation
    val contacts = mutableStateListOf<EmergencyContact>()
    var isLoading = mutableStateOf(true)
    var error: MutableState<String?> = mutableStateOf(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initializeFirebase()
        }
    }

    private suspend fun initializeFirebase() {
        try {
            auth = Firebase.auth
            db = Firebase.firestore

            // 1. Authenticate user using custom token or anonymously
            if (initialAuthToken != null) {
                auth.signInWithCustomToken(initialAuthToken).await()
            } else {
                auth.signInAnonymously().await()
            }

            // 2. Get User ID (authenticated or anonymous)
            userId = auth.currentUser?.uid ?: UUID.randomUUID().toString()

            // 3. Start listening for real-time contact updates
            startContactsListener()

        } catch (e: Exception) {
            error.value = "Failed to initialize Firebase: ${e.message}"
            println("Firebase Init Error: ${e.message}")
            isLoading.value = false
        }
    }

    private fun getCollectionPath(userId: String) =
        // MANDATORY Firestore Private Data Path Structure
        "artifacts/$appId/users/$userId/emergency_contacts"

    private fun startContactsListener() {
        firestoreListener?.remove()

        if (userId.isBlank()) {
            isLoading.value = false
            return
        }

        val contactsCollection = db.collection(getCollectionPath(userId))

        firestoreListener = contactsCollection.addSnapshotListener { snapshot, e ->
            isLoading.value = false
            if (e != null) {
                error.value = "Listen failed: ${e.message}"
                println("Firestore Listen Error: ${e.message}")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val updatedContacts = snapshot.documents.mapNotNull { document ->
                    // Map document data to EmergencyContact, including the document ID
                    document.toObject(EmergencyContact::class.java)?.copy(id = document.id)
                }.sortedBy { it.name }

                contacts.clear()
                contacts.addAll(updatedContacts)
            }
        }
    }

    fun addContact(contact: EmergencyContact) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection(getCollectionPath(userId)).add(contact).await()
            } catch (e: Exception) {
                error.value = "Failed to add contact: ${e.message}"
                println("Add Contact Error: ${e.message}")
            }
        }
    }

    fun deleteContact(contactId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection(getCollectionPath(userId)).document(contactId).delete().await()
            } catch (e: Exception) {
                error.value = "Failed to delete contact: ${e.message}"
                println("Delete Contact Error: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        firestoreListener?.remove()
    }
}