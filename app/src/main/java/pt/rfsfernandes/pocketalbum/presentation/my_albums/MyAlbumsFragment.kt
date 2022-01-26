package pt.rfsfernandes.pocketalbum.presentation.my_albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.databinding.MyAlbumsFragmentBinding
import pt.rfsfernandes.pocketalbum.presentation.adapters.MyAlbumsAdapter
import pt.rfsfernandes.pocketalbum.utils.getUsablePhotoUrl
import java.io.File

@AndroidEntryPoint
class MyAlbumsFragment : Fragment() {

  private val viewModel: MyAlbumsViewModel by viewModels()
  private lateinit var binding: MyAlbumsFragmentBinding
  private lateinit var adapter: MyAlbumsAdapter

  companion object {
    fun newInstance() = MyAlbumsFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = MyAlbumsFragmentBinding.inflate(inflater)
    adapter = MyAlbumsAdapter(requireContext()) { album ->
      val url: String = if (album.imagePath != null) {
        File(album.imagePath!!).toURI().toString()
      } else album.image.getUsablePhotoUrl()
      val directions = MyAlbumsFragmentDirections.actionMyAlbumsFragmentToAlbumDetailFragment(
        album
          .name.toString(), album.artist.toString(), url
      )
      findNavController().navigate(directions)
    }

    binding.recyclerViewMyAlbums.layoutManager = LinearLayoutManager(context)
    binding.recyclerViewMyAlbums.adapter = adapter

    binding.fabToArtistSearch.setOnClickListener {
      findNavController().navigate(R.id.action_myAlbumsFragment_to_searchArtistFragment)
    }
    viewModel.fetchStoredAlbums()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    iniViewModelCollectors()

  }

  private fun iniViewModelCollectors() {
    viewModel.storedAlbums.onEach {
      adapter.submitList(it)
    }.launchIn(lifecycleScope)
  }

}