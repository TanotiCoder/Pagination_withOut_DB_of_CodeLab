package com.example.pagination3google.Model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagination3google.Data.GithubPagingSource
import com.example.pagination3google.Service.GitHubService
import kotlinx.coroutines.flow.Flow


class GithubRepository(private val service: GitHubService) {
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}