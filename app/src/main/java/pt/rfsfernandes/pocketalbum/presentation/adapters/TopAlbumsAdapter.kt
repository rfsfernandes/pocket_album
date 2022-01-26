package pt.rfsfernandes.pocketalbum.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.databinding.AlbumRowBinding
import pt.rfsfernandes.pocketalbum.utils.differs.TopAlbumsDiffCallback
import pt.rfsfernandes.pocketalbum.utils.getUsablePhotoUrl

/**
 *   Class TopAlbumsAdapter created at 23/01/2022 20:56 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class TopAlbumsAdapter(private val context: Context, private val onClick: (TopAlbum) -> Unit) :
  ListAdapter<TopAlbum,
      TopAlbumsAdapter.TopAlbumViewHolder>(TopAlbumsDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbumViewHolder {
    val binding = AlbumRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return TopAlbumViewHolder(context, binding, onClick)
  }


  override fun onBindViewHolder(holder: TopAlbumViewHolder, position: Int) {
    val album = getItem(position)
    holder.bind(album)
  }

  class TopAlbumViewHolder(
    private val context: Context, val binding: AlbumRowBinding, val
    onClick: (TopAlbum) -> Unit
  ) :
    RecyclerView.ViewHolder(binding.root) {
    private var currentTopAlbum: TopAlbum? = null

    init {

      binding.root.setOnClickListener {
        currentTopAlbum?.let {
          onClick(it)
        }
      }

    }

    fun bind(album: TopAlbum) {
      currentTopAlbum = album
      binding.textViewTopAlbumTitle.text = album.name
      val url: String = album.image.getUsablePhotoUrl()
      if (url.isNotEmpty()) {
        Picasso.get().load(url).placeholder(R.drawable.album_24)
          .into(binding.imageViewTopAlbum)
      }

      binding.textViewTopAlbumPlayCount.text =
        context.resources.getString(R.string.played_times, album.playcount.toString())
      binding.textViewTopAlbumArtist.text =
        context.resources.getString(R.string.album_by, album.artist?.name)
    }

  }

}
