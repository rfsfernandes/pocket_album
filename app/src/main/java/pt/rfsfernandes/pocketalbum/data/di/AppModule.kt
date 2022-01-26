package pt.rfsfernandes.pocketalbum.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.rfsfernandes.pocketalbum.BuildConfig
import pt.rfsfernandes.pocketalbum.data.local.AppDatabase
import pt.rfsfernandes.pocketalbum.data.remote.RetrofitBuilder
import pt.rfsfernandes.pocketalbum.data.remote.apiservice.LastFMService
import pt.rfsfernandes.pocketalbum.data.repository.Repository
import pt.rfsfernandes.pocketalbum.data.repository.RepositoryImpl
import pt.rfsfernandes.pocketalbum.domain.use_cases.*
import javax.inject.Singleton

/**
 *   Class AppModule created at 22/01/2022 20:25 for the project PocketAlbum
 *   By: rodrigofernandes
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideGetAlbumInfo(repository: Repository): GetAlbumInfo {
    return GetAlbumInfo(repository)
  }

  @Provides
  @Singleton
  fun provideGetStoredAlbums(repository: Repository): GetStoredAlbums {
    return GetStoredAlbums(repository)
  }

  @Provides
  @Singleton
  fun provideGetTopAlbums(repository: Repository): GetTopAlbums {
    return GetTopAlbums(repository)
  }

  @Provides
  @Singleton
  fun provideSearchForArtist(repository: Repository): SearchForArtist {
    return SearchForArtist(repository)
  }

  @Provides
  @Singleton
  fun provideAddAlbumToList(repository: Repository): AddAlbumToList {
    return AddAlbumToList(repository)
  }

  @Provides
  @Singleton
  fun provideRemoveAlbumFromList(repository: Repository): RemoveAlbumFromList {
    return RemoveAlbumFromList(repository)
  }

  @Provides
  @Singleton
  fun provideRepository(
    service: LastFMService,
    database: AppDatabase
  ): Repository {
    return RepositoryImpl(service, database.lastFMDao)
  }

  @Provides
  @Singleton
  fun provideDatabase(app: Application): AppDatabase {
    return Room.databaseBuilder(app, AppDatabase::class.java, "pocket_album_db").build()
  }

  @Provides
  @Singleton
  fun provideService(): LastFMService {
    return RetrofitBuilder(BuildConfig.baseUrl).lastFMService
  }
}
