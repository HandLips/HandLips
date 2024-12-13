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
fun DetailHistoryScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    // TODO: Model class for chat (message) in History Response
    var chats by remember { mutableStateOf<List<String>>(emptyList()) }
    val state = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
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
            items(dummyChats) { chat ->
                ChatBubbleComponent(
                    message = chat.message,
                    time = chat.time,
                    isFromMe = chat.isFromMe,
                )
            }
        }
    }
}


data class ChatMessage(
    val message: String,
    val time: String,
    val isFromMe: Boolean
)


val dummyChats = listOf(
    ChatMessage(
        message = "Halo! Apa kabar?",
        time = "09:00",
        isFromMe = true
    ),
    ChatMessage(
        message = "Hai! Alhamdulillah baik, kamu gimana?",
        time = "09:02",
        isFromMe = false
    ),
    ChatMessage(
        message = "Baik juga! Lagi ngerjain fitur baru nih",
        time = "09:03",
        isFromMe = true
    ),
    ChatMessage(
        message = "Wah, fitur apa tuh? Kepo nih 😄",
        time = "09:05",
        isFromMe = false
    ),
    ChatMessage(
        message = "Interface chat gitu, ada animasinya juga lho",
        time = "09:06",
        isFromMe = true
    ),
    ChatMessage(
        message = "Keren! Nanti share ya kalau udah jadi 😊",
        time = "09:08",
        isFromMe = false
    ),
    ChatMessage(
        message = "Siap! Tinggal benerin UI dikit lagi kok",
        time = "09:10",
        isFromMe = true
    ),
    ChatMessage(
        message = "Santai aja, btw kalau butuh bantuan testing bilang ya",
        time = "09:12",
        isFromMe = false
    ),
    ChatMessage(
        message = "Oke, makasih banyak ya! 🙏",
        time = "09:13",
        isFromMe = true
    ),
    ChatMessage(
        message = "Sama-sama kak! 👍",
        time = "09:15",
        isFromMe = false
    )
)

@Preview
@Composable
private fun DetailHistoryPreview() {
//    DetailHistoryScreen()
}
