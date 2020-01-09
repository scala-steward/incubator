package com.cmartin.learn.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


//TODO documentar componentes necesarios para akka-tapir-logging
/*
  - ActuatorApi
  - ActuatorEndPoint
  - SwaggerApi
  - API Model

 */
object AkkaWebServerApp extends App { //TODO configuration trait

  val routes: Route =
    ActuatorApi.route ~
      SwaggerApi.route

  // A K K A  S Y S T E M
  implicit lazy val system: ActorSystem = ActorSystem("WebActorSystem")
  implicit val executionContext: ExecutionContext = system.dispatcher
  system.log.info(s"Starting WebServer")

  //implicit lazy val system = ActorSystem(Behaviors.empty, "HelloAkkaHttpServer")
  //implicit lazy val materializer: Materializer = Materializer(system)
  //implicit lazy val classicSystem: akka.actor.ActorSystem = system.toClassic
  //implicit val executionContext: ExecutionContext = classicSystem.dispatcher

  val futureBinding: Future[Http.ServerBinding] =
    Http()
      .bindAndHandle(routes,
        "localhost", //TODO configuration properties
        8080)

  futureBinding.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)

    case Failure(ex) =>
      system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
      system.terminate()
  }

}