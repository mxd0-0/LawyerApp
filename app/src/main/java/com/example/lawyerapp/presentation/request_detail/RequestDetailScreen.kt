package com.example.lawyerapp.presentation.request_detail


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lawyerapp.ui.theme.LightGreyText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: RequestDetailViewModel = hiltViewModel(),
) {
    // --- THIS IS THE MAIN FIX ---
    // Use the 'by' delegate to automatically unwrap the state's value.
    val state by viewModel.state.collectAsState()
    val letter = state.letter

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
        // Now you can access properties directly on 'state'
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        if (letter == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Letter not found.")
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
            InfoCard(label = "Email", value = letter.email)
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(label = "Name", value = letter.fullName)
            Spacer(modifier = Modifier.height(12.dp))
            InfoCard(label = "Description", value = letter.description)

            Spacer(modifier = Modifier.height(32.dp))

            if (letter.lawyerId.isNotEmpty()) {
                InfoCard(label = "Answered by Lawyer ID", value = letter.lawyerId)
            }

            Text(
                "Lawyer's Answer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = letter.lawyerAnswer,
                onValueChange = { viewModel.updateLawyerAnswer(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text("Type your response here...") }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.saveChanges()
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                // Accessing properties directly on 'state' now works
                enabled = !state.isSaving
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Save and Close", fontSize = 16.sp)
                }
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


