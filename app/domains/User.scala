package domains

import play.api.libs.json.{JsValue, Json}
import utils.JsonUtility._

/**
  * Created by anand on 5/25/16.
  */
case class User(id: Long, name: String, email: String, password: String) {

  def toJson: JsValue = Json.toJson(this)

  def toJsonString: String = toJson.toString()

}
