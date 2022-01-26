package pt.rfsfernandes.pocketalbum.utils.differs

import androidx.recyclerview.widget.DiffUtil
import pt.rfsfernandes.pocketalbum.data.models.generic.Track

/**
 *   Object TrackDiffCallback created at 24/01/2022 22:21 for the project PocketAlbum
 *   By: rodrigofernandes
 */
object TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
  override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
    return (oldItem.url) == (newItem.url)
  }
}