package library

import cats.implicits._

import scala.collection.concurrent.TrieMap

class Library(val repositoryInterpreter: BookRepositoryInterpreter = new BookRepositoryInterpreter(new TrieMap[String, Book])) {

  def addBook(title: String, year: Int, author: String): Option[Book] = {
    val book = Book(title, year, author)
    val action = for {
      newBook <- save(book)
      _ <- showOneBook(newBook)
    } yield newBook
    action.foldMap(repositoryInterpreter)
  }

  def removeBook(id: String): Option[Book] = {
    val action = for {
      existing <- findById(id)
      _ <- getAvailable(existing)
      removed <- removeById(id)
    } yield removed
    action.foldMap(repositoryInterpreter)
  }

  def listAllBooks(): Option[Unit] = {
    val action = for {
      all <- findAll()
      _ <- showBooksDistinct(all)
    } yield ()
    action.foldMap(repositoryInterpreter)
  }

  def findByTitle(title: String): Option[List[Book]] = {
    val action = for {
      books <- findBy(title = title.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def findByYear(year: Int): Option[List[Book]] = {
    val action = for {
      books <- findBy(year = year.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def findByAuthor(author: String): Option[List[Book]] = {
    val action = for {
      books <- findBy(author = author.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }


  def findByTitleAndYear(title: String, year: Int): Option[List[Book]] = {
    val action = for {
      books <- findBy(title = title.some, year = year.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def findByTitleAndAuthor(title: String, author: String): Option[List[Book]] = {
    val action = for {
      books <- findBy(title = title.some, author = author.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def findByYearAndAuthor(year: Int, author: String): Option[List[Book]] = {
    val action = for {
      books <- findBy(year = year.some, author = author.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def findByTitleAndYearAndAuthor(title: String, year: Int, author: String): Option[List[Book]] = {
    val action = for {
      books <- findBy(title.some, year.some, author.some)
      _ <- showBooks(books)
    } yield books
    action.foldMap(repositoryInterpreter)
  }

  def lentBook(id: String, by: String): Option[Book] = {
    val action = for {
      existing <- findById(id)
      available <- getAvailable(existing)
      updated <- update(available.copy(status = Lent(by)))
    } yield updated
    action.foldMap(repositoryInterpreter)
  }

  def getBookDetails(id: String): Option[Book] = {
    val action = for {
      book <- findById(id)
      _ <- showOneBook(book)
    } yield book
    action.foldMap(repositoryInterpreter)
  }
}
