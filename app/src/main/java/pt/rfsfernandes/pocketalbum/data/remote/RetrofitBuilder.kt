package pt.rfsfernandes.pocketalbum.data.remote

import pt.rfsfernandes.pocketalbum.data.remote.apiservice.LastFMService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *   Class RetrofitBuilder created at 22/01/2022 20:42 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class RetrofitBuilder(baseUrl: String) {
  private var instance: Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val lastFMService = instance.create(LastFMService::class.java)

}
