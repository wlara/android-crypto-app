package io.github.wlara.cryptoapp.data.repo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.github.wlara.cryptoapp.data.repo.impl.AssetsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModuleBinds {
    @Binds
    abstract fun bindAssetsRepository(repository: AssetsRepositoryImpl): AssetsRepository
}
