package pt.rfsfernandes.pocketalbum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist

/**
 *   Class AppDatabase created at 22/01/2022 20:49 for the project PocketAlbum
 *   By: rodrigofernandes
 */
@Database(entities = [Album::class, TopAlbum::class, Artist::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract val lastFMDao: LastFMDao
}
