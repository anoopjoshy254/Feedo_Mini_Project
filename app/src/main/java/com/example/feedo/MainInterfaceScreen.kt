import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.feedo.R


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
        TopSection(userName = userName, phoneNumber = phoneNumber)

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
                Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(phoneNumber, fontSize = 16.sp, color = Color.Gray)
            }
            Button(
                onClick = { /* Report a complaint */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Report a complaint!", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // System Overview
        Card(
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
                    Text("Your Systems:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Model No:13323", fontSize = 16.sp, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("System Count:1", fontSize = 16.sp, color = Color.Black)
                    TextButton(onClick = { /* Know more */ }) {
                        Text("Know More", color = Color.Blue)
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
fun FeatureIcon(name: String, icon: androidx.compose.ui.graphics.painter.Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFF1E90FF), shape = RoundedCornerShape(8.dp))
        ) {
            Icon(
                painter = icon,
                contentDescription = name,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(name, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun FoodLevelIndicator() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Food Level", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
            Text("0kg", fontSize = 12.sp, color = Color.Gray)
            Text("15kg", fontSize = 12.sp, color = Color.Gray)
            Text("30kg", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun NavigationBar() {
    BottomAppBar(backgroundColor = Color.Black) {
        IconButton(onClick = { /* Navigate to Home */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                tint = Color.White
            )
        }
        IconButton(onClick = { /* Navigate to Notifications */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "Notifications",
                tint = Color.White)
        }
        IconButton(onClick = { /* Navigate to Contact */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Phone",
                tint = Color.White)
        }
    }
}
