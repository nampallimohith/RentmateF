package com.saveetha.rentmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    recipientName: String, // "Alex Chen" or "Rajesh Kumar"
    onBackClick: () -> Unit
) {
    val tealColor = Color(0xFF009688)
    var messageText by remember { mutableStateOf("") }
    
    // Sample Messages
    val messages = remember { mutableStateListOf(
        ChatMessage("Hi! I saw your profile and we seem like a great match!", false, "10:30 AM"),
        ChatMessage("Hey! Thanks for reaching out. I agree, our lifestyles seem compatible.", true, "10:32 AM"),
        ChatMessage("Would you like to schedule a time to meet and discuss the apartment?", false, "10:35 AM"),
        ChatMessage("That sounds great! I'm free this weekend. What works for you?", true, "10:37 AM")
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        ) {
                            // Placeholder image
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = recipientName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Active now", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Video Call */ }) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = Color.DarkGray)
                    }
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.DarkGray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Attach */ }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = Color.Gray)
                    }
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Type a message...") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                messages.add(ChatMessage(messageText, true, "Now"))
                                messageText = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(tealColor, RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class ChatMessage(val content: String, val isMe: Boolean, val time: String)

@Composable
fun ChatBubble(message: ChatMessage) {
    val tealColor = Color(0xFF009688)
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = if (message.isMe) 
                RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp) 
            else 
                RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
            color = if (message.isMe) tealColor else Color.White,
            border = if (!message.isMe) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)) else null,
            shadowElevation = if (!message.isMe) 2.dp else 0.dp
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(16.dp),
                color = if (message.isMe) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = message.time,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
