package pt.rfsfernandes.pocketalbum.domain.use_cases

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.repository.FakeRepositoryImpl

/**
 *   Class AddAlbumToListTest created at 02/02/2022 22:38 for the project PocketAlbum
 *   By: rodrigofernandes
 */

class AddAlbumToListTest {

  private lateinit var addAlbumToList: AddAlbumToList
  private lateinit var fakeRepositoryImpl: FakeRepositoryImpl

  @Before
  fun setUp() {
    fakeRepositoryImpl = FakeRepositoryImpl()
    addAlbumToList = AddAlbumToList(fakeRepositoryImpl)

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
  fun `add album to list and check if its there and dont add if already exists`() = runBlocking {
    val albumToAdd = Album("Test album")


    addAlbumToList(albumToAdd)

    assert(fakeRepositoryImpl.storedAlbums.contains(albumToAdd))

    val sizeBefore = fakeRepositoryImpl.storedAlbums.size
    addAlbumToList(albumToAdd)

    assert(fakeRepositoryImpl.storedAlbums.size == sizeBefore)

  }

}
