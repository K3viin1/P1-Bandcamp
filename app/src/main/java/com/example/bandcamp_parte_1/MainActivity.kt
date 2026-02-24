package com.example.bandcamp_parte_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay

val RosaFucsia = Color(0xFFFF00FF)
val AzulCeleste = Color(0xFF00FFFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BandcampListScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandcampListScreen() {
    val context = LocalContext.current

    val myDiscs = remember {
        listOf(
            Disc(
                title = "Bad Bunny - Mix/Collection",
                artist = "Bad Bunny",
                imageUrl = "https://preview.redd.it/rank-them-the-ultimate-bad-bunny-album-ranking-v0-mze03da4fbbe1.jpeg?width=640&crop=smart&auto=webp&s=9ac07227b5d41ded30d452a4307802afa07431ad",
                audioRes = R.raw.perro_negro,
                tracks = listOf(
                    Track("PERRO NEGRO", "2:42", "https://f4.bcbits.com/img/a1964263741_5.jpg", R.raw.perro_negro),
                    Track("Perreo intenso", "3:12", "https://f4.bcbits.com/img/a4132135123_5.jpg"),
                    Track("Yo no soy Celoso", "2:58", "https://f4.bcbits.com/img/a2586648925_5.jpg"),
                    Track("EeO", "3:31", "https://f4.bcbits.com/img/a1596470058_5.jpg"),
                    Track("Un abar verano in ti", "3:44", "https://f4.bcbits.com/img/a2667418997_5.jpg"),
                    Track("Radical One Presents", "4:05", "https://f4.bcbits.com/img/a2629122652_5.jpg"),
                    Track("on.my.nuevayol", "3:09", "https://f4.bcbits.com/img/a1261522848_5.jpg"),
                    Track("Los conejos Malo mashups", "4:18", "https://f4.bcbits.com/img/a3952886075_5.jpg")
                )
            ),

            Disc(
                title = "Minecraft - Mix/Collection",
                artist = "Daniel Rosenfeld",
                imageUrl = "https://f4.bcbits.com/img/a3390257927_5.jpg",
                audioRes = R.raw.key_minecraft,
                tracks = listOf(
                    Track("Key de Minecraft(C418)", "1:05", "https://f4.bcbits.com/img/a3390257927_5.jpg", R.raw.key_minecraft),
                    Track("Subwoofer Lullaby", "3:29", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Living Mice", "2:58", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Haggstrom", "3:24", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Minecraft", "4:15", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Oxygene", "1:05", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Mice on Venus", "4:42", "https://f4.bcbits.com/img/a3390257927_5.jpg"),
                    Track("Dry Hands", "1:08", "https://f4.bcbits.com/img/a3390257927_5.jpg")
                )
            ),

            Disc("Miss de Lifetime", "Erika de Casier", "https://f4.bcbits.com/img/a3386965825_5.jpg", R.raw.miss),
            Disc("Hold On (The Distance Between)", "The Protomen", "https://f4.bcbits.com/img/a2960923617_5.jpg", R.raw.hold_on),
            Disc("Love Is Not Enough", "Converge", "https://f4.bcbits.com/img/a1291330267_5.jpg", R.raw.perro_negro),
            Disc("Deadstick", "King Gizzard & The Lizard Wizard", "https://f4.bcbits.com/img/a0206419347_5.jpg", R.raw.perro_negro),
            Disc("Dragon", "King Gizzard & The Lizard Wizard", "https://f4.bcbits.com/img/a2805471381_5.jpg", R.raw.perro_negro),
            Disc("Dark Ambient of 2025", "Cryo Chamber", "https://f4.bcbits.com/img/a2942138512_5.jpg", R.raw.perro_negro),
            Disc("Back to the street", "TONY NOSCRIPT", "https://f4.bcbits.com/img/a1247865196_5.jpg", R.raw.perro_negro),
            Disc("Alpha Fluids", "The Ruins Of Beverast", "https://f4.bcbits.com/img/a2961890428_5.jpg", R.raw.perro_negro),
            Disc("Reise", "Ellende", "https://f4.bcbits.com/img/a3766998842_5.jpg", R.raw.perro_negro),
            Disc("The Darkest Path", "ABGLANZ", "https://f4.bcbits.com/img/a0190313790_5.jpg", R.raw.perro_negro),
            Disc("Collapsing Spiritual Nebula", "Ectovoid", "https://f4.bcbits.com/img/a1333707558_5.jpg", R.raw.perro_negro),
            Disc("Unbirthing", "Cynthoni", "https://f4.bcbits.com/img/a1897641613_5.jpg", R.raw.perro_negro),
            Disc("Spoken For", "FLAVOR FOLEY", "https://f4.bcbits.com/img/a2264356507_5.jpg", R.raw.perro_negro),
            Disc("Rising High", "Beyond The Black", "https://f4.bcbits.com/img/a3472209551_5.jpg", R.raw.perro_negro)
        )
    }

    val gradientBackground = Brush.linearGradient(
        colors = listOf(RosaFucsia, AzulCeleste),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Collection", fontWeight = FontWeight.Bold, color = Color.Yellow) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF000000)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(brush = gradientBackground)
        ) {
            itemsIndexed(myDiscs) { index, disc ->
                DiscItem(
                    disc = disc,
                    index = index,
                    onCardClick = {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("TITLE", disc.title)
                            putExtra("ARTIST", disc.artist)
                            putExtra("IMAGE", disc.imageUrl)
                            putExtra("AUDIO_RES", disc.audioRes)

                            putStringArrayListExtra("TRACK_NAMES", ArrayList(disc.tracks.map { it.name }))
                            putStringArrayListExtra("TRACK_DURATIONS", ArrayList(disc.tracks.map { it.duration }))
                            putStringArrayListExtra("TRACK_IMAGES", ArrayList(disc.tracks.map { it.imageUrl }))
                            putIntegerArrayListExtra("TRACK_AUDIOS", ArrayList(disc.tracks.map { it.audioRes }))
                        }
                        context.startActivity(intent)

                    }
                )
            }
        }
    }
}

@Composable
fun DiscItem(
    disc: Disc,
    index: Int,
    onCardClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000),
        label = "alpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.6f,
        animationSpec = tween(800),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        delay(index * 150L)
        isVisible = true
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = disc.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = disc.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = disc.artist,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }
        }
    }
}
