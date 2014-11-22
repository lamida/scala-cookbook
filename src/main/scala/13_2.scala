import akka.actor._

class HelloActor2(myName: String) extends Actor {
  def receive = {
    case "hello" => println(s"hello from $myName")
    case _ => println(s"'huh?', said $myName")
  }
}

object Main2 extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props(new HelloActor2("Fred")), name = "helloactor")

  helloActor ! "hello"
  helloActor ! "buenos dias"

  system.shutdown
}


