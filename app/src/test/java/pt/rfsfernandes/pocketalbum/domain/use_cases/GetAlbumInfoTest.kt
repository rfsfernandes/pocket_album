package pt.rfsfernandes.pocketalbum.domain.use_cases

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.repository.FakeRepositoryImpl
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class GetAlbumInfoTest created at 02/02/2022 23:03 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class GetAlbumInfoTest {

  private lateinit var getAlbumInfo: GetAlbumInfo
  private lateinit var fakeRepositoryImpl: FakeRepositoryImpl

  @Before
  fun setUp() {
    fakeRepositoryImpl = FakeRepositoryImpl()
    getAlbumInfo = GetAlbumInfo(fakeRepositoryImpl)

    ('0'..'z').forEachIndexed { index, c ->
      fakeRepositoryImpl.storedAlbums.add(
        Album(
          c.toString(), c.toString(), c.toString(), index, listOf(), null, c.toString(), index
            .toString(), null
        )
      )
    }

  }

  @Test
  fun `returns Resource Success when finds an album info`() = runBlocking {
    val first = getAlbumInfo("a", "a").first()
    assert(first is Resource.Success)
  }

  @Test
  fun `returns Resource Error when album info isn't found`() = runBlocking {
    val first = getAlbumInfo("z1", "z1").first()
    assert(first is Resource.Error)
    if(first is Resource.Error) {
      assertThat(first.message == "Not found")
    }
  }

  @Test
  fun `returns Resource Error when exception is thrown`() = runBlocking {
    val first = getAlbumInfo("undefined", "undefined").first()
    assert(first is Resource.Error)
    if(first is Resource.Error) {
      assertThat(first.message == "An unexpected error occurred. Please try again later")
    }
  }

  @Test
  fun `returns Resource NetworkError 401 when hostname isn't find and album is saved`() =
    runBlocking {
    val first = getAlbumInfo("401", "401").first()
    assertThat(first is Resource.NetworkError)
    if(first is Resource.NetworkError) {
      assertThat(first.errorCode == 401)
    }
  }

  @Test
  fun `returns Resource NetworkError 404 when hostname isn't find and album isn't saved`() =
    runBlocking {
    val first = getAlbumInfo("404", "404").first()
    assertThat(first is Resource.NetworkError)
    if(first is Resource.NetworkError) {
      assertThat(first.errorCode == 404)
    }
  }

}
