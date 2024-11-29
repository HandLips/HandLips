package id.handlips.views.on_boarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    pages: List<OnboardingPage>,
    onClickLogin: () -> Unit
) {
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            visible = pagerState.currentPage != pages.size - 1
        ) {
            HorizontalPagerIndicator(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            )
        }

        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState,
            pages = pages
        ) {
            onBoardingViewModel.saveOnBoardingState(completed = true)
            onClickLogin()
        }
    }
}

@Composable
private fun PagerScreen(onBoardingPage: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                painter = painterResource(id = onBoardingPage.image),
                contentDescription = "Pager Image"
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = onBoardingPage.title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = onBoardingPage.description,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    pages: List<OnboardingPage>,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == pages.size - 1
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = White
                )
            ) {
                Text(text = "LANJUTKAN")
            }
        }
    }
}