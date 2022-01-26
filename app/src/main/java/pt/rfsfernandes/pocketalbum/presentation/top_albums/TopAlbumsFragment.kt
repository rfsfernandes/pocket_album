package pt.rfsfernandes.pocketalbum.presentation.top_albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.databinding.TopAlbumsFragmentBinding
import pt.rfsfernandes.pocketalbum.presentation.adapters.TopAlbumsAdapter
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.getUsablePhotoUrl
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent

@AndroidEntryPoint
class TopAlbumsFragment : Fragment() {

  private val viewModel: TopAlbumsViewModel by viewModels()
  private lateinit var binding: TopAlbumsFragmentBinding
  private val args: TopAlbumsFragmentArgs by navArgs()
  private lateinit var artistName: String
  private lateinit var mbid: String
  private lateinit var adapter: TopAlbumsAdapter

  companion object {
    fun newInstance() = TopAlbumsFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = TopAlbumsFragmentBinding.inflate(layoutInflater)

    if (context != null)
      setupRecyclerView()

    binding.goBackToSearch.setOnClickListener {

      findNavController().popBackStack()
    }

    binding.closeButton.setOnClickListener {
      val directions = TopAlbumsFragmentDirections.actionTopAlbumsFragmentToMyAlbumsFragment2()
      findNavController().navigate(directions)
    }

    artistName = args.artistName
    mbid = args.artistMbid

    binding.textViewArtistName.text = artistName

    initViewModelCollectors()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.getTopAlbums(artistName, mbid, true)
  }

  private fun setupRecyclerView() {
    adapter = TopAlbumsAdapter(requireContext()) { album ->
      val url: String = album.image.getUsablePhotoUrl()

      album.name?.let { name ->
        album.artist?.name?.let { pickedArtistName ->
          val directions =
            TopAlbumsFragmentDirections.actionTopAlbumsFragmentToAlbumDetailFragment(
              name,
              pickedArtistName, url
            )

          findNavController().navigate(directions)
        }

      }

    }

    binding.recyclerViewTopAlbums.adapter = adapter
    val manager = LinearLayoutManager(context)
    binding.recyclerViewTopAlbums.layoutManager = manager

    binding.recyclerViewTopAlbums.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (manager.findLastVisibleItemPosition() == (adapter.itemCount - 5))
          viewModel.getTopAlbums(artistName, mbid, false)
      }

    })

  }

  private fun initViewModelCollectors() {
    viewModel.topAlbumsResource.onEach {
      when (it) {
        is Resource.Success -> {
          it.data?.let { list ->
            adapter.submitList(list)

            if (binding.progressBarTopAlbum.visibility == View.VISIBLE)
              binding.progressBarTopAlbum.visibility = View.GONE

            if (list.isEmpty()) {
              Snackbar.make(
                binding.root,
                getString(R.string.no_results_found),
                BaseTransientBottomBar.LENGTH_LONG
              ).show()
            }
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
          binding.progressBarTopAlbum.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
      }
    }.launchIn(lifecycleScope)
  }

}