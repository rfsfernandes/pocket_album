package pt.rfsfernandes.pocketalbum.data.remote.apiservice

import pt.rfsfernandes.pocketalbum.data.models.album.AlbumInfoWrapper
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbumsResponse
import pt.rfsfernandes.pocketalbum.data.models.artist.ArtistsSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

  @GET("?format=json")
  suspend fun searchArtist(
    @Query("api_key") apiKey: String,
    @Query("method") method: String,
    @Query("artist") artist: String? = "",
    @Query("mbid") mbid: String? = "",
    @Query("page") page: Int? = 0,
    @Query("limit") limit: Int? = 0,
  ): Response<ArtistsSearchResponse>

  @GET("?format=json")
  suspend fun getTopAlbums(
    @Query("api_key") apiKey: String,
    @Query("method") method: String,
    @Query("artist") artist: String? = "",
    @Query("page") page: Int? = 0,
    @Query("limit") limit: Int? = 0,
  ): Response<TopAlbumsResponse>

  @GET("?format=json")
  suspend fun getAlbumInfo(
    @Query("api_key") apiKey: String,
    @Query("method") method: String,
    @Query("artist") artist: String? = "",
    @Query("album") album: String? = "",
  ): Response<AlbumInfoWrapper>


}