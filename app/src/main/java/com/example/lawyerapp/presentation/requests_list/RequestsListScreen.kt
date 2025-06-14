package com.example.lawyerapp.presentation.requests_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lawyerapp.domain.model.Letter
import com.example.lawyerapp.presentation.requests_list.components.LetterItemCard
import com.example.lawyerapp.ui.theme.AppOrange
import com.example.lawyerapp.ui.theme.LawAppTheme
import com.example.lawyerapp.ui.theme.LightGreyText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsListScreen(
    onNavigateToDetail: (Letter) -> Unit,
    viewModel: RequestsListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { AppBottomNavigation() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // For status bar space

            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))

            FilterChips()

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.letters, key = { it.idLetter }) { letter ->
                    LetterItemCard(
                        letter = letter,
                        onDelete = { viewModel.onDeleteLetter(letter) },
                        onCheck = { onNavigateToDetail(letter) }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        )
    )
}

@Composable
fun FilterChips() {
    val filters = listOf("All", "Consult", "Free Consults", "File Reques")
    var selectedFilter by remember { mutableStateOf("Consult") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter == selectedFilter
            FilterChip(
                selected = isSelected,
                onClick = { selectedFilter = filter },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppOrange,
                    selectedLabelColor = Color.White,
                    labelColor = LightGreyText
                ),
                // --- FIX IS HERE ---
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true, // The chip is always enabled
                    selected = isSelected, // Pass the selection state
                    borderColor = Color.LightGray, // Color when not selected
                    selectedBorderColor = AppOrange // Color when selected
                )
            )
        }
    }
}

@Composable
fun AppBottomNavigation() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("requests", "Profile")
    val icons = listOf(Icons.Outlined.Build, Icons.Default.Person)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = LightGreyText,
                    unselectedTextColor = LightGreyText
                )
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RequestsListScreenPreview() {
    LawAppTheme {
        // We pass a dummy lambda for navigation in preview
        RequestsListScreen(onNavigateToDetail = {})
    }
}