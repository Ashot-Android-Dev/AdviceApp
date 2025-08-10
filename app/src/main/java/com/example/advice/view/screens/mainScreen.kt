package com.example.advice.view.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.advice.ui.theme.Blue
import com.example.advice.ui.theme.Toil600
import com.example.advice.R
import com.example.advice.view.navHost.Route


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    textAdvice: String,
    buttonText: String,
    buttonColor: Color? = null,
    textColor: Color? = null,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .background(brush = Brush.linearGradient(listOf(Blue, Toil600))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            contentAlignment = Alignment.Center
        ) {
            Image(painterResource(R.drawable.logo_advice), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize())
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(brush = Brush.linearGradient(listOf(Blue,Toil600))),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = textAdvice,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                color = textColor ?: Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.91f)
                    .padding(bottom = 65.dp),
                onClick = { navController.navigate(Route.AdviceScreen.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor ?: Color.White
                )
            ) {
                Text(
                    text = buttonText,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor ?: Color.Black
                )
            }
        }
    }
}


