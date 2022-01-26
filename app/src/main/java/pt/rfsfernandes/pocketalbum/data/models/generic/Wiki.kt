package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class Wiki created at 22/01/2022 22:31 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class Wiki(

  @SerializedName("published") val published: String? = null,
  @SerializedName("summary") val summary: String? = null,
  @SerializedName("content") val content: String? = null

)