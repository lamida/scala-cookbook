package actortests

import akka.actor._

class TestActor extends Actor {
  def receive = {
    case _ => println("a message was received")
  }
}

object SystemStopExample extends App {
  val actorSystem = ActorSystem("SystemStopExample")
  val actor = actorSystem.actorOf(Props[TestActor], name = "test")
  actor ! "hello"

  actorSystem.stop(actor)
  actorSystem.shutdown
}

