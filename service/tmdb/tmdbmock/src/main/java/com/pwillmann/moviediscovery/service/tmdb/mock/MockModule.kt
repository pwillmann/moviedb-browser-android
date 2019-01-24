package com.pwillmann.moviediscovery.service.tmdb.mock

import com.pwillmann.moviediscovery.service.tmdb.core.TvShowsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MockModule {

    @JvmStatic
    @Provides
    @Singleton
    fun mockZenjobService(mockService: MockTMDBService): TvShowsService =
            mockService
}