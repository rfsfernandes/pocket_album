package pt.rfsfernandes.pocketalbum.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pt.rfsfernandes.pocketalbum.data.models.generic.Track
import pt.rfsfernandes.pocketalbum.databinding.TracksRowBinding
import pt.rfsfernandes.pocketalbum.utils.differs.TrackDiffCallback
import pt.rfsfernandes.pocketalbum.utils.toTrackTime

/**
 *   Class TracksAdapter created at 24/01/2022 22:20 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class TracksAdapter : ListAdapter<Track,
    TracksAdapter.TrackViewHolder>(TrackDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
    val binding = TracksRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return TrackViewHolder(binding)
  }


  override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
    val track = getItem(position)
    holder.bind(track)
  }

  class TrackViewHolder(val binding: TracksRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
      binding.textViewTrackName.text = track.name
      binding.textViewTrackTime.text = "${track.duration?.toTrackTime()}"
    }

  }

}

