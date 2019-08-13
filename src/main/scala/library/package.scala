import java.util.UUID

import cats.free.Free
import cats.free.Free.liftF

package object library {
  type BookRepository[A] = Free[BookRepositoryA, A]

  def save(book: Book): BookRepository[Book] = liftF[BookRepositoryA, Book](Save(book))

  def update(book: Book): BookRepository[Book] = liftF[BookRepositoryA, Book](Update(book))

  def removeById(id: UUID): BookRepository[Book] = liftF[BookRepositoryA, Book](RemoveById(id))

  def findById(id: UUID): BookRepository[Book] = liftF[BookRepositoryA, Book](FindById(id))

  def findAll(): BookRepository[List[Book]] = liftF[BookRepositoryA, List[Book]](FindAll())

  def findBy(
              title: Option[String] = None,
              year: Option[Int] = None,
              author: Option[String] = None
            ): BookRepository[List[Book]] = liftF[BookRepositoryA, List[Book]](FindBy(title, year, author))
}
