package io.github.wlara.cryptoapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.wlara.cryptoapp.R
import io.github.wlara.cryptoapp.data.model.Asset
import io.github.wlara.cryptoapp.ui.theme.CryptoAppTheme
import java.text.NumberFormat
import java.util.*

private val UsdFormat = NumberFormat.getCurrencyInstance().apply {
    currency = Currency.getInstance("USD")
}

private val PercentFormat = NumberFormat.getPercentInstance().apply {
    maximumFractionDigits = 2
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items = viewModel.assets.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(items.loadState.refresh is LoadState.Loading),
            onRefresh = { items.refresh() },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(items) { asset -> AssetItem(asset!!) }
                when (items.loadState.append) {
                    is LoadState.Loading -> item { LoadingItem() }
                    is LoadState.Error -> item { ErrorItem { items.retry() } }
                    else -> {}
                }
            }
        }
    }
    if (items.loadState.refresh is LoadState.Error) {
        val errorMessage = stringResource(id = R.string.load_error_message)
        val retryLabel = stringResource(id = R.string.retry_button_label)
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = retryLabel
            )
            if (result == SnackbarResult.ActionPerformed) items.retry()
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorItem(onRetry: () -> Unit) {
    Row(
        modifier = Modifier
            .heightIn(min = 76.dp)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.load_error_message),
            style = MaterialTheme.typography.h5
        )
        Button(onClick = { onRetry() }) {
            Text(text = stringResource(id = R.string.retry_button_label))
        }
    }
}

@Composable
private fun AssetItem(asset: Asset) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        ConstraintLayout {
            val (logo, symbol, name, price, change) = createRefs()
            val centerLine = createGuidelineFromTop(0.5f)
            AsyncImage(
                model = asset.logoUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(logo) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            )
            Text(
                text = asset.symbol,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.constrainAs(symbol) {
                    start.linkTo(logo.end, 8.dp)
                    bottom.linkTo(centerLine)
                }
            )
            Text(
                text = asset.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(logo.end, 8.dp)
                    top.linkTo(centerLine)
                }
            )
            Text(
                text = UsdFormat.format(asset.priceUsd),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.constrainAs(price) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(centerLine)
                }
            )
            Text(
                text = PercentFormat.format(asset.change24Hr),
                color = if (asset.change24Hr < 0) Color.Red else Color.Green,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.constrainAs(change) {
                    top.linkTo(centerLine)
                    end.linkTo(parent.end, 8.dp)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorItemPreview() {
    CryptoAppTheme {
        ErrorItem {}
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetItemPreview() {
    val asset = Asset(
        name = "Bitcoin",
        symbol = "BTC",
        priceUsd = 19627.02,
        change24Hr = 0.0329,
        logoUrl = "https://assets.coincap.io/assets/icons/btc@2x.png"
    )
    CryptoAppTheme {
        AssetItem(asset)
    }
}

