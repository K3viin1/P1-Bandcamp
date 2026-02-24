package com.example.bandcamp_parte_1

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("TITLE") ?: "Unknown Title"
        val artist = intent.getStringExtra("ARTIST") ?: "Unknown Artist"
        val imageUrl = intent.getStringExtra("IMAGE") ?: ""
        val audioRes = intent.getIntExtra("AUDIO_RES", 0)

        val names = intent.getStringArrayListExtra("TRACK_NAMES") ?: arrayListOf()
        val durations = intent.getStringArrayListExtra("TRACK_DURATIONS") ?: arrayListOf()
        val images = intent.getStringArrayListExtra("TRACK_IMAGES") ?: arrayListOf()
        val audios = intent.getIntegerArrayListExtra("TRACK_AUDIOS") ?: arrayListOf()

        val tracks = names.indices.map { i ->
            Track(
                name = names[i],
                duration = durations.getOrElse(i) { "--:--" },
                imageUrl = images.getOrElse(i) { imageUrl },
                audioRes = audios.getOrElse(i) { 0 }
            )
        }

        setContent {
            DetailScreen(
                title = title,
                artist = artist,
                imageUrl = imageUrl,
                tracks = tracks,
                audioRes = audioRes,
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    artist: String,
    imageUrl: String,
    tracks: List<Track>,
    audioRes: Int,
    onBack: () -> Unit
) {
    val gradientBackground = Brush.linearGradient(
        colors = listOf(RosaFucsia, AzulCeleste),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    val context = LocalContext.current
    var playingIndex by remember { mutableIntStateOf(-1) }
    var isPlaying by remember { mutableStateOf(false) }
    val mp = remember { MediaPlayer() }

    DisposableEffect(Unit) {
        onDispose { mp.release() }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Disc Detail",
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF000000)
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(brush = gradientBackground)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(300.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = artist,
                fontSize = 20.sp,
                color = Color.Yellow,
                modifier = Modifier.padding(bottom = 14.dp),
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tracks:", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))

                    if (tracks.isEmpty()) {
                        Text("1. $title - Original Mix", color = Color.White)
                        Text("2. Instrumental Version", color = Color.White)
                    } else {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 260.dp)
                        ) {
                            itemsIndexed(tracks) { i, t ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SubcomposeAsyncImage(
                                        model = t.imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(Modifier.width(10.dp))

                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            t.name,
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            t.duration,
                                            color = Color.White.copy(alpha = 0.75f),
                                            fontSize = 12.sp
                                        )
                                    }

                                    IconButton(onClick = {
                                        if (t.audioRes == 0) return@IconButton
                                        val afd = context.resources.openRawResourceFd(t.audioRes)

                                        if (playingIndex == i && isPlaying) {
                                            mp.pause()
                                            isPlaying = false
                                        } else {
                                            try {
                                                mp.reset()
                                                val afd = context.resources.openRawResourceFd(audioRes)
                                                mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                                afd.close()
                                                mp.prepare()
                                                mp.start()

                                                playingIndex = i
                                                isPlaying = true

                                                mp.setOnCompletionListener {
                                                    isPlaying = false
                                                    playingIndex = -1
                                                }
                                            } catch (_: Exception) {
                                                isPlaying = false
                                                playingIndex = -1
                                            }
                                        }
                                    }) {
                                        Icon(
                                            imageVector = if (playingIndex == i && isPlaying)
                                                Icons.Default.Pause
                                            else
                                                Icons.Default.PlayArrow,
                                            contentDescription = "Play/Pause",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
