package pt.rfsfernandes.pocketalbum.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.databinding.SearchArtistRowBinding
import pt.rfsfernandes.pocketalbum.utils.differs.ArtistDiffCallback

/**
 *   Class SearchArtistAdapter created at 23/01/2022 17:30 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class SearchArtistAdapter(private val onClick: (Artist) -> Unit) : ListAdapter<Artist,
    SearchArtistAdapter.SearchViewHolder>(ArtistDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
    val binding = SearchArtistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return SearchViewHolder(binding, onClick)
  }


  override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
    val artist = getItem(position)
    holder.bind(artist)
  }

  class SearchViewHolder(val binding: SearchArtistRowBinding, val onClick: (Artist) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    private var currentArtist: Artist? = null

    init {

      binding.root.setOnClickListener {
        currentArtist?.let {
          onClick(it)
        }
      }

    }

    fun bind(artist: Artist) {
      currentArtist = artist
      binding.textViewArtistSearchName.text = artist.name
    }

  }

}
