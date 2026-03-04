package com.saveetha.rentmate

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TealColor = Color(0xFF009688)
private val LightTeal = Color(0xFFE0F2F1)
private val BackgroundColor = Color(0xFFF8F9FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoommateSearchScreen(
    onBackClick: () -> Unit,
    onStartQuizClick: () -> Unit,
    onSavedProfilesClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = false,
                    onClick = onBackClick,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TealColor,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Group, contentDescription = "Roommates") },
                    label = { Text("Roommates") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TealColor,
                        selectedTextColor = TealColor,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Chats") },
                    label = { Text("Chats") },
                    selected = false,
                    onClick = { /* Handle Chats */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TealColor,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { /* Handle Profile */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TealColor,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(TealColor, Color(0xFF00ACC1))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Find Your Perfect\nRoommate",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Take our compatibility quiz to get matched",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }

            // Algorithm Card
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = LightTeal,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = TealColor,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Smart Matching Algorithm",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Answer a few questions about your lifestyle and preferences to find compatible roommates",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onStartQuizClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Text("Start Compatibility Quiz", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Stats Section
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatItem(icon = Icons.Default.People, count = "500+", label = "Active Users", modifier = Modifier.weight(1f))
                StatItem(icon = Icons.Default.Favorite, count = "95%", label = "Match Rate", modifier = Modifier.weight(1f))
                StatItem(icon = Icons.Default.FlashOn, count = "1000+", label = "Connections", modifier = Modifier.weight(1f))
            }

            // Saved Profiles Card
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable(onClick = onSavedProfilesClick),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF5F5F5),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = TealColor)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = "View Saved Profiles", fontWeight = FontWeight.Bold)
                        Text(text = "Check out your saved potential roommates", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, count: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA).copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = TealColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = count, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = label, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityQuizIntroScreen(
    onBackClick: () -> Unit,
    onStartQuizClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Compatibility Quiz", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Surface(
                shape = CircleShape,
                color = LightTeal,
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Assignment,
                        contentDescription = null,
                        tint = TealColor,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Let's Find Your Match",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Answer questions about your lifestyle to find roommates who share your preferences",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(48.dp))
            
            BenefitRow("Quick 5-minute questionnaire")
            BenefitRow("Lifestyle and habit matching")
            BenefitRow("Privacy-focused and secure")
            BenefitRow("Instant compatibility results")
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onStartQuizClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start Quiz", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BenefitRow(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F8)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityQuizScreen(
    onBackClick: () -> Unit,
    onQuizComplete: () -> Unit
) {
    val questions = listOf(
        QuizQuestion(1, "What time do you usually go to bed?", listOf("Early sleeper (9PM-11PM)", "Normal (11PM-1AM)", "Night owl (after 1AM)", "Flexible sleeper")),
        QuizQuestion(2, "What are your alcohol habits?", listOf("Does not drink", "Drinks occasionally", "Drinks regularly", "Alcohol not allowed in house")),
        QuizQuestion(3, "What is your smoking preference?", listOf("Non-smoker", "Smokes outside only", "Smokes inside", "Smoking-friendly home")),
        QuizQuestion(4, "How clean do you keep your space?", listOf("Very tidy", "Moderately clean", "Lived-in", "Relaxed")),
        QuizQuestion(5, "How social are you at home?", listOf("Very social", "Moderately social", "Prefer quiet", "Need alone time")),
        QuizQuestion(6, "How do you feel about pets?", listOf("Love pets", "Okay with pets", "Prefer no pets", "Allergic")),
        QuizQuestion(7, "How often do you have guests over?", listOf("Very often", "Sometimes", "Rarely", "Never"))
    )
    
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    
    val currentQuestion = questions[currentQuestionIndex]
    val progress = (currentQuestionIndex + 1).toFloat() / questions.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Compatibility Quiz", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentQuestionIndex == 0) {
                            onBackClick()
                        } else {
                            currentQuestionIndex--
                            selectedOption = null
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = TealColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = TealColor,
                trackColor = LightTeal
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = currentQuestion.question,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            currentQuestion.options.forEach { option ->
                val isSelected = selectedOption == option
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { 
                            selectedOption = option
                            // Auto-advance with small delay for visual feedback
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) TealColor.copy(alpha = 0.1f) else Color.White
                    ),
                    border = BorderStroke(
                        1.dp, 
                        if (isSelected) TealColor else Color.LightGray.copy(alpha = 0.5f)
                    )
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = option,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) TealColor else Color.Black
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        selectedOption = null
                    } else {
                        onQuizComplete()
                    }
                },
                enabled = selectedOption != null,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TealColor,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (currentQuestionIndex == questions.size - 1) "Finish" else "Next",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

data class RoommateMatch(
    val name: String,
    val age: Int,
    val occupation: String,
    val matchPercentage: Int,
    val tags: List<String>,
    val habits: Map<String, String>,
    val differences: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchResultsScreen(
    onBackClick: () -> Unit,
    onCompareClick: () -> Unit,
    onMessageClick: (String) -> Unit
) {
    val matches = listOf(
        RoommateMatch(
            "Alex Chen", 26, "Software Engineer", 95, 
            listOf("Early Bird", "Very Tidy", "Pet Friendly"),
            mapOf("Drinks" to "Occasionally", "Smoking" to "Non-smoker", "Schedule" to "Early sleeper")
        ),
        RoommateMatch(
            "Maria Garcia", 24, "Graphic Designer", 78, 
            listOf("Night Owl", "Social", "Loves Cooking"),
            mapOf("Drinks" to "Regularly", "Smoking" to "Non-smoker", "Schedule" to "Night owl"),
            listOf("Different sleep schedule", "More social lifestyle")
        ),
        RoommateMatch(
            "James Wilson", 28, "Teacher", 88, 
            listOf("Flexible", "Clean", "Quiet"),
            mapOf("Drinks" to "No alcohol", "Smoking" to "Non-smoker", "Schedule" to "Normal schedule")
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Matches", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
        ) {
            // Success Header
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF00ACC1))
                    .padding(20.dp)
            ) {
                Column {
                    Text("Great News!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "We found 3 compatible roommates based on your lifestyle preferences",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(matches) { match ->
                    MatchCard(match, onMessageClick)
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                OutlinedButton(
                    onClick = onCompareClick,
                    modifier = Modifier.padding(16.dp).fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, TealColor),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TealColor)
                ) {
                    Icon(Icons.Default.CompareArrows, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compare Matches", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MatchCard(match: RoommateMatch, onMessageClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Profile Image Placeholder
                Surface(
                    shape = CircleShape,
                    color = Color.LightGray,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "${match.name}, ${match.age}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = match.occupation, color = Color.Gray, fontSize = 12.sp)
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (match.matchPercentage >= 85) TealColor else Color(0xFFFFA000)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${match.matchPercentage}%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                match.tags.forEach { tag ->
                    Surface(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Lifestyle Habits
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9F9F9), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                LifestyleRow(Icons.Default.LocalBar, match.habits["Drinks"] ?: "")
                LifestyleRow(Icons.Default.SmokeFree, match.habits["Smoking"] ?: "")
                LifestyleRow(Icons.Default.AccessTime, match.habits["Schedule"] ?: "")
            }
            
            if (match.differences.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Lifestyle Differences:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFA000))
                    }
                    match.differences.forEach { diff ->
                        Text("• $diff", fontSize = 11.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 24.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onMessageClick(match.name) },
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ChatBubble, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Message")
                }
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(
                    onClick = { /* Favorite */ },
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun LifestyleRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = TealColor, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareRoommatesScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Roommates", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profil Headers
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Profile 1
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(64.dp)) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Alex Chen", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("95%", color = TealColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                
                // VS
                Box(modifier = Modifier.height(64.dp).padding(top = 20.dp), contentAlignment = Alignment.Center) {
                    Text("VS", color = Color.LightGray, fontWeight = FontWeight.Bold)
                }
                
                // Profile 2
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(64.dp)) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Maria Garcia", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("92%", color = TealColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            CompareSection("Sleep Schedule", "Early Bird", "Night Owl")
            CompareSection("Cleanliness", "Very Tidy", "Tidy")
            CompareSection("Social Level", "Moderate", "Very Social")
            CompareSection("Pets", "Yes", "Yes")
            CompareSection("Smoking", "No", "No")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Button(
                    onClick = { /* View Alex */ },
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Alex", fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { /* View Maria */ },
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Maria", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun CompareSection(label: String, val1: String, val2: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = label, 
            modifier = Modifier.fillMaxWidth(), 
            textAlign = TextAlign.Center, 
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(val1, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 14.sp)
                Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.LightGray))
                Text(val2, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 14.sp)
            }
        }
    }
}
