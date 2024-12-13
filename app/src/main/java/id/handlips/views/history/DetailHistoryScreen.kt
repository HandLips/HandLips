package id.handlips.views.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.handlips.component.bubble_chat.ChatBubbleComponent
import id.handlips.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHistoryScreen(modifier: Modifier = Modifier) {
    // TODO: Model class for chat (message) in History Response
    var chats by remember { mutableStateOf<List<String>>(emptyList()) }
    val state = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton({ null }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description",
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = White,
                        titleContentColor = Color.Black,
                    ),
                title = {
                    Text("History")
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            state = state,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
        ) {
            // Preview
            items(10) { index ->
                ChatBubbleComponent(
                    message = "Test chat",
                    time = "now",
                    isFromMe = index % 2 == 0,
                )
            }

            items(chats) { chat ->
                ChatBubbleComponent(
                    message = chat,
                    time = "TODO",
                    isFromMe = false,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailHistoryPreview() {
    DetailHistoryScreen()
}
