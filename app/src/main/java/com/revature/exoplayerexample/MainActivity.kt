package com.revature.exoplayerexample

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.revature.exoplayerexample.ui.theme.ExoPlayerExampleTheme
import com.revature.exoplayerexample.ui.theme.Purple500

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExoPlayerExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExoPlayerComposable()
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun ExoPlayerComposable(){

    val context = LocalContext.current
    val mediaURL = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

    Scaffold(
        content = {

              Column(
                  modifier = Modifier.fillMaxSize()) {

                  Column(
                      modifier = Modifier
                          .fillMaxWidth()
                          .height(50.dp)
                          .background(color = Purple500),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center) {

                      Text(
                          text = "My Media Player",
                          fontWeight = FontWeight.Bold,
                          fontSize = 20.sp,
                          color = Color.White
                      )
                  }

//                  val exoPlayer = remember(context){
//
//                      ExoPlayer.Builder(context).build().apply {
//
//                          //User Agent - the client application being used
//                          val dataSourceFactory:DataSource.Factory = DefaultDataSourceFactory(
//                              context,
//                              Util.getUserAgent(context,context.packageName)
//                          )
//                          val source = ProgressiveMediaSource.Factory(dataSourceFactory)
//                              .createMediaSource(MediaItem.fromUri(mediaURL))
//                          //this.prepare(source)
//                          playWhenReady = true
//
//                          this.setMediaSource(source)
//                          this.prepare()
//                      }
//                  }
                  val player = ExoPlayer.Builder(context).build()
                  val playerView = StyledPlayerView(context)
                  val mediaItem = MediaItem.fromUri(mediaURL)
                  val playWhenReady by rememberSaveable { mutableStateOf(true) }
                  player.setMediaItem(mediaItem)
                  playerView.player = player
                  LaunchedEffect(player) {
                      player.prepare()
                      player.playWhenReady = playWhenReady
                  }



                  Column(
                      modifier = Modifier
                          .fillMaxWidth(),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center) {


                      AndroidView(factory = { playerView })

                  }

              }
        },
        topBar = { TopAppBar(title = {Text("Exo Player")})}

    )
}
