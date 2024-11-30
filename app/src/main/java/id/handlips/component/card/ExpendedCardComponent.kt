package id.handlips.component.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import id.handlips.ui.theme.Shapes
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins
import kotlinx.coroutines.delay

@Composable
fun ExpendedCardComponent(
    modifier: Modifier = Modifier,
    title: String,
    compose: @Composable () -> Unit
) {
    var expended by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expended) 180f else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = Shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = { expended = !expended }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().background(White)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins
                )
            )
            IconButton(
                onClick = { expended = !expended },
                modifier = Modifier
                    .alpha(ContentAlpha.medium)
                    .rotate(rotationState)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrow down"
                )
            }
        }
        if (expended) {
            compose()
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ExpendedCardComponentPreview() {
    ExpendedCardComponent(title = "Title",
        compose = {
            Text(
                text = "Content goes here",
                modifier = Modifier.padding(16.dp)
            )
        }
    )
}