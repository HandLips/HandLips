package id.handlips.views.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import id.handlips.R
import id.handlips.component.card.CardComponent
import id.handlips.component.dialog.DialogError
import id.handlips.component.loading.LoadingAnimation
import id.handlips.data.model.DataHistory
import id.handlips.data.model.DataProfile
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins
import id.handlips.utils.Resource
import id.handlips.utils.formatDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickSubscripe: () -> Unit,
    onClickEvent: () -> Unit
) {
    val getEmail = viewModel.getCurrent()?.email.toString()
    val getDisplayName = viewModel.getCurrent()?.displayName.toString()
    val getPhotoUrl = viewModel.getCurrent()?.photoUrl.toString()
    var profile by remember { mutableStateOf<DataProfile?>(null) }
    var historyItems by remember { mutableStateOf<List<DataHistory>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var dialogError by remember { mutableStateOf(false) }
    var textError by remember { mutableStateOf("") }
    val name = if (getDisplayName.isNotBlank()) {getDisplayName} else if(profile?.name?.isNotBlank()!!){ profile?.name } else stringResource(R.string.guest)
    LaunchedEffect(Unit) {
//        viewModel.getHistory().observeForever { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//                    Log.d("HomeScreen", "Fetching history: Loading...")
//                    loading = true
//                }
//
//                is Resource.Success -> {
//                    loading = false
//                    historyItems = resource.data.data
//                    Log.d("HomeScreen", "History fetched successfully: ${historyItems.size} items")
//                }
//
//                is Resource.Error -> {
//                    loading = false
//                    dialogError = true
//                    textError = resource.message
//                    Log.e("HomeScreen", "Error fetching history: ${resource.message}")
//                }
//            }
//        }

        viewModel.getProfile(getEmail).observeForever { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("HomeScreen", "Fetching profile: Loading...")
                    loading = true
                }

                is Resource.Success -> {
                    loading = false
                    profile = resource.data.data
                    Log.d("HomeScreen", "Profile fetched successfully: ${profile?.name}")
                }
                is Resource.Error -> {
                    loading = false
                }
            }
        }
    }

    if (dialogError) {
        DialogError(
            onDismissRequest = { dialogError = false },
            textError = textError
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Blue)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.hai_user,
                            name!!
                        ),
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    AsyncImage(
                        model = getPhotoUrl,
                        contentDescription = stringResource(R.string.logo),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(45.dp)
                    )
                }
            }

            // Card Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-70).dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(top = 25.dp, start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = R.mipmap.ic_logo_handlips_round, // URL atau resource drawable
                                contentDescription = stringResource(R.string.logo),
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .size(80.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(R.string.handlips),
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontFamily = poppins,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = stringResource(R.string.berkomunikasi_menjadi_lebih_mudah_dan_kami_berkomitmen_mendukung_aktivitas_sehari_hari_anda),
                                    modifier = Modifier.padding(start = 16.dp),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        letterSpacing = 0.1.sp,
                                        lineHeight = 12.sp,
                                        color = Color.Gray,
                                    ),
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                    }
                    Spacer(Modifier.padding(bottom = 15.dp))
                    MenuSection(onClickSubscripe = onClickSubscripe, onClickEvent = onClickEvent)
                    Spacer(Modifier.padding(bottom = 15.dp))
                }
            }

            // Main Content
            Column(
                modifier = Modifier
                    .padding(horizontal = 26.dp)
                    .offset(y = (-50).dp)
            ) {
                Text(
                    text = stringResource(R.string.history_terbaru),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    if (historyItems.isNotEmpty()) {
                        historyItems.forEach { historyItem ->
                            CardComponent(
                                title = historyItem.title.trim(),
                                sumChat = historyItem.message?.size.toString(),
                                date = formatDate(historyItem.createdAt),
                                onClick = { Log.d("HomeScreen", "Clicked on history item: ${historyItem.title}") }
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(25.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.there_is_no_history),
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation(
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun MenuSection(onClickSubscripe: () -> Unit, onClickEvent: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_calender,
            title = stringResource(R.string.eventicle),
            onClick = onClickEvent
        )

        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_history,
            title = stringResource(R.string.history)
        ) {
            // Handle click event
        }

        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_payment,
            title = stringResource(R.string.langganan),
            onClick = {
                onClickSubscripe()
            }
        )
    }
}

@Composable
fun CardMenu(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Blue)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id),
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = White
            )

            Spacer(Modifier.padding(2.dp))
            Text(
                text = title,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.padding(4.dp))
        }
    }
}


