package library.repository

import java.util.UUID

import library.Book

trait BookRepository[F[_]] {
  def save(book: Book): F[Book]

  def update(book: Book): F[Option[Book]]

  def removeById(id: UUID): F[Option[Book]]

  def findById(id: UUID): F[Option[Book]]

  def findAll(): F[List[Book]]

  def findBy(title: Option[String] = None, year: Option[Int] = None, author: Option[String] = None): F[List[Book]]
}
