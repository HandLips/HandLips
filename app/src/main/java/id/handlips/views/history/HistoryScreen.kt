package id.handlips.views.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.handlips.component.card.CardComponent
import id.handlips.ui.theme.White
import id.handlips.utils.formatDate
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit, onClickHistoryDetail: () -> Unit) {
    val state = rememberLazyListState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onClickBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "",
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
//        Column(
//            modifier =
//                Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .padding(16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.Start,
//        ) {
//            Text(text = "Welcome to History")
//        }
        LazyColumn(
            state = state,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
        ) {
            // Preview
            items(dummyHistoryItems) { historyItem ->
                CardComponent(
                    title = historyItem.title,
                    sumChat = historyItem.sumChat,
                    date = formatDate(historyItem.date),
                    onClick = onClickHistoryDetail,
                )
            }
        }
    }
}

data class HistoryItem(
    val id: String,
    val title: String,
    val sumChat: String,
    val date: String
)

// Helper function to format date


val dummyHistoryItems = listOf(
    HistoryItem(
        id = "1",
        title = "Konsultasi Kesehatan Mental",
        sumChat = "15",
        date = "2024-11-29T11:47:48.000Z"
    ),
    HistoryItem(
        id = "2",
        title = "Diskusi Masalah Keluarga",
        sumChat = "23",
        date = "2024-11-28T09:30:00.000Z"
    ),
    HistoryItem(
        id = "3",
        title = "Bimbingan Karir",
        sumChat = "18",
        date = "2024-11-27T14:15:22.000Z"
    ),
    HistoryItem(
        id = "4",
        title = "Konseling Pribadi",
        sumChat = "12",
        date = "2024-11-26T16:20:10.000Z"
    ),
    HistoryItem(
        id = "5",
        title = "Masalah Akademik",
        sumChat = "25",
        date = "2024-11-25T10:05:30.000Z"
    ),
    HistoryItem(
        id = "6",
        title = "Konsultasi Relationship",
        sumChat = "30",
        date = "2024-11-24T13:40:15.000Z"
    ),
    HistoryItem(
        id = "7",
        title = "Bimbingan Studi",
        sumChat = "16",
        date = "2024-11-23T15:55:48.000Z"
    ),
    HistoryItem(
        id = "8",
        title = "Diskusi Pengembangan Diri",
        sumChat = "28",
        date = "2024-11-22T08:25:00.000Z"
    ),
    HistoryItem(
        id = "9",
        title = "Konseling Trauma",
        sumChat = "19",
        date = "2024-11-21T11:10:33.000Z"
    ),
    HistoryItem(
        id = "10",
        title = "Manajemen Stress",
        sumChat = "22",
        date = "2024-11-20T17:30:20.000Z"
    )
)
@Preview
@Composable
private fun HistoryPreview() {
//    HistoryScreen(
//        onClickBack = {}
//    )
}
