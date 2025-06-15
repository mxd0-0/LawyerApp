package com.example.lawyerapp.presentation.requests_list.components

import com.example.lawyerapp.ui.theme.LightGreyText


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lawyerapp.data.model.Letter
import com.example.lawyerapp.ui.theme.SwipeGreen
import com.example.lawyerapp.ui.theme.SwipeRed

import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterItemCard(
    letter: Letter,
    onDelete: () -> Unit,
    onCheck: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> { // Swipe Left (Check)
                    onCheck()
                    false // Don't dismiss item, just trigger action
                }
                SwipeToDismissBoxValue.StartToEnd -> { // Swipe Right (Delete)
                    onDelete()
                    true // Let the item be dismissed from the list
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { it * 0.25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true, // Enables delete
        enableDismissFromEndToStart = true, // Enables check
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> SwipeRed
                    SwipeToDismissBoxValue.EndToStart -> SwipeGreen
                    else -> Color.Transparent
                }, label = "background color animation"
            )
            val alignment = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                else -> Alignment.Center
            }
            val icon = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Delete
                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Check
                else -> null
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
                label = "icon scale animation"
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                icon?.let {
                    Icon(
                        it,
                        contentDescription = "Action Icon",
                        modifier = Modifier.scale(scale),
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = letter.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = letter.description,
                        fontSize = 14.sp,
                        color = LightGreyText,
                        maxLines = 2
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = letter.date,
                    fontSize = 12.sp,
                    color = LightGreyText
                )
            }
        }
    }
}