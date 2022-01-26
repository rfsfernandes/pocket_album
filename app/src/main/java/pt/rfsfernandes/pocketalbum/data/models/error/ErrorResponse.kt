package pt.rfsfernandes.pocketalbum.data.models.error

import com.google.gson.annotations.SerializedName

/**
 *   Class ErrorResponse created at 22/01/2022 23:22 for the project PocketAlbum
 *   By: rodrigofernandes
 */
open class ErrorResponse(

  @SerializedName("message") val message: String? = null,
  @SerializedName("error") val error: Int? = null

)