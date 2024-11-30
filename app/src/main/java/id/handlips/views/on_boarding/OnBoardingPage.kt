package id.handlips.views.on_boarding

import androidx.annotation.DrawableRes
import id.handlips.R

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
//    object First : OnboardingPage(
//        image = R.drawable.ic_onboarding_1,
//        title = "Pantau Penggunaan Air",
//        description = "Lihat infografis, bandingkan penggunaan air bulan lalu, dan hemat uang."
//    )
//
//    object Second : OnboardingPage(
//        image = R.drawable.ic_onboarding_2,
//        title = "Jelajahi Dunia Hadiah Kami",
//        description = "Pelajari tentang halaman reward, penuhi tujuan, dan klaim reward eksklusif"
//    )
//
//    object Third : OnboardingPage(
//        image = R.drawable.ic_onboarding_3,
//        title = "Donasi dengan Makna",
//        description = "Bersama-sama, mari berbuat baik dan memberikan dampak positif."
//    )
}

//val welcomePages = listOf(
//    OnboardingPage.First,
//    OnboardingPage.Second,
//    OnboardingPage.Third,
//)