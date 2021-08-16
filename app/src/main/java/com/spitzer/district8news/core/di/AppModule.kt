package com.spitzer.district8news.core.di

import com.spitzer.district8news.core.network.ApiClient
import com.spitzer.district8news.core.repository.PostRepository
import com.spitzer.district8news.core.repository.PostRepositoryImpl
import com.spitzer.district8news.core.repository.PostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePostRepository(
        service: PostService
    ): PostRepository =
        PostRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient =
        ApiClient()

    @Provides
    @Singleton
    fun providePostService(apiClient: ApiClient): PostService =
        apiClient.createService(PostService::class.java)
}