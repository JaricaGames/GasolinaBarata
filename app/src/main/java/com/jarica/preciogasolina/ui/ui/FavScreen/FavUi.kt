package com.jarica.preciogasolina.ui.ui.FavScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.Typography
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByTowns

@Composable
fun FavUi(favViewModel: FavViewModel, listViewModel: ListViewModel) {

    val listFavId: MutableList<String> = mutableListOf()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<FavoriteUiState>(
        initialValue = FavoriteUiState.Loading, key1 = lifecycle, key2 = favViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            favViewModel.uiState.collect {
                value = it
            }
        }
    }

    when (uiState) {
        is FavoriteUiState.Error -> {}
        FavoriteUiState.Loading -> {}
        is FavoriteUiState.Success -> {
            (uiState as FavoriteUiState.Success).favorites.forEach {
                listFavId.add(it.id)
            }

            if (listFavId.isNotEmpty()) {
                BannerAdView()
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {

                    LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                        items(1) {
                            favViewModel.ReturnListFavId(listFavId)
                            listFavId.forEach { idGasStationFav ->
                                val gasStation =
                                    favViewModel.lookForGasStationFavoriteCard(idGasStationFav)
                                if (gasStation != null) {
                                    cardStationByTowns(gasStation, listViewModel, listFavId)
                                }
                            }
                        }

                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BannerAdView()
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {

                        Text(
                            text = "Actualmente no tienes ningún favorito",
                            style = Typography.body1,
                            //fontSize = 22.sp,
                            //fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.Naranja)
                        )
                    }
                }
            }

        }


    }
}

@SuppressLint("MissingPermission")
@Composable
fun BannerAdView() {
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            // Add your adUnitID, this is for testing.
            adUnitId = "ca-app-pub-4979320410432560/7752668839"
            loadAd(AdRequest.Builder().build())
        }
    })
}


