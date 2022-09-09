package com.example.pagination3google

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.pagination3google.Model.GithubRepository
import com.example.pagination3google.Service.GitHubService
import com.example.pagination3google.ui.ViewModelFactory

object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GitHubService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository())
    }
}