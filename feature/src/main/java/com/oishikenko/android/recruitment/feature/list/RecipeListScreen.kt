package com.oishikenko.android.recruitment.feature.list

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.feature.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import androidx.compose.foundation.layout.Box as Box

@Composable
fun LoadingIndicator(modifier: Modifier.Companion) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 41.11.dp),
            color = Color(6,58,119,255),
            strokeWidth = 8.dp
        )
    }

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel()
) {
    val cookingRecords by viewModel.cookingRecords.collectAsStateWithLifecycle()
    val pagingItems = viewModel.cookingRecords.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            ) {
                Text(
                    text = stringResource(id = R.string.cooking_records_title),
                    color = Color.Black,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(
                            start = 62.64.dp,
                        ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_header),
                    contentDescription = "headerImage",
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        )
                )
            }
        }
    ) { innerPadding ->
        //val pagingData: PagingData<CookingRecord> by remember { viewModel.cookingRecords }.collectAsLazyPagingItems()

        if (pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0) {
            // データが読み込まれる前に表示される CircularProgressIndicator
            LoadingIndicator(
                modifier = Modifier
            )
        } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            items(pagingItems) { record ->
                record?.let {
                    RecipeListItem(it)
                }
            }

            // LazyColumn の最後に到達したときに新しいデータを読み込むための LoadState を監視する
            pagingItems.apply {
                when(loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingIndicator(modifier = Modifier) }
                    }
                    is LoadState.Error -> {
                        // handle error state
                    }
                    is LoadState.NotLoading -> {
                    }
                }
            }
        }

            /*items(pagingItems) { record ->
                record?.let {
                    RecipeListItem(it)
                }
            }*/
        }

        /*items(cookingRecords) {
                RecipeListItem(it)
            }*/

    }
}



@Preview
@Composable
fun PreviewRecipeListScreen(){
    MaterialTheme {
        RecipeListScreen()
    }
}




