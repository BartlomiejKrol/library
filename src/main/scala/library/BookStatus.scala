package library

sealed trait BookStatus

case object Available extends BookStatus
case class Lent(by: String) extends BookStatus

