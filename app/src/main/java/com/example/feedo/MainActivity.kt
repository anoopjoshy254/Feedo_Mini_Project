package com.example.feedo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedo.ui.theme.FeedoTheme


// Data class to store user details
data class User(val email: String, val password: String)

// List to store multiple users' data
private val usersList = mutableListOf<User>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { FeedoScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("sign_up") { SignUpScreen(navController) }
                    composable("sign_up_success") { SignUpSuccessScreen(navController) }
                    composable("main_interface") { MainInterfaceScreen(
                        navController) }
                }
            }
        }
    }
}

@Composable
fun FeedoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FEEDO",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "Login with Mail", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("sign_up") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "Sign Up", color = Color.Black, fontSize = 16.sp)
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginError = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = loginError.value, color = Color.Red, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                if (email.value.isBlank() || password.value.isBlank() ) {
                    loginError.value = "All fields must be filled"
                } else {
                    //loginError.value = "Invalid Credentials"
                    signinUser(email.value, password.value,)
                    navController.navigate("main_interface")

                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "Login", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SignUpScreen(navController: NavHostController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val mobile = remember { mutableStateOf("") }
    val signUpError = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign Up",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mobile.value,
            onValueChange = { mobile.value = it },
            label = { Text("Mobile no") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = signUpError.value, color = Color.Red, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank() || mobile.value.isBlank()) {
                    signUpError.value = "All fields must be filled"
                } else {
//                    usersList.add(User(email.value, password.value))
//                    signUpError.value = ""
                    signupUser(name.value, password.value, email.value, mobile.value)
                    navController.navigate("sign_up_success")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "Sign Up", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SignUpSuccessScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You are successfully signed up!\nNow press back and login.",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("home") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "Back to Home", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun MainInterfaceScreen(
    navController: NavHostController,
    userName: String,
    phoneNumber: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Section: User Profile and System Overview
        TopSection(userName = userName, phoneNumber = phoneNumber )

        Spacer(modifier = Modifier.height(16.dp))

        // Main Section: Icons for Features
        MainFeaturesSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Section: Food Level Indicator
        FoodLevelIndicator()

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Bar
        NavigationBar()
    }
}

@Composable
fun TopSection(userName: String, phoneNumber: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // User Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                androidx.compose.material.Text(
                    userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                androidx.compose.material.Text(phoneNumber, fontSize = 16.sp, color = Color.Gray)
            }
            androidx.compose.material.Button(
                onClick = { /* Report a complaint */ },
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                shape = RoundedCornerShape(8.dp)
            ) {
                androidx.compose.material.Text("Report a complaint!", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // System Overview
        androidx.compose.material.Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFEAF6FF),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    androidx.compose.material.Text(
                        "Your Systems:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    androidx.compose.material.Text(
                        "Model No:13323",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    androidx.compose.material.Text(
                        "System Count:1",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    androidx.compose.material.TextButton(onClick = { /* Know more */ }) {
                        androidx.compose.material.Text("Know More", color = Color.Blue)
                    }
                }
            }
        }
    }
}

@Composable
fun MainFeaturesSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FeatureIcon("Scheduled Feeding", painterResource(id = R.drawable.ic_clock))
            FeatureIcon("Manual Feeding", painterResource(id = R.drawable.ic_manual))
            FeatureIcon("Feeding History", painterResource(id = R.drawable.ic_history))
            FeatureIcon("Water PH Level", painterResource(id = R.drawable.ic_ph))
        }
    }
}

@Composable
fun FeatureIcon(name: String, icon: Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFF1E90FF), shape = RoundedCornerShape(8.dp))
        ) {
            androidx.compose.material.Icon(
                painter = icon,
                contentDescription = name,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material.Text(name, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun FoodLevelIndicator() {
    Column(modifier = Modifier.fillMaxWidth()) {
        androidx.compose.material.Text(
            "Food Level",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp) // Adjust based on the food level (e.g., 15kg)
                    .background(Color.Green, shape = RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text("0kg", fontSize = 12.sp, color = Color.Gray)
            androidx.compose.material.Text("15kg", fontSize = 12.sp, color = Color.Gray)
            androidx.compose.material.Text("30kg", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun NavigationBar() {
    androidx.compose.material.BottomAppBar(backgroundColor = Color.Black) {
        androidx.compose.material.IconButton(onClick = { /* Navigate to Home */ }) {
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                tint = Color.White
            )
        }
        androidx.compose.material.IconButton(onClick = { /* Navigate to Notifications */ }) {
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "Notifications",
                tint = Color.White
            )
        }
        androidx.compose.material.IconButton(onClick = { /* Navigate to Contact */ }) {
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Phone",
                tint = Color.White
            )
        }
    }
}

