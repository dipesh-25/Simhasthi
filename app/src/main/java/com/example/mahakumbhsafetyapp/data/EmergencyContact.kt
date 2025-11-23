package com.example.mahakumbhsafetyapp.data

// Data class for an Emergency Contact, matching the Firestore document structure.
data class EmergencyContact(
    // 'id' will store the unique Firestore document ID
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val relationship: String = "" // e.g., "Family", "Friend", "Co-worker"
) {
    // Companion object for Firestore field names
    companion object {
        const val FIELD_NAME = "name"
        const val FIELD_PHONE = "phone"
        const val FIELD_RELATIONSHIP = "relationship"
    }
}