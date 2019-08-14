package library

import java.util.UUID

sealed trait BookRepositoryA[A]

case class Save(book: Book) extends BookRepositoryA[Book]

case class Update(book: Book) extends BookRepositoryA[Book]

case class RemoveById(id: UUID) extends BookRepositoryA[Book]

case class FindById(id: UUID) extends BookRepositoryA[Book]

case class FindAll() extends BookRepositoryA[List[Book]]

case class FindBy(title: Option[String], year: Option[Int], author: Option[String]) extends BookRepositoryA[List[Book]]

case class ShowBooksDistinct(books: List[Book]) extends BookRepositoryA[Unit]

case class ShowBooks(books: List[Book]) extends BookRepositoryA[Unit]

case class ShowOneBook(book: Book) extends BookRepositoryA[Unit]

case class GetAvailable(book: Book) extends BookRepositoryA[Book]