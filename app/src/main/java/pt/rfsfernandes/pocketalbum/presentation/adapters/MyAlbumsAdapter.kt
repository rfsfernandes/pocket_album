package pt.rfsfernandes.pocketalbum.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.databinding.AlbumRowBinding
import pt.rfsfernandes.pocketalbum.utils.differs.AlbumDiffCallback
import pt.rfsfernandes.pocketalbum.utils.getUsablePhotoUrl
import java.io.File

/**
 *   Class MyAlbumsAdapter created at 24/01/2022 23:44 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class MyAlbumsAdapter(private val context: Context, private val onClick: (Album) -> Unit) :
  ListAdapter<Album,
      MyAlbumsAdapter.AlbumViewHolder>(AlbumDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
    val binding = AlbumRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AlbumViewHolder(context, binding, onClick)
  }


  override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
    val album = getItem(position)
    holder.bind(album)
  }

  class AlbumViewHolder(
    private val context: Context, val binding: AlbumRowBinding, val
    onClick: (Album) -> Unit
  ) :
    RecyclerView.ViewHolder(binding.root) {
    private var currentAlbum: Album? = null

    init {

      binding.root.setOnClickListener {
        currentAlbum?.let {
          onClick(it)
        }
      }

    }

    fun bind(album: Album) {
      currentAlbum = album
      binding.textViewTopAlbumTitle.text = album.name
      if (album.imagePath != null) {
        var file = File(album.imagePath.toString())
        val uri = file.toURI().toString()
        Picasso.get().load(uri).placeholder(R.drawable.album_24)
          .into(binding.imageViewTopAlbum)
      } else {
        val url: String = album.image.getUsablePhotoUrl()
        if (url.isNotEmpty()) {
          Picasso.get().load(url).placeholder(R.drawable.album_24)
            .into(binding.imageViewTopAlbum)
        }
      }


      binding.textViewTopAlbumPlayCount.text =
        context.resources.getString(R.string.played_times, album.playcount.toString())
      binding.textViewTopAlbumArtist.text = context.resources.getString(
        R.string.album_by, album
          .artist.toString()
      )
    }

  }

}