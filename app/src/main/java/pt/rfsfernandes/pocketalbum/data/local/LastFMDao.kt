package pt.rfsfernandes.pocketalbum.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist

@Dao
interface LastFMDao {

  @Query("SELECT * FROM Artist")
  suspend fun searchForArtist(): List<Artist>

  @Query("SELECT * FROM Album ORDER BY name")
  fun getStoredAlbums(): Flow<List<Album>>

  @Query("SELECT * FROM Album WHERE name LIKE :albumName AND artist LIKE :artistName")
  suspend fun getAlbumInfo(albumName: String?, artistName: String?): Album?

  @Query("SELECT * FROM TopAlbum")
  suspend fun getTopAlbums(): List<TopAlbum>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertArtists(artists: List<Artist>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAlbums(albums: List<Album>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAlbum(album: Album)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTopAlbums(topAlbums: List<TopAlbum>)

  @Query("DELETE FROM Artist")
  suspend fun cleanCachedArtists()

  @Query("DELETE FROM TopAlbum")
  suspend fun cleanCachedTopAlbums()

  @Query("DELETE FROM Album WHERE name LIKE :albumName AND artist LIKE :artistName")
  suspend fun removeAlbumFromDB(albumName: String?, artistName: String?): Int
}