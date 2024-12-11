package id.handlips.views.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.handlips.component.card.CardComponent
import id.handlips.data.model.DataHistory
import id.handlips.ui.theme.White
import id.handlips.utils.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    var histories by remember { mutableStateOf<List<DataHistory>>(emptyList()) }
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
            items(10) {
                CardComponent(
                    title = "Judul",
                    sumChat = "20",
                    date = formatDate("2024-11-29T11:47:48.000Z"),
                    onClick = { null },
                )
            }
            items(histories) { history ->
                CardComponent(
                    title = history.title.trim(),
                    sumChat = history.message?.size.toString(),
                    date = formatDate(history.message.last()?.createdAt ?: "Now"),
                    onClick = { null },
                )
            }
        }
    }
}

@Preview
@Composable
private fun HistoryPreview() {
    HistoryScreen(
        onClickBack = {}
    )
}
