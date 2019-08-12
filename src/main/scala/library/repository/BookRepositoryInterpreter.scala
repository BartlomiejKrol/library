package library.repository

import java.util.UUID

import cats.Applicative
import cats.implicits._
import library.Book

import scala.collection.concurrent.TrieMap

class BookRepositoryInterpreter[F[_] : Applicative] extends BookRepository[F] {

  private val repository = new TrieMap[UUID, Book]

  override def save(book: Book): F[Book] = {
    val id = UUID.randomUUID()
    val entity = book.copy(id = id.some)
    repository += (id -> entity)
    entity.pure[F]
  }

  override def update(book: Book): F[Option[Book]] = book.id.traverse(
    id => {
      repository.update(id, book)
      book.pure[F]
    }
  )

  override def removeById(id: UUID): F[Option[Book]] = repository.remove(id).pure[F]

  override def findById(id: UUID): F[Option[Book]] = repository.get(id).pure[F]

  override def findAll(): F[List[Book]] = repository.values.toList.pure[F]

  override def findBy(title: Option[String], year: Option[Int], author: Option[String]): F[List[Book]] =
    repository.values.toList
      .filter(book => title.forall(_ == book.title))
      .filter(book => year.forall(_ == book.year))
      .filter(book => author.forall(_ == book.author))
      .pure[F]
}
