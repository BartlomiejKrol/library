package library

case class Book(
                 title: String,
                 year: Int,
                 author: String,
                 status: BookStatus = Available,
                 id: Option[String] = None
               )