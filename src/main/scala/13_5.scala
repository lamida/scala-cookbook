package actortests.parentchild

import akka.actor._

case class CreateChild (name: String)
case class Name (name: String)

class Child extends Actor {
  var name = "No name"
  override def postStop {
    println(s"D'oh! They killed me ($name): ${self.path}")
  }
  def receive = {
    case Name(name) => this.name = name
    case _ => println(s"Child $name got message")
  }
}

class Parent extends Actor {
  def receive = {
    case CreateChild(name) =>
      println(s"Parent about to create Child ($name) ...")
      val child = context.actorOf(Props[Child], name = s"$name")
      child ! Name(name)

    case _ => println(s"Parent got some other messsage.")
  }
}

object ParentChildDemo extends App {
  val actorSystem = ActorSystem("ParentChildTest")
  val parent = actorSystem.actorOf(Props[Parent], name = "Parent")

  parent ! CreateChild("Jonanthan")
  parent ! CreateChild("Jordan")
  Thread.sleep(500)

  println("Sending Jonanthan a PoisonPill ...")
  val jonathan = actorSystem.actorSelection("/user/Parent/Jonanthan")
  jonathan ! PoisonPill
  println("jonathan was killed")

  Thread.sleep(5000)
  actorSystem.shutdown
}


