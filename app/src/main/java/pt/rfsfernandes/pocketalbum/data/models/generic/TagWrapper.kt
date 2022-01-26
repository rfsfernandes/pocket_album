package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class TagWrapper created at 22/01/2022 22:32 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class TagWrapper(

  @SerializedName("tag") val tag: List<Tag> = listOf()

)