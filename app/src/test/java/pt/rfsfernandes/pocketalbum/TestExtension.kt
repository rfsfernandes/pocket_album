package pt.rfsfernandes.pocketalbum

import org.junit.Test
import pt.rfsfernandes.pocketalbum.utils.toTrackTime

/**
 *   Class TestExtension created at 24/01/2022 22:35 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class TestExtension {

  @Test
  fun toTrackTime() {

    val time1 = 421
    val strTime1 = time1.toTrackTime()
    assert( strTime1 == "07:01")

    val time2 = 4700
    val strTime2 = time2.toTrackTime()
    assert( strTime2 == "01:18:20")

  }

}
