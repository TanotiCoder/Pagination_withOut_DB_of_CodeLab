package com.example.pagination3google.Data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagination3google.Model.GithubRepository.Companion.NETWORK_PAGE_SIZE
import com.example.pagination3google.Model.Repo
import com.example.pagination3google.Service.GitHubService
import com.example.pagination3google.Service.IN_QUALIFIER
import retrofit2.HttpException
import java.io.IOException


private val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(private val service: GitHubService, private val query: String) :
    PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val response = service.searchRepo(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty())  null  else  position + (params.loadSize / NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}