package com.saveetha.rentmate

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

// Data Models
data class Listing(
    val id: String,
    val title: String,
    val bhkType: String,
    val propertyType: String,
    val preferredTenant: String,
    val furnishing: String,
    val bathrooms: String,
    val squareFeet: String,
    val description: String,
    val monthlyRent: String,
    val securityDeposit: String,
    val monthlyMaintenance: String,
    val brokerage: String,
    val availableFrom: String,
    val noticePeriod: String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val landmarks: String,
    val views: Int = 0,
    val status: String = "Active"
)

data class Applicant(
    val id: String,
    val name: String,
    val propertyId: String,
    val propertyAddress: String,
    val income: String,
    val moveInDate: String,
    val creditScore: String,
    val status: String = "Pending" // Pending, Accepted, Rejected
)

data class Message(
    val id: String,
    val senderName: String,
    val message: String,
    val timestamp: String
)

data class Visit(
    val id: String,
    val propertyId: String,
    val propertyTitle: String,
    val propertyAddress: String,
    val date: String,
    val time: String,
    val status: String = "Scheduled"
)

// Singleton Repository
object ListingRepository {
    private val _listings: SnapshotStateList<Listing> = mutableStateListOf()
    private val _applicants: SnapshotStateList<Applicant> = mutableStateListOf()
    private val _messages: SnapshotStateList<Message> = mutableStateListOf()
    private val _visits: SnapshotStateList<Visit> = mutableStateListOf()
    
    val listings: List<Listing> get() = _listings
    val applicants: List<Applicant> get() = _applicants.filter { it.status == "Pending" }
    val messages: List<Message> get() = _messages
    val visits: List<Visit> get() = _visits
    
    init {
        // Add sample applicants
        _applicants.add(
            Applicant(
                id = "1",
                name = "John Doe",
                propertyId = "sample1",
                propertyAddress = "123 Main St",
                income = "₹45k/yr",
                moveInDate = "Move In - Dec 1st",
                creditScore = "Credit: 750"
            )
        )
        _applicants.add(
            Applicant(
                id = "2",
                name = "Jane Smith",
                propertyId = "sample2",
                propertyAddress = "456 Oak Ave",
                income = "₹70k/yr",
                moveInDate = "Move In - Immediate",
                creditScore = "Credit: /10"
            )
        )
        _applicants.add(
            Applicant(
                id = "3",
                name = "Mike Johnson",
                propertyId = "sample3",
                propertyAddress = "123 Main St",
                income = "₹52k/yr",
                moveInDate = "Move In - Nov 1st",
                creditScore = "Credit: 710"
            )
        )
        _applicants.add(
            Applicant(
                id = "4",
                name = "Sarah Williams",
                propertyId = "sample4",
                propertyAddress = "789 Pine Rd",
                income = "₹60k/yr",
                moveInDate = "Move In - Dec 10th",
                creditScore = "Credit: 720"
            )
        )
        _applicants.add(
            Applicant(
                id = "5",
                name = "Tom Brown",
                propertyId = "sample5",
                propertyAddress = "456 Oak Ave",
                income = "₹65k/yr",
                moveInDate = "Move In - Dec 1st",
                creditScore = "Credit: 690"
            )
        )
        
        // Add sample messages
        _messages.add(
            Message(
                id = "1",
                senderName = "Sarah Williams",
                message = "Is the apartment still available for October?",
                timestamp = "10:30 AM"
            )
        )
        _messages.add(
            Message(
                id = "2",
                senderName = "Mike Johnson",
                message = "Thanks for showing me the place yesterday!",
                timestamp = "Yesterday"
            )
        )
        _messages.add(
            Message(
                id = "3",
                senderName = "Jane Smith",
                message = "I have submitted my application.",
                timestamp = "Tue"
            )
        )
    }
    
    fun addListing(listing: Listing) {
        _listings.add(listing)
    }
    
    fun updateListing(listing: Listing) {
        val index = _listings.indexOfFirst { it.id == listing.id }
        if (index != -1) {
            _listings[index] = listing
        }
    }
    
    fun getListingById(id: String): Listing? {
        return _listings.find { it.id == id }
    }
    
    fun addApplicant(applicant: Applicant) {
        _applicants.add(applicant)
    }
    
    fun updateApplicantStatus(applicantId: String, status: String) {
        val index = _applicants.indexOfFirst { it.id == applicantId }
        if (index != -1) {
            _applicants[index] = _applicants[index].copy(status = status)
        }
    }
    
    fun addMessage(message: Message) {
        _messages.add(0, message) // Add to beginning
    }
    
    fun addVisit(visit: Visit) {
        _visits.add(visit)
    }
    
    fun getActiveListingsCount(): Int = _listings.count { it.status == "Active" }
    
    fun getNewApplicantsCount(): Int = _applicants.count { it.status == "Pending" }
    
    fun getTotalViews(): Int = _listings.sumOf { it.views }
}
