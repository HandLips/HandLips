package id.handlips.views.menu_home.gemini

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.R
import id.handlips.component.bubble_chat.ChatBubbleComponent
import id.handlips.component.dialog.DialogError
import id.handlips.component.loading.LoadingAnimation
import id.handlips.data.model.GeminiResponse
import id.handlips.data.model.MessageGemini
import id.handlips.data.model.Topic
import id.handlips.ui.theme.poppins
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.toList

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeminiScreen(
    modifier: Modifier = Modifier,
    viewModel: GeminiViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                val data = (uiState as UiState.Success<GeminiResponse>).data
                val chat = MessageGemini(text = data.response, isFromMe = false, time = "Now")
                viewModel.addMessage(chat)
                showDialogSuccess = true
            }

            is UiState.Error -> {
                errorMessage = (uiState as UiState.Error).message
                showDialogError = true
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onClickBack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back_icon)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.chat_bot),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            Log.d("GeminiScreen", "Search query updated: $query")
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(stringResource(R.string.masukan_topik)) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    IconButton(
                        onClick = {
                            if (searchQuery.isNotBlank()) {
                                val topic = Topic(topic = searchQuery)
                                Log.d("GeminiScreen", "Sending message with topic: $searchQuery")
                                viewModel.addMessage(
                                    MessageGemini(
                                        text = searchQuery,
                                        isFromMe = true,
                                        time = "Now"
                                    )
                                )
                                viewModel.geminiResult(topic)
                                searchQuery = ""
                            } else {
                                Log.d("GeminiScreen", "Search query is blank, not sending")
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(messages) { message ->
                    ChatBubbleComponent(
                        isFromMe = message.isFromMe,
                        message = message.text,
                        time = message.time,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                }
            }

            if (uiState is UiState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }
            // Error Dialog
            if (showDialogError) {
                DialogError(
                    onDismissRequest = { showDialogError = false },
                    textError = errorMessage
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
