package library

import java.util.UUID

case class Book(
                 title: String,
                 year: Int,
                 author: String,
                 status: BookStatus = Available,
                 id: Option[UUID] = None
               )