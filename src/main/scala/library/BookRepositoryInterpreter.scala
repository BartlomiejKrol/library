package library

import java.util.UUID

import cats.implicits._
import cats.~>

import scala.collection.concurrent.TrieMap

class BookRepositoryInterpreter(val repository: TrieMap[UUID, Book]) extends (BookRepositoryA ~> Option) {

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

    case FindById(id) => repository.get(id)

    case RemoveById(id) => repository.get(id) match {
      case Some(book) => {
        repository.remove(id)
        book.some
      }
      case _ => None
    }

    case FindAll() => {
      if (repository.values.isEmpty) {
        None
      } else {
        repository.values.toList.some
      }
    }

    case FindBy(titleOpt, yearOpt, authorOpt) =>
      repository.values
        .filter(book => titleOpt.forall(_ == book.title))
        .filter(book => yearOpt.forall(_ == book.year))
        .filter(book => authorOpt.forall(_ == book.author))
        .toList
        .some

    case ShowBooks(books) => if (books.isEmpty) {
      None
    } else {
      books.foreach(book => {
        val id = book.id.map(_.toString).getOrElse("missing")
        val status = book.status match {
          case Available => "available"
          case Lent(user) => s"lent by user $user"
          case _ => "unknown"
        }
        println(s" id: $id,  title: ${book.title}, year: ${book.year}, auhtor: ${book.author}, status $status")
      }).some
    }

    case ShowBooksDistinct(books) =>
      if (books.isEmpty) {
        None
      }
      else {
        books
          .groupBy(book => (book.title, book.year, book.author))
          .view.mapValues(
          _.foldRight((0, 0): (Int, Int))(
            (book: Book, args: (Int, Int)) => book.status match {
              case Available => (args._1 + 1, args._2)
              case Lent(_) => (args._1, args._2 + 1)
              case _ => args
            }))
          .foreach(entry => println(s"title: ${entry._1._1}, year: ${entry._1._2}, auhtor: ${entry._1._3}, available: ${entry._2._1}, lent: ${entry._1._2}"))
          .some
      }

    case ShowOneBook(book: Book) =>
      val status = book.status match {
        case Available => "available"
        case Lent(user) => s"lent by user: $user"
        case _ => "status is missing"
      }
      print(s"title: ${book.title}, year: ${book.year}, auhtor: ${book.author}, status: $status").some

    case GetAvailable(book: Book) => book.status match {
      case Available => book.some
      case _ => None
    }

  }
}
