package pt.rfsfernandes.pocketalbum.presentation.search_artist

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.databinding.SearchArtistFragmentBinding
import pt.rfsfernandes.pocketalbum.presentation.adapters.SearchArtistAdapter
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.decorators.VerticalItemDecorator
import pt.rfsfernandes.pocketalbum.utils.hideKeyboard
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent
import kotlin.math.abs

@AndroidEntryPoint
class SearchArtistFragment : Fragment() {
  private val viewModel: SearchArtistViewModel by viewModels()
  private lateinit var binding: SearchArtistFragmentBinding
  private lateinit var adapter: SearchArtistAdapter

  companion object {
    fun newInstance() = SearchArtistFragment()
  }


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = SearchArtistFragmentBinding.inflate(inflater)
    setupRecyclerView()
    binding.goBackToMyAlbums.setOnClickListener {
      findNavController().popBackStack()
    }

    binding.imageButtonSearchArtists.setOnClickListener {
      searchArtist()
    }

    binding.editTextSearchArtist.setOnEditorActionListener(object : TextView
    .OnEditorActionListener {
      override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          searchArtist()
          return true
        }
        return false
      }
    })

    val params: LinearLayout.LayoutParams =
      binding.cardViewSearch.layoutParams as LinearLayout.LayoutParams

    binding.searchAppBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
      val margin = abs(verticalOffset)

      when {
        margin in 40..150 -> params.leftMargin = margin
        margin < 40 -> params.leftMargin = 40
        margin >= 150 -> params.leftMargin = 150
      }
      binding.cardViewSearch.post {
        binding.cardViewSearch.layoutParams = params
      }
    })
    initViewModelCollectors()
    return binding.root
  }

  private fun setupRecyclerView() {
    adapter = SearchArtistAdapter {
      val directions = SearchArtistFragmentDirections
        .actionSearchArtistFragmentToTopAlbumsFragment(it.name.toString(), it.mbid.toString())
      findNavController().navigate(directions)
    }

    binding.recyclerViewArtistSearch.adapter = adapter
    val manager = LinearLayoutManager(context)
    binding.recyclerViewArtistSearch.layoutManager = manager

    binding.recyclerViewArtistSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (manager.findLastVisibleItemPosition() == (adapter.itemCount - 5))
          viewModel.searchArtist(binding.editTextSearchArtist.text.toString(), false)
      }

    })

    val decorator = VerticalItemDecorator(context, R.drawable.divider)
    binding.recyclerViewArtistSearch.addItemDecoration(decorator)
  }

  private fun searchArtist() {
    hideKeyboard()
    viewModel.searchArtist(binding.editTextSearchArtist.text.toString(), true)

  }

  private fun initViewModelCollectors() {

    viewModel.artistResource.onEach {

      when (it) {
        is Resource.Success -> {
          it.data?.let { list ->
            adapter.submitList(list)
            if (list.isEmpty()) {
              Snackbar.make(
                binding.root,
                getString(R.string.no_results_found),
                BaseTransientBottomBar.LENGTH_LONG
              ).show()
            }
          }
        }
        is Resource.Error -> Snackbar.make(
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
      }
    }.launchIn(lifecycleScope)

  }

}