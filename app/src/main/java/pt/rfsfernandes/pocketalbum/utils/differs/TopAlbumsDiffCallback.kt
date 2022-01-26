package pt.rfsfernandes.pocketalbum.utils.differs

import androidx.recyclerview.widget.DiffUtil
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum

/**
 *   Object TopAlbumsDiffCallback created at 23/01/2022 20:52 for the project PocketAlbum
 *   By: rodrigofernandes
 */
object TopAlbumsDiffCallback : DiffUtil.ItemCallback<TopAlbum>() {
  override fun areItemsTheSame(oldItem: TopAlbum, newItem: TopAlbum): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: TopAlbum, newItem: TopAlbum): Boolean {
    return (oldItem.url) == (newItem.url)
  }
}
