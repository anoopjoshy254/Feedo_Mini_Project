package com.example.feedo

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star


@Composable
fun ReportComplaintScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("Select Category") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var feedbackText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    val complaintId = remember { Random.nextInt(1000, 9999) } // Generates random ID

    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Report a Complaint") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Complaint ID: #$complaintId", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(10.dp))

            // Dropdown for Complaint Category
            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedCategory)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    listOf("Technical Issue", "Service Issue", "Other").forEach { category ->
                        DropdownMenuItem(onClick = {
                            selectedCategory = category
                            expanded = false
                        }) {
                            Text(category)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Text Field for Description
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Image Upload Button
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Upload Screenshot (Optional)")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Submit Complaint Button
            Button(
                onClick = {
                    if (selectedCategory == "Select Category" || description.isEmpty()) {
                        Toast.makeText(context, "Please complete the form!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Complaint Submitted!", Toast.LENGTH_LONG).show()
                        navController.navigate("home") // Navigate back to home
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Submit Complaint", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Customer Care Contact Section
            Text("Need Help? Contact Customer Care", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+123456789"))
                        context.startActivity(phoneIntent)
                    }
                ) {
                    Text("Call Support")
                }

                Button(
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@feedo.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Customer Support Inquiry")
                        }
                        context.startActivity(emailIntent)
                    }
                ) {
                    Text("Email Support")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Feedback Section
            Text("Rate Your Experience", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Row {
                for (i in 1..5) {
                    IconButton(onClick = { rating = i }) {
                        Icon(
                            imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= rating) Color.Yellow else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            BasicTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.LightGray)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text("Submit Feedback", color = Color.White)
            }
        }
    }
}