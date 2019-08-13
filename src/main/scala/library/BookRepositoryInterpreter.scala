package library

import java.util.UUID

import cats.~>
import cats.implicits._

import scala.collection.concurrent.TrieMap

object BookRepositoryInterpreter extends (BookRepositoryA ~> Option) {

  val repository = new TrieMap[UUID, Book]

  override def apply[A](fa: BookRepositoryA[A]): Option[A] = fa match {
    case Save(book) => {
      val id = UUID.randomUUID()
      val entity = book.copy(id = id.some)
      if (repository.contains(id)) {
        None
      } else {
        repository += (id -> entity)
        entity.some
      }
    }
    case Update(book) => book.id match {
      case Some(id) => {
        repository.update(id, book)
        book.some
      }
      case _ => None
    }

    case FindById(id: UUID) => repository.get(id)

    case RemoveById(id: UUID) => repository.get(id) match {
      case Some(book) => {
        repository.remove(id)
        book.some
      }
      case _ => None
    }

    case FindAll() => {
      repository.values.toList.some
    }

    case FindBy(titleOpt, yearOpt, authorOpt) =>
      repository.values.toList
        .filter(book => titleOpt.forall(_ == book.title))
        .filter(book => yearOpt.forall(_ == book.year))
        .filter(book => authorOpt.forall(_ == book.author))
        .some

  }
}
