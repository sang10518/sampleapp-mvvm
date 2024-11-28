package com.swc.sampleapp_mvvm.view.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.ui.UiState
import com.swc.sampleapp_mvvm.viewmodel.PostsViewModel
import com.swc.sampleapp_mvvm.viewmodel.WeatherViewModel

@Composable
fun PostsScreen(viewModel: PostsViewModel) {
    val postsState by viewModel.postsState.collectAsState()

    when (postsState) {
        is UiState.Loading -> {
            Text("Loading posts...")
        }
        is UiState.Success -> {
            val posts = (postsState as UiState.Success).data as List<PostResponse>
            LazyColumn {
                itemsIndexed(posts) { index, post ->
                    PostItem(
                        post = post,
                        onClick = {
                            // Handle click for specific post
                            viewModel.onPostClicked(post, index)
                        }
                    )
                }
            }
        }
        is UiState.Error -> {
            val errorMessage = (postsState as UiState.Error).message
            Text("Error: $errorMessage")
        }
    }
}

@Composable
fun PostItem(
    post: PostResponse,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = post.title)
            Text(text = post.body)
        }
    }
}

