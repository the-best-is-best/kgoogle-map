package io.github.sample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> TypeAhead(
    itemsProvider: suspend (String) -> List<T>, // Async function to fetch items
    itemToString: (T) -> String, // Conversion to string for display
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit = {
        Text(
            text = "Loading...",
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    },
    noResultsContent: @Composable () -> Unit = {
        Text(
            text = "No results found.",
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    },
    onItemSelected: (T) -> Unit, // Callback for when an item is selected
) {
    var query by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<T>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var noResults by remember { mutableStateOf(false) }
    var itemSelected by remember { mutableStateOf(false) } // New state to track if an item has been selected
    var debounceJob: Job? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()

    // Debounced fetching logic
    LaunchedEffect(query) {
        if (query.isEmpty()) {
            suggestions = emptyList()
            noResults = false
            return@LaunchedEffect
        }

        if (!itemSelected) {
            isLoading = true
            noResults = false

            debounceJob?.cancel() // Cancel any ongoing job

            debounceJob = coroutineScope.launch {
                delay(500) // Debounce delay
                try {
                    val results = itemsProvider(query)
                    suggestions = results
                    noResults = results.isEmpty()
                } catch (e: Exception) {
                    suggestions = emptyList()
                    noResults = true
                } finally {
                    isLoading = false
                }
            }
        }
    }

    // Main UI layout
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Search input field
        TextField(
            value = query,
            onValueChange = {
                query = it
                itemSelected = false // Reset itemSelected when user types a new query
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp) // Optional shadow for visual enhancement
                .background(Color.White)
                .border(1.dp, Color.Gray),
            placeholder = { Text("Search...") }
        )

        // Suggestions list displayed below the search input
        if (suggestions.isNotEmpty() || isLoading || noResults) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .background(Color.White)
                    .shadow(4.dp) // Optional shadow for visual enhancement
            ) {
                if (isLoading) {
                    loadingContent()
                } else if (suggestions.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        items(suggestions.size) { index ->
                            val item = suggestions[index]
                            val itemText = itemToString(item)

                            Text(
                                text = itemText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemSelected(item)
                                        query = itemText // Set query to the selected item
                                        itemSelected = true // Mark item as selected
                                        suggestions =
                                            emptyList() // Hide suggestions after selection
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                } else if (noResults && query.isNotEmpty()) {
                    noResultsContent()
                }
            }
        }
    }
}
