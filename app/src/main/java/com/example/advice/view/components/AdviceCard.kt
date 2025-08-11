package com.example.advice.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun AdviceCard(
    advice: AdviceEntity,
    dataTime: String,
    onclickDelete: (AdviceEntity) -> Unit,
    onclickAddFavorite: (AdviceEntity) -> Unit,

    ) {
    var expanded by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(Blue, Blue, Blue1))
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
                text = dataTime,
                fontSize = 20.sp,
                color = Color.White

            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Text(
                text = if (expanded) advice.advice else advice.advice.take(20) + "...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                letterSpacing = 2.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                IconButton(onClick = { onclickAddFavorite(advice) }) {
                    Icon(
                        imageVector = if (advice.isFavorite) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        tint = if (advice.isFavorite) Color.Black else Color.Black,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = { onclickDelete(advice) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
        }
    }
}
