package utils

import domains.User
import play.api.libs.json.Json

/**
  * Created by anand on 5/25/16.
  */
object JsonUtility {

  implicit val userFormat = Json.format[User]

}
