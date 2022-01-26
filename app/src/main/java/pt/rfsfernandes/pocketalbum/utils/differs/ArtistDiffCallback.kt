package pt.rfsfernandes.pocketalbum.utils.differs

import androidx.recyclerview.widget.DiffUtil
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist

/**
 *   Object ArtistDiffCallback created at 23/01/2022 17:34 for the project PocketAlbum
 *   By: rodrigofernandes
 */
object ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {

  override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
    return (oldItem.name + oldItem.mbid + oldItem.url) == (newItem.name + newItem.mbid + newItem.url)
  }
}
