package controllers

import javax.inject.{Inject, Singleton}

import domains.User
import play.api.cache.CacheApi
import play.api.mvc.{Action, Security}

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by anand on 5/25/16.
  */
@Singleton
class AuthController @Inject()(cache: CacheApi) extends Authentication(cache) {

  val user = User(1, "Satya", "satya@gmail.com", "satya@1234")

  def login = Action.async {
    cache.set(user.id.toString, user, 5.minutes)
    Future.successful(Ok(s"Hi ${user.name}").withSession(Security.username -> user.id.toString))
  }

  def logout = withAuth { user => implicit request =>
    cache.remove(user.id.toString)
    Future.successful(Ok("You are now logged out...").withNewSession)
  }

  def getSessionUser = withAuth { user => implicit request =>
    Future.successful(Ok(user.toJson))
  }

}
