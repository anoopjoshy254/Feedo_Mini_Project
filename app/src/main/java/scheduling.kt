import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun ScheduledFeedingScreen(navController: NavHostController, context: Context) {
    var schedules by remember { mutableStateOf(mutableListOf<Schedule>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedSchedule by remember { mutableStateOf<Schedule?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Feeding Schedules",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Schedule Cards
        schedules.sortBy { it.time }
        schedules.forEach { schedule ->
            ScheduleCard(schedule = schedule, onEdit = {
                selectedSchedule = schedule
                showDialog = true
            })
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Add Schedule Button
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = {
                selectedSchedule = null
                showDialog = true
            }) {
                Text("+", color = Color.White, fontSize = 24.sp)
            }
        }
    }

    if (showDialog) {
        AddScheduleDialog(context, selectedSchedule) { schedule ->
            schedules.removeAll { it.id == schedule.id }
            schedules.add(schedule)
            showDialog = false
        }
    }
}

@Composable
fun AddScheduleDialog(context: Context, schedule: Schedule?, onScheduleAdded: (Schedule) -> Unit) {
    val calendar = Calendar.getInstance()
    var selectedTime by remember { mutableStateOf(schedule?.time ?: SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)) }
    var weight by remember { mutableStateOf(schedule?.weight?.toString() ?: "") }
    var autoTime by remember { mutableStateOf("") }
    var isManualTime by remember { mutableStateOf(schedule != null) } // Track if manual time is set

    fun calculateAutoTime(weight: Int): String {
        val newTime = Calendar.getInstance().apply { add(Calendar.MINUTE, weight) }
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(newTime.time)
    }

    AlertDialog(
        onDismissRequest = {},
        title = { Text("${if (schedule == null) "Set" else "Edit"} Feeding Schedule") },
        text = {
            Column {
                Button(onClick = {
                    TimePickerDialog(context, { _, hour, minute ->
                        selectedTime = String.format("%02d:%02d", hour, minute)
                        isManualTime = true // Mark that user set time manually
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
                }) {
                    Text("Select Time")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = weight,
                    onValueChange = {
                        weight = it
//                        autoTime = if (it.isNotEmpty()) calculateAutoTime(it.toInt()) else ""
//                        if (!isManualTime) selectedTime = autoTime // Auto update if time wasn't manually set
                    },
                    label = { Text("Enter Weight (kg)") }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text("Auto Time: $autoTime", fontSize = 14.sp, color = Color.Gray)
            }
        },
        confirmButton = {
            Button(onClick = {
                val finalTime = if (isManualTime) selectedTime else autoTime
                val newSchedule = schedule?.copy(time = finalTime, weight = weight.toIntOrNull() ?: 0)
                    ?: Schedule(UUID.randomUUID().toString(), finalTime, weight.toIntOrNull() ?: 0, true)
                onScheduleAdded(newSchedule)
                sendToBackend(newSchedule)
            }) {
                Text("Save")
            }
        }
    )
}

fun secondToTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}


@Composable
fun ScheduleCard(schedule: Schedule, onEdit: () -> Unit) {
    var isEnabled by remember { mutableStateOf(schedule.isEnabled) }
//    val timeRemaining = calculateTimeRemaining(schedule.time)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${schedule.time}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Weight: ${schedule.weight}kg",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Valve Duration: ${secondToTime(schedule.weight*30)}", // Add valve duration here
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Row {
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                        uncheckedThumbColor = Color.Red
                    )
                )
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}

//fun calculateTimeRemaining(time: String): String {
//    return try {
//        val currentTime = Calendar.getInstance()
//        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val targetTime = Calendar.getInstance().apply {
//            time = formatter.parse(time) ?: return "Invalid Time"
//        }
//        val diff = targetTime.timeInMillis - currentTime.timeInMillis
//        if (diff > 0) "in ${TimeUnit.MILLISECONDS.toMinutes(diff)} min" else "Passed"
//    } catch (e: Exception) {
//        "Invalid Time"
//    }
//}

fun sendToBackend(schedule: Schedule) {
    println("Sending schedule to backend: $schedule")
}

data class Schedule(val id: String, val time: String, val weight: Int, val isEnabled: Boolean)