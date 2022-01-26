package pt.rfsfernandes.pocketalbum.presentation.album_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.generic.Track
import pt.rfsfernandes.pocketalbum.databinding.AlbumDetailFragmentBinding
import pt.rfsfernandes.pocketalbum.presentation.adapters.TracksAdapter
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.collapse
import pt.rfsfernandes.pocketalbum.utils.expand
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent


@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

  private lateinit var binding: AlbumDetailFragmentBinding
  private lateinit var adapter: TracksAdapter
  private val viewModel: AlbumDetailViewModel by viewModels()
  private val args: AlbumDetailFragmentArgs by navArgs()
  private lateinit var currentAlbum: Album
  private var tracksAreShowing = true
  private var currentList: List<Track> = arrayListOf()

  companion object {
    fun newInstance() = AlbumDetailFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = AlbumDetailFragmentBinding.inflate(layoutInflater)
    binding.textViewArtistName.text = args.artist
    binding.textViewAlbumDetailName.text = args.albumName
    setUpTracksAdapter()
    val imageUrl = args.imageUrl
    if (imageUrl.isNotEmpty())
      Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.album_24)
        .into(binding.imageViewAlbumDetail)

    viewModel.getAlbumInfo(args.artist, args.albumName)

    binding.goBackToTopAlbums.setOnClickListener {
      findNavController().popBackStack()
    }

    binding.addToMyAlbums.setOnClickListener {
      if (::currentAlbum.isInitialized) {

        if (!currentAlbum.isFavorite) {
          viewModel.addToFavorites(
            context, binding.imageViewAlbumDetail.drawable.toBitmap(),
            currentAlbum
          )
        } else {
          setFavoriteImage(false)
          currentAlbum.isFavorite = false
          viewModel.removeFromFavorites(
            context,
            currentAlbum.artist.toString(),
            currentAlbum.name.toString(),
            currentAlbum.imagePath.toString()
          )
        }

      }
    }

    binding.cardViewShowTracks.setOnClickListener {

      if (tracksAreShowing) {
        binding.linearLayoutDetailTracks.collapse()
        binding.imageViewTracksArrow.animate().apply {
          duration = 400
          rotation(180f)
        }.withEndAction {
          tracksAreShowing = false
        }
      } else {
        binding.linearLayoutDetailTracks.expand()
        binding.imageViewTracksArrow.animate().apply {
          duration = 400
          rotation(0f)
        }.withEndAction {
          tracksAreShowing = true
        }
      }

    }

    return binding.root
  }

  private fun setUpTracksAdapter() {
    adapter = TracksAdapter()
    binding.recyclerViewAlbumDetailTracks.layoutManager = LinearLayoutManager(context)
    binding.recyclerViewAlbumDetailTracks.adapter = adapter
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewModelCollectors()
  }

  private fun initViewModelCollectors() {
    viewModel.albumInfoResource.onEach {
      when (it) {
        is Resource.Success -> {
          it.data?.let { album ->
            displayAlbumData(album)
          }
        }
        is Resource.Error ->
          Snackbar.make(
            binding.root, it.message.toString(), BaseTransientBottomBar.LENGTH_LONG
          ).show()

        is Resource.NetworkError -> Snackbar.make(
          binding.root, getString(R.string.no_internet_connection), BaseTransientBottomBar
            .LENGTH_LONG
        ).show()
        else -> {}
      }
    }.launchIn(lifecycleScope)

    viewModel.eventFlow.onEach {
      when (it) {
        is UIEvent.Loading -> {
          binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
        is UIEvent.Snackbar -> Snackbar.make(
          binding.root, it.text.toString(), BaseTransientBottomBar.LENGTH_LONG
        ).show()
      }
    }.launchIn(lifecycleScope)

    viewModel.addAlbumResource.onEach {
      when (it) {
        is Resource.Success -> it.data?.let { it1 -> setFavoriteImage(it1) }
        is Resource.Error -> it.data?.let { it1 -> setFavoriteImage(it1) }
        else -> {}
      }
    }.launchIn(lifecycleScope)

  }

  private fun displayAlbumData(album: Album) {
    if (album.tracks != null) {
      currentList = album.tracks?.track!!
      adapter.submitList(album.tracks?.track)
    }
    binding.linearLayoutDetailTracks.visibility = if (currentList.isEmpty()) View.GONE else View.VISIBLE
    binding.cardViewShowTracks.visibility = if (currentList.isEmpty()) View.GONE else View.VISIBLE

    currentAlbum = album

    binding.textViewAlbumDetailPlayCount.text =
      resources.getString(R.string.played_times, album.playcount.toString())

    val description = album.wiki?.content?.let { it1 ->
      HtmlCompat.fromHtml(
        it1.replace("\n", "<br>"),
        FROM_HTML_MODE_COMPACT
      )
    }

    binding.textViewAlbumDescription.visibility = if (description.isNullOrEmpty()) View.GONE else View.VISIBLE
    binding.textView.visibility = if (description.isNullOrEmpty()) View.GONE else {
      binding.textViewAlbumDescription.text = description
      View.VISIBLE
    }

    setFavoriteImage(album.isFavorite)
  }

  private fun setFavoriteImage(isFavorite: Boolean) {
    if (isFavorite) {
      binding.addToMyAlbums.setImageDrawable(
        ResourcesCompat.getDrawable(
          resources, R.drawable
            .heart_shape_filled, null
        )
      )
    } else {
      binding.addToMyAlbums.setImageDrawable(
        ResourcesCompat.getDrawable(
          resources, R.drawable
            .heart_shape_empty, null
        )
      )
    }
  }

}