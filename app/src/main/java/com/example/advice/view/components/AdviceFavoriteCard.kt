package com.example.advice.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advice.model.room.advice.AdviceEntity
import com.example.advice.ui.theme.Blue
import com.example.advice.ui.theme.Blue1


@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    favoriteText: String,
    adviceEntity: AdviceEntity,
    isScrolling: Boolean=true,

    ) {
    var expanded by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(Blue, Blue, Blue1))

    LaunchedEffect(isScrolling) {
        if (isScrolling) {
            expanded = false
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(
            topStart = 30.dp, topEnd = 8.dp,
            bottomStart = 8.dp, bottomEnd = 30.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(brush = gradient)
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = if (expanded) adviceEntity.advice else adviceEntity.advice.take(20) + "...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                letterSpacing = 2.sp,
            )
        }
    }
}
