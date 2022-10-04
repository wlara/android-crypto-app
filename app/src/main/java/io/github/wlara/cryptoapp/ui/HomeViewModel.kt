package io.github.wlara.cryptoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.wlara.cryptoapp.data.model.Asset
import io.github.wlara.cryptoapp.data.repo.AssetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AssetsRepository
) : ViewModel() {

    val assets: Flow<PagingData<Asset>> = Pager(PagingConfig(pageSize = 60)) {
        repository.assetsPagingSource()
    }.flow.cachedIn(viewModelScope)
}
