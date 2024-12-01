package id.handlips.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.ui.theme.poppins

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String,
    val color: Color,
    val titleStyle: TextStyle,
    val descriptionStyle: TextStyle
) {
    object First : OnboardingPage(
        image = R.drawable.ic_onboarding1,
        title = "Membantu Teman Tuli dan Bisu",
        description = "Handlips hadir untuk membantu teman tuli dan teman bisu untuk memiliki kesempatan yang sama dalam berkomunikasi dengan sesama.",
        color = Color(red = 168, green = 167, blue = 255),
        titleStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 22.sp
        ),
        descriptionStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontSize = 12.sp
        )
    )

    object Second : OnboardingPage(
        image = R.drawable.ic_onboarding2,
        title = "Penerjemahan Yang Praktis",
        description = "Handlips mampu menerjemahkan Bahasa isyarat ke text dan audio ke text dengan mudah dan cepat.",
        color = Color(red = 255, green = 212, blue = 251),
        titleStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            color = Color(red = 56, green = 90, blue = 100),
            fontSize = 22.sp
        ),
        descriptionStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Color(red = 56, green = 90, blue = 100),
            fontSize = 12.sp
        )
    )

    object Third : OnboardingPage(
        image = R.drawable.ic_onboarding3,
        title = "Berkomunikasi Menjadi Lebih Mudah",
        description = "Berkomunikasi tidak akan menjadi tantangan lagi bagi para teman tuli dan bisu karena sekarang ada Handlips.",
        color = Color(
            red = 210, green = 104, blue = 204,
        ),
        titleStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            color = Color.White,
                    fontSize = 22.sp
        ),
        descriptionStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontSize = 12.sp
        )
    )
}

val welcomePages = listOf(
    OnboardingPage.First,
    OnboardingPage.Second,
    OnboardingPage.Third,
)