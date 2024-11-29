@file:OptIn(ExperimentalMaterial3Api::class)

package id.handlips.views.menu_home.gemini

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.ktx.Firebase
import id.handlips.R
import id.handlips.component.bubble_chat.ChatBubbleComponent
import id.handlips.data.model.MessageGemini
import id.handlips.ui.theme.poppins

@Composable
fun GeminiScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.masukan_topik)) },
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
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
    }
}

