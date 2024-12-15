package com.swc.sampleapp_mvvm.view.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.swc.sampleapp_mvvm.R
import com.swc.sampleapp_mvvm.model.remote.PostResponse
import com.swc.sampleapp_mvvm.ui.UiState
import com.swc.sampleapp_mvvm.viewmodel.PostsViewModel

//@Composable
//fun PostsScreen(viewModel: PostsViewModel) {
//    val postsState by viewModel.postsState.collectAsState()
//    when (postsState) {
//        is UiState.Loading -> CircularProgressIndicator()
//        is UiState.Success -> {
//            val posts = (postsState as UiState.Success).data as List<PostResponse>
//            LazyColumn {
//                itemsIndexed(posts) { index, post ->
//                    Text(text = post.title)
//                }
//            }
//        }
//        is UiState.Error -> Text(text = (postsState as UiState.Error).message)
//    }
//}

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

@Preview(showBackground = true)
@Composable
fun PreviewPostItem() {
    // Sample data for preview
    val samplePost = PostResponse(
        useId = 1,
        id = 1,
        title = "Sample Post Title",
        body = "This is the body of the post"
    )

    PostItem(post = samplePost, onClick = {})
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostItem(

    post: PostResponse,
    onClick: () -> Unit
) {
//    val dpValue: Dp  = 16.dp
//    val pxValue = with(LocalDensity.current) { dpValue.toPx() }


    Card(
        colors = cardColors(containerColor = Color.Yellow), // Set card color to yellow
        shape = RoundedCornerShape(2.dp), // Set corner radius to 2dp
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    text = "User ID: ${post.useId}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Row to layout image and text side by side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp) // Adjust padding if needed
            ) {
                // Image
                GlideImage(
                    model = "abc'", // Image URL
                    contentDescription = "Thumbnail",
                    loading = placeholder(R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .height(36.dp) // Make the image take up the whole height
                        .width(36.dp) // Set image width as needed
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(4.dp)),

                    contentScale = ContentScale.Crop
                )

                // Column for text (title and body) next to the image
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp) // Space between image and text
                        .align(Alignment.CenterVertically) // Center text vertically
                ) {
                    Text(
                        text = post.title,
                        color = Color.Blue,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(bottom = 4.dp) // Space between title and body
                    )
                    Text(text = post.body,
                        maxLines = 1, // Limits to a single line
                        overflow = TextOverflow.Ellipsis, // Adds ellipsis if text overflows
                    )
                }
            }
        }
    }

}

