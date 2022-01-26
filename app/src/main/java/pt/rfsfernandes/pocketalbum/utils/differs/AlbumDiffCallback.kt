package pt.rfsfernandes.pocketalbum.utils.differs

import androidx.recyclerview.widget.DiffUtil
import pt.rfsfernandes.pocketalbum.data.models.album.Album

object AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
  override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
    return (oldItem.url) == (newItem.url)
  }
}