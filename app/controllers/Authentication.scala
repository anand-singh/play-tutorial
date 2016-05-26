package controllers

import javax.inject.{Inject, Singleton}

import domains.User
import play.api.cache.CacheApi
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by anand on 5/26/16.
  */
@Singleton
class Authentication @Inject()(cache: CacheApi) extends Controller {

  /**
    * This is used to get username from request
    */
  def username(request: RequestHeader): Option[String] = request.session.get(Security.username)

  /**
    * This is used to redirect unauthorized access to login page
    */
  def unauthorized(request: RequestHeader): Result = Results.Unauthorized

  /**
    * This method is use to validate request
    */
  def withAuth(f: => User => Request[AnyContent] => Future[Result]): Action[AnyContent] = Action.async {
    implicit request =>
      username(request).map { id =>
        cache.get[User](id) match {
          case Some(user: User) => f(user)(request)
          case _ => Future.successful(unauthorized(request))
        }
      }.getOrElse(Future.successful(unauthorized(request)))
  }

}
