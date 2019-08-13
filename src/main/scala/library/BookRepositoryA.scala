package library

import java.util.UUID

sealed trait BookRepositoryA[A]

case class Save(book: Book) extends BookRepositoryA[Book]

case class Update(book: Book) extends BookRepositoryA[Book]

case class RemoveById(id: UUID) extends BookRepositoryA[Book]

case class FindById(id: UUID) extends BookRepositoryA[Book]

case class FindAll() extends BookRepositoryA[List[Book]]

case class FindBy(title: Option[String], year: Option[Int], author: Option[String]) extends BookRepositoryA[List[Book]]

case class ListBooksDistinctly() extends BookRepositoryA[Unit]
