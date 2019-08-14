import cats.free.Free
import cats.free.Free.liftF

package object library {
  type BookRepository[A] = Free[BookRepositoryA, A]

  def save(book: Book): BookRepository[Book] = liftF[BookRepositoryA, Book](Save(book))

  def update(book: Book): BookRepository[Book] = liftF[BookRepositoryA, Book](Update(book))

  def removeById(id: String): BookRepository[Book] = liftF[BookRepositoryA, Book](RemoveById(id))

  def findById(id: String): BookRepository[Book] = liftF[BookRepositoryA, Book](FindById(id))

  def findAll(): BookRepository[List[Book]] = liftF[BookRepositoryA, List[Book]](FindAll())

  def findBy(
              title: Option[String] = None,
              year: Option[Int] = None,
              author: Option[String] = None
            ): BookRepository[List[Book]] = liftF[BookRepositoryA, List[Book]](FindBy(title, year, author))

  def showBooks(books: List[Book]): BookRepository[Unit] = liftF[BookRepositoryA, Unit](ShowBooks(books))

  def showBooksDistinct(books: List[Book]): BookRepository[Unit] = liftF[BookRepositoryA, Unit](ShowBooksDistinct(books))

  def showOneBook(book: Book): BookRepository[Unit] = liftF[BookRepositoryA, Unit](ShowOneBook(book))

  def getAvailable(book: Book): BookRepository[Book] = liftF[BookRepositoryA, Book](GetAvailable(book))
}
