package com.saveetha.rentmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import com.saveetha.rentmate.ui.theme.RentmateTheme

sealed class Screen {
    object Splash : Screen()
    object Subscription : Screen()
    object UserSelection : Screen()
    object RenterLogin : Screen()
    object RenterSignUp : Screen()
    object VerifyEmail : Screen()
    object ForgotPassword : Screen()
    object ResetCode : Screen()
    object CreateNewPassword : Screen()
    object PasswordResetSuccess : Screen()
    object RenterHome : Screen()

    // Lister Screens
    object ListerLogin : Screen()
    object ListerSignUp : Screen()
    object ListerVerifyEmail : Screen()
    object ListerForgotPassword : Screen()
    object ListerResetCode : Screen()
    object ListerCreateNewPassword : Screen()
    object ListerPasswordResetSuccess : Screen()
    object ListerHome : Screen()
    object ListerProfile : Screen()
    object EditProfile : Screen()
    
    // Listing Screens
    object PropertyDetails : Screen()
    object PricingTerms : Screen()
    object PhotosLocation : Screen()
    object PreviewListing : Screen()
    object ListingPublished : Screen()
    
    // Lister Management Screens
    object ManageListings : Screen()
    object ReviewApplicants : Screen()
    object Notifications : Screen()
    data class ViewListingDetails(val listingId: String) : Screen()

    // Renter Flow Screens
    data class RenterListingDetails(val listingId: String) : Screen()
    data class ScheduleVisit(val listingId: String) : Screen()
    data class VisitConfirmed(val visitId: String) : Screen()
    data class Chat(val recipientName: String) : Screen()
    object ListerVisits : Screen()

    // Roommate Finder Flow
    object RoommateSearch : Screen()
    object CompatibilityQuizIntro : Screen()
    object CompatibilityQuiz : Screen()
    object MatchResults : Screen()
    object CompareRoommates : Screen()

    // Renter Profile Flow
    object RenterProfileSetupIntro : Screen()
    object RenterPersonalInfo : Screen()
    object RenterProfileDashboard : Screen()
    object RenterEditProfile : Screen()
}

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {
    private var onPaymentSuccessCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)
        enableEdgeToEdge()
        setContent {
            RentmateTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
                
                // ViewModels for authentication
                val loginViewModel = remember { LoginViewModel() }
                val signUpViewModel = remember { SignUpViewModel() }
                
                // Listing data state
                var editingListingId by remember { mutableStateOf<String?>(null) }
                var existingListing by remember { mutableStateOf<Listing?>(null) }
                
                var listingTitle by remember { mutableStateOf("") }
                var listingBHK by remember { mutableStateOf("") }
                var listingPropertyType by remember { mutableStateOf("") }
                var listingTenant by remember { mutableStateOf("") }
                var listingFurnishing by remember { mutableStateOf("") }
                var listingBathrooms by remember { mutableStateOf("") }
                var listingSquareFeet by remember { mutableStateOf("") }
                var listingDescription by remember { mutableStateOf("") }
                var listingMonthlyRent by remember { mutableStateOf("") }
                var listingSecurityDeposit by remember { mutableStateOf("") }
                var listingMaintenance by remember { mutableStateOf("") }
                var listingBrokerage by remember { mutableStateOf("") }
                var listingAvailableFrom by remember { mutableStateOf("") }
                var listingNoticePeriod by remember { mutableStateOf("") }
                var listingAddress by remember { mutableStateOf("") }
                var listingCity by remember { mutableStateOf("") }
                var listingState by remember { mutableStateOf("") }
                var listingPincode by remember { mutableStateOf("") }
                var listingLandmarks by remember { mutableStateOf("") }

                // Renter Profile State
                var renterName by remember { mutableStateOf("John Doe") }
                var renterEmail by remember { mutableStateOf("john.doe@email.com") }
                var renterPhone by remember { mutableStateOf("+1 (555) 123-4567") }
                var renterAge by remember { mutableStateOf("25") }
                var renterGender by remember { mutableStateOf("Men") }
                var renterOccupation by remember { mutableStateOf("Software Engineer") }
                var renterLocation by remember { mutableStateOf("Bangalore") }
                var renterBio by remember { mutableStateOf("Easy-going professional looking for a great living situation.") }

                // Clear listing data helper
                fun clearListingData() {
                    editingListingId = null
                    existingListing = null
                    listingTitle = ""
                    listingBHK = ""
                    listingPropertyType = ""
                    listingTenant = ""
                    listingFurnishing = ""
                    listingBathrooms = ""
                    listingSquareFeet = ""
                    listingDescription = ""
                    listingMonthlyRent = ""
                    listingSecurityDeposit = ""
                    listingMaintenance = ""
                    listingBrokerage = ""
                    listingAvailableFrom = ""
                    listingNoticePeriod = ""
                    listingAddress = ""
                    listingCity = ""
                    listingState = ""
                    listingPincode = ""
                    listingLandmarks = ""
                }

                when (val screen = currentScreen) {
                    Screen.Splash -> {
                        SplashScreen {
                            currentScreen = Screen.Subscription
                        }
                    }
                    Screen.Subscription -> {
                        SubscriptionScreen(
                            onSkipClick = { currentScreen = Screen.UserSelection },
                            onSubscribeClick = {
                                onPaymentSuccessCallback = {
                                    currentScreen = Screen.UserSelection
                                }
                                startRazorpayPayment()
                            }
                        )
                    }
                    Screen.UserSelection -> {
                        UserTypeSelectionScreen(
                            onRenterClick = { currentScreen = Screen.RenterLogin },
                            onListerClick = { currentScreen = Screen.ListerLogin }
                        )
                    }
                    Screen.RenterLogin -> {
                        RenterLoginScreen(
                            viewModel = loginViewModel,
                            onBackClick = { currentScreen = Screen.UserSelection },
                            onLoginSuccess = { currentScreen = Screen.RenterHome },
                            onSignUpClick = { currentScreen = Screen.RenterSignUp },
                            onForgotPasswordClick = { currentScreen = Screen.ForgotPassword }
                        )
                    }
                    Screen.RenterSignUp -> {
                        RenterSignUpScreen(
                            viewModel = signUpViewModel,
                            onBackClick = { currentScreen = Screen.RenterLogin },
                            onSignUpSuccess = { currentScreen = Screen.RenterProfileSetupIntro }
                        )
                    }
                    Screen.VerifyEmail -> {
                        VerifyEmailScreen(
                            onBackClick = { currentScreen = Screen.RenterSignUp },
                            onVerifyClick = { currentScreen = Screen.RenterProfileSetupIntro }
                        )
                    }
                    Screen.ForgotPassword -> {
                        ForgotPasswordScreen(
                            onBackClick = { currentScreen = Screen.RenterLogin },
                            onSendClick = { currentScreen = Screen.ResetCode }
                        )
                    }
                    Screen.ResetCode -> {
                        ResetCodeScreen(
                            onBackClick = { currentScreen = Screen.ForgotPassword },
                            onVerifyClick = { currentScreen = Screen.CreateNewPassword }
                        )
                    }
                    Screen.CreateNewPassword -> {
                        CreateNewPasswordScreen(
                            onBackClick = { currentScreen = Screen.ResetCode },
                            onResetClick = { currentScreen = Screen.PasswordResetSuccess }
                        )
                    }
                    Screen.PasswordResetSuccess -> {
                        PasswordResetSuccessScreen(
                            onLoginClick = { currentScreen = Screen.RenterLogin }
                        )
                    }

                    // Lister Flow
                    Screen.ListerLogin -> {
                        ListerLoginScreen(
                            viewModel = loginViewModel,
                            onBackClick = { currentScreen = Screen.UserSelection },
                            onLoginSuccess = { currentScreen = Screen.ListerHome },
                            onSignUpClick = { currentScreen = Screen.ListerSignUp },
                            onForgotPasswordClick = { currentScreen = Screen.ListerForgotPassword }
                        )
                    }
                    Screen.ListerSignUp -> {
                        ListerSignUpScreen(
                            viewModel = signUpViewModel,
                            onBackClick = { currentScreen = Screen.ListerLogin },
                            onSignUpSuccess = { currentScreen = Screen.ListerHome }
                        )
                    }
                    Screen.ListerVerifyEmail -> {
                        VerifyEmailScreen(
                            onBackClick = { currentScreen = Screen.ListerSignUp },
                            onVerifyClick = { currentScreen = Screen.ListerHome }
                        )
                    }
                    Screen.ListerForgotPassword -> {
                        ForgotPasswordScreen(
                            onBackClick = { currentScreen = Screen.ListerLogin },
                            onSendClick = { currentScreen = Screen.ListerResetCode }
                        )
                    }
                    Screen.ListerResetCode -> {
                        ResetCodeScreen(
                            onBackClick = { currentScreen = Screen.ListerForgotPassword },
                            onVerifyClick = { currentScreen = Screen.ListerCreateNewPassword }
                        )
                    }
                    Screen.ListerCreateNewPassword -> {
                        CreateNewPasswordScreen(
                            onBackClick = { currentScreen = Screen.ListerResetCode },
                            onResetClick = { currentScreen = Screen.ListerPasswordResetSuccess }
                        )
                    }
                    Screen.ListerPasswordResetSuccess -> {
                        PasswordResetSuccessScreen(
                            onLoginClick = { currentScreen = Screen.ListerLogin }
                        )
                    }

                    // Home Screens
                    Screen.RenterHome -> {
                        RenterHomeScreen(
                            onListingClick = { id -> currentScreen = Screen.RenterListingDetails(id) },
                            onProfileClick = { currentScreen = Screen.RenterProfileDashboard },
                            onRoommatesClick = { currentScreen = Screen.RoommateSearch },
                            onPayClick = { listing ->
                                onPaymentSuccessCallback = {
                                    Toast.makeText(this@MainActivity, "Payment successful for ${listing.title}", Toast.LENGTH_LONG).show()
                                }
                                startRazorpayPaymentForListing(listing)
                            }
                        )
                    }
                    is Screen.RenterListingDetails -> {
                        val listingId = screen.listingId
                        RenterListingDetailsScreen(
                            listingId = listingId,
                            onBackClick = { currentScreen = Screen.RenterHome },
                            onScheduleVisitClick = { id -> currentScreen = Screen.ScheduleVisit(id) },
                            onMessageOwnerClick = { _, name -> currentScreen = Screen.Chat(name) }
                        )
                    }
                    is Screen.ScheduleVisit -> {
                        val listingId = screen.listingId
                        ScheduleVisitScreen(
                            listingId = listingId,
                            onBackClick = { currentScreen = Screen.RenterListingDetails(listingId) },
                            onConfirmClick = { visitId -> currentScreen = Screen.VisitConfirmed(visitId) }
                        )
                    }
                    is Screen.VisitConfirmed -> {
                        val visitId = screen.visitId
                        VisitConfirmedScreen(
                            visitId = visitId,
                            onHomeClick = { currentScreen = Screen.RenterHome },
                            onMessageOwnerClick = { currentScreen = Screen.Chat("Rajesh Kumar") }
                        )
                    }
                    is Screen.Chat -> {
                        ChatScreen(
                            recipientName = screen.recipientName,
                            onBackClick = { currentScreen = Screen.RenterHome } // Or back to previous
                        )
                    }
                    Screen.ListerHome -> {
                        ListerHomeScreen(
                            onLogoutClick = { currentScreen = Screen.UserSelection },
                            onPostNewListingClick = { 
                                clearListingData()
                                currentScreen = Screen.PropertyDetails 
                            },
                            onManageListingsClick = { currentScreen = Screen.ManageListings },
                            onReviewApplicantsClick = { currentScreen = Screen.ReviewApplicants },
                            onNotificationsClick = { currentScreen = Screen.Notifications },
                            onVisitsClick = { currentScreen = Screen.ListerVisits },
                            onProfileClick = { currentScreen = Screen.ListerProfile }
                        )
                    }
                    Screen.ListerVisits -> {
                        ListerVisitsScreen(
                            onBackClick = { currentScreen = Screen.ListerHome }
                        )
                    }
                    Screen.ListerProfile -> {
                        ListerProfileScreen(
                            onBackClick = { currentScreen = Screen.ListerHome },
                            onEditClick = { currentScreen = Screen.EditProfile },
                            onLogoutClick = { currentScreen = Screen.UserSelection }
                        )
                    }
                    Screen.EditProfile -> {
                        EditProfileScreen(
                            onBackClick = { currentScreen = Screen.ListerProfile },
                            onSaveClick = { currentScreen = Screen.ListerProfile }
                        )
                    }
                    
                    // Listing Flow Screens
                    Screen.PropertyDetails -> {
                        PropertyDetailsScreen(
                            onBackClick = { currentScreen = Screen.ListerHome },
                            onContinueClick = { currentScreen = Screen.PricingTerms },
                            existingListing = existingListing,
                            onDataChange = { title, bhk, type, tenant, furnish, bath, sqft, desc ->
                                listingTitle = title
                                listingBHK = bhk
                                listingPropertyType = type
                                listingTenant = tenant
                                listingFurnishing = furnish
                                listingBathrooms = bath
                                listingSquareFeet = sqft
                                listingDescription = desc
                            }
                        )
                    }
                    Screen.PricingTerms -> {
                        PricingTermsScreen(
                            onBackClick = { currentScreen = Screen.PropertyDetails },
                            onContinueClick = { currentScreen = Screen.PhotosLocation },
                            existingListing = existingListing,
                            onDataChange = { rent, deposit, maint, broker, avail, notice ->
                                listingMonthlyRent = rent
                                listingSecurityDeposit = deposit
                                listingMaintenance = maint
                                listingBrokerage = broker
                                listingAvailableFrom = avail
                                listingNoticePeriod = notice
                            }
                        )
                    }
                    Screen.PhotosLocation -> {
                        PhotosLocationScreen(
                            onBackClick = { currentScreen = Screen.PricingTerms },
                            onContinueClick = { currentScreen = Screen.PreviewListing },
                            step = 3,
                            existingListing = existingListing,
                            onDataChange = { addr, city, state, pin, land ->
                                listingAddress = addr
                                listingCity = city
                                listingState = state
                                listingPincode = pin
                                listingLandmarks = land
                            }
                        )
                    }
                    Screen.PreviewListing -> {
                        PreviewListingScreen(
                            onBackClick = { currentScreen = Screen.PhotosLocation },
                            onEditClick = { currentScreen = Screen.PropertyDetails },
                            onPublishClick = {
                                if (editingListingId != null && existingListing != null) {
                                    // Update existing listing
                                    val updatedListing = existingListing!!.copy(
                                        title = listingTitle,
                                        bhkType = listingBHK,
                                        propertyType = listingPropertyType,
                                        preferredTenant = listingTenant,
                                        furnishing = listingFurnishing,
                                        bathrooms = listingBathrooms,
                                        squareFeet = listingSquareFeet,
                                        description = listingDescription,
                                        monthlyRent = listingMonthlyRent,
                                        securityDeposit = listingSecurityDeposit,
                                        monthlyMaintenance = listingMaintenance,
                                        brokerage = listingBrokerage,
                                        availableFrom = listingAvailableFrom,
                                        noticePeriod = listingNoticePeriod,
                                        address = listingAddress,
                                        city = listingCity,
                                        state = listingState,
                                        pincode = listingPincode,
                                        landmarks = listingLandmarks
                                    )
                                    ListingRepository.updateListing(updatedListing)
                                    currentScreen = Screen.ManageListings
                                    clearListingData()
                                } else {
                                    // Add new listing
                                    val newListing = Listing(
                                        id = System.currentTimeMillis().toString(),
                                        title = listingTitle.ifEmpty { "Property Listing" },
                                        bhkType = listingBHK,
                                        propertyType = listingPropertyType,
                                        preferredTenant = listingTenant,
                                        furnishing = listingFurnishing,
                                        bathrooms = listingBathrooms,
                                        squareFeet = listingSquareFeet,
                                        description = listingDescription,
                                        monthlyRent = listingMonthlyRent,
                                        securityDeposit = listingSecurityDeposit,
                                        monthlyMaintenance = listingMaintenance,
                                        brokerage = listingBrokerage,
                                        availableFrom = listingAvailableFrom,
                                        noticePeriod = listingNoticePeriod,
                                        address = listingAddress,
                                        city = listingCity,
                                        state = listingState,
                                        pincode = listingPincode,
                                        landmarks = listingLandmarks,
                                        views = (100..500).random()
                                    )
                                    ListingRepository.addListing(newListing)
                                    currentScreen = Screen.ListingPublished
                                }
                            },
                            propertyTitle = listingTitle.ifEmpty { "Modern Downtown Apartment" },
                            address = listingAddress.ifEmpty { "123 Main St" },
                            city = listingCity.ifEmpty { "San Francisco" },
                            state = listingState.ifEmpty { "CA" },
                            monthlyRent = listingMonthlyRent.ifEmpty { "1,200" },
                            bhkType = listingBHK.ifEmpty { "2BHK" },
                            bathrooms = listingBathrooms.ifEmpty { "1" },
                            squareFeet = listingSquareFeet.ifEmpty { "850" },
                            listingId = editingListingId,
                            isEditing = editingListingId != null
                        )
                    }
                    Screen.ListingPublished -> {
                        ListingPublishedScreen(
                            onViewListingClick = { currentScreen = Screen.ManageListings },
                            onBackToHomeClick = { currentScreen = Screen.ListerHome }
                        )
                    }
                    
                    // Lister Management Screens
                    Screen.ManageListings -> {
                        ManageListingsScreen(
                            onBackClick = { currentScreen = Screen.ListerHome },
                            onAddListingClick = { 
                                clearListingData()
                                currentScreen = Screen.PropertyDetails 
                            },
                            onEditListingClick = { listingId ->
                                editingListingId = listingId
                                existingListing = ListingRepository.getListingById(listingId)
                                currentScreen = Screen.PropertyDetails
                            },
                            onViewDetailsClick = { listingId ->
                                currentScreen = Screen.ViewListingDetails(listingId)
                            }
                        )
                    }
                    is Screen.ViewListingDetails -> {
                        ViewListingDetailsScreen(
                            listingId = screen.listingId,
                            onBackClick = { currentScreen = Screen.ManageListings },
                            onEditClick = { listingId ->
                                editingListingId = listingId
                                existingListing = ListingRepository.getListingById(listingId)
                                currentScreen = Screen.PropertyDetails
                            }
                        )
                    }
                    Screen.ReviewApplicants -> {
                        ReviewApplicantsScreen(
                            onBackClick = { currentScreen = Screen.ListerHome }
                        )
                    }
                    Screen.Notifications -> {
                        NotificationsScreen(
                            onBackClick = { currentScreen = Screen.ListerHome }
                        )
                    }

                    // Roommate Finder Flow
                    Screen.RoommateSearch -> {
                        RoommateSearchScreen(
                            onBackClick = { currentScreen = Screen.RenterHome },
                            onStartQuizClick = { currentScreen = Screen.CompatibilityQuizIntro },
                            onSavedProfilesClick = { /* TODO */ }
                        )
                    }
                    Screen.CompatibilityQuizIntro -> {
                        CompatibilityQuizIntroScreen(
                            onBackClick = { currentScreen = Screen.RoommateSearch },
                            onStartQuizClick = { currentScreen = Screen.CompatibilityQuiz }
                        )
                    }
                    Screen.CompatibilityQuiz -> {
                        CompatibilityQuizScreen(
                            onBackClick = { currentScreen = Screen.CompatibilityQuizIntro },
                            onQuizComplete = { currentScreen = Screen.MatchResults }
                        )
                    }
                    Screen.MatchResults -> {
                        MatchResultsScreen(
                            onBackClick = { currentScreen = Screen.RoommateSearch },
                            onCompareClick = { currentScreen = Screen.CompareRoommates },
                            onMessageClick = { name -> currentScreen = Screen.Chat(name) }
                        )
                    }
                    Screen.CompareRoommates -> {
                        CompareRoommatesScreen(
                            onBackClick = { currentScreen = Screen.MatchResults }
                        )
                    }

                    // Renter Profile Flow
                    Screen.RenterProfileSetupIntro -> {
                        RenterProfileSetupIntroScreen(
                            onContinueClick = { currentScreen = Screen.RenterPersonalInfo }
                        )
                    }
                    Screen.RenterPersonalInfo -> {
                        RenterPersonalInfoScreen(
                            onBackClick = { currentScreen = Screen.RenterProfileSetupIntro },
                            onContinueClick = { name, age, gender, occupation, location, phone ->
                                renterName = name
                                renterAge = age
                                renterGender = gender
                                renterOccupation = occupation
                                renterLocation = location
                                renterPhone = phone
                                currentScreen = Screen.RenterHome
                            }
                        )
                    }
                    Screen.RenterProfileDashboard -> {
                        RenterProfileDashboardScreen(
                            name = renterName,
                            email = renterEmail,
                            onBackClick = { currentScreen = Screen.RenterHome },
                            onEditProfileClick = { currentScreen = Screen.RenterEditProfile },
                            onLogoutClick = { currentScreen = Screen.UserSelection }
                        )
                    }
                    Screen.RenterEditProfile -> {
                        RenterEditProfileScreen(
                            name = renterName,
                            email = renterEmail,
                            phone = renterPhone,
                            occupation = renterOccupation,
                            bio = renterBio,
                            onBackClick = { currentScreen = Screen.RenterProfileDashboard },
                            onSaveClick = { name, email, phone, occupation, bio ->
                                renterName = name
                                renterEmail = email
                                renterPhone = phone
                                renterOccupation = occupation
                                renterBio = bio
                                currentScreen = Screen.RenterProfileDashboard
                            }
                        )
                    }
                }
            }
        }
    }

    private fun startRazorpayPayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_SLc8R4COIuIdDY")
        try {
            val options = JSONObject()
            options.put("name", "Rentmate")
            options.put("description", "Premium Subscription")
            options.put("currency", "INR")
            options.put("amount", "10000") // 100 INR = 10000 paise
            options.put("theme.color", "#6C5CE7")
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRazorpayPaymentForListing(listing: Listing) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_SLc8R4COIuIdDY")
        try {
            val options = JSONObject()
            options.put("name", "Rentmate")
            options.put("description", "Booking for ${listing.title}")
            options.put("currency", "INR")
            
            // Convert monthlyRent to paise (multiply by 100)
            val rentAmount = listing.monthlyRent.toIntOrNull() ?: 0
            val amountInPaise = rentAmount * 100
            
            options.put("amount", amountInPaise.toString()) 
            options.put("theme.color", "#009688")
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(s: String?, paymentData: PaymentData?) {
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        onPaymentSuccessCallback?.invoke()
    }

    override fun onPaymentError(errorCode: Int, errorMessage: String?, paymentData: PaymentData?) {
        Toast.makeText(this, "Payment Failed: $errorMessage", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RentmateTheme {
        Greeting("Android")
    }
}