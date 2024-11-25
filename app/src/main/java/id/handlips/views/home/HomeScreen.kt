package id.handlips.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.R
import id.handlips.component.card.CardComponent
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), backLogin: () -> Unit) {
    if (!viewModel.isLoggin()) {
        LaunchedEffect(Unit) {
            backLogin()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                        text = "Hai! Budiono Siregar",
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }
            }

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
                        // Logo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Logo", color = White)
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "HandLips",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontFamily = poppins,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Berkomunikasi menjadi lebih mudah, dan kami berkomitmen mendukung aktivitas sehari-hari Anda.",
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

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    MenuSection()
                    Spacer(Modifier.padding(bottom = 15.dp))
                }
            }

            // Main Card Content
            Column(
                modifier = Modifier
                    .padding(horizontal = 26.dp)
                    .offset(y = (-50).dp)
            ) {
                Text(
                    text = "History terbaru",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    CardComponent(
                        title = "Title",
                        sumChat = "20",
                        date = "12 Mei 2023",
                        onClick = {}
                    )
                    CardComponent(
                        title = "Title",
                        sumChat = "20",
                        date = "12 Mei 2023",
                        onClick = {}
                    )
                    CardComponent(
                        title = "Title",
                        sumChat = "20",
                        date = "12 Mei 2023",
                        onClick = {}
                    )
                    CardComponent(
                        title = "Title",
                        sumChat = "20",
                        date = "12 Mei 2023",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun MenuSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_visibility,
            title = "Event"
        ) {
            // Handle click event
        }

        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_visibility_off,
            title = "History"
        ) {
            // Handle click event
        }

        CardMenu(
            modifier = Modifier.size(100.dp),
            id = R.drawable.ic_arrow_back,
            title = "Langganan"
        ) {
            // Handle click event
        }
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
