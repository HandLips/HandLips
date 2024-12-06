package id.handlips.views.menu_home.gemini

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.component.bubble_chat.ChatBubbleComponent
import id.handlips.component.button.LongButton
import id.handlips.data.model.MessageGemini
import id.handlips.ui.theme.poppins
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeminiScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Format tanggal untuk ditampilkan
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMMM yyyy") }
    val formattedDate = selectedDate.format(dateFormatter)
    val messages = remember {
        listOf(
            MessageGemini("Hello!", "10:00", true),
            MessageGemini("Hi! Ada yang bisa saya bantu?", "10:01", false),
            MessageGemini("Saya ingin bertanya tentang event yang ada di sekitar saya apakah ada acara komunitas yang bisa saya datangi, sherlock tak parani", "10:02", true)
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back_icon)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.event),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Date Picker Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Date Button with formatted date
                    LongButton(
                        onClick = { showDatePicker = true },
                        text = formattedDate
                    )
                }

                // Search Bar Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(stringResource(R.string.masukan_topik)) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Send Button
                    IconButton(
                        onClick = { /* Handle send action */ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { message ->
                ChatBubbleComponent(
                    isFromMe = message.isFromMe,
                    message = message.text,
                    time = message.time
                )
            }
        }

        // Date Picker Dialog
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate
                    .toEpochDay() * 24 * 60 * 60 * 1000
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { milliseconds ->
                                selectedDate = LocalDate.ofEpochDay(milliseconds / (24 * 60 * 60 * 1000))
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun GeminiScreenPreview() {
    GeminiScreen(onClickBack = {})
}