package com.example.lawyerapp.presentation.request_detail


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lawyerapp.domain.model.Letter
import com.example.lawyerapp.ui.theme.LawAppTheme
import com.example.lawyerapp.ui.theme.LightGreyText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    letter: Letter?,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        if (letter == null) {
            // Handle case where letter is not passed correctly
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error: Letter not found.")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Consult Request",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Expand")
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoCard(label = "Email", value = letter.email)

            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(label = "Name", value = letter.fullName)

            Spacer(modifier = Modifier.height(12.dp))

            // This is the repeated section from your UI
            InfoCard(label = "Email", value = "${letter.email}\ndhbdhbdhbdh\ndhdbdhbdhbdh\ndhbdhbdhbdhbd\nhbdhdbddddddddddddbfkjfn")

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Consult Request",
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            // And the second repeated section
            InfoCard(label = "Email", value = "${letter.email}\ndhbdhbdhbdh\ndhdbdhbdhbdh\ndhbdhbdhbdhbd\nhbdhdbddddddddddddbfkjfn")


            Spacer(modifier = Modifier.weight(1f)) // Pushes button to the bottom

            Button(
                onClick = { onNavigateBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Done", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = label, color = LightGreyText, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RequestDetailScreenPreview() {
    LawAppTheme {
        val previewLetter = Letter(
            email = "Mohamed@gmailfbkfn",
            fullName = "USe4i4i4i4"
        )
        RequestDetailScreen(letter = previewLetter, onNavigateBack = {})
    }
}