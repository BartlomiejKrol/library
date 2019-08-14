package library

import cats.implicits._
import org.scalatest.FlatSpec

import scala.collection.concurrent.TrieMap

class LibrarySpec extends FlatSpec {


  val testBookWithId1 = Book("book", 2019, "author", id = "1".some)
  val testBookWithId2 = Book("book2", 2019, "author", id = "2".some)
  val testBookWithId3 = Book("book3", 2020, "different_author", id = "3".some)
  val testBook4 = Book("book4", 2019, "someone")
  val testBook5 = Book("book5", 2019, "other")
  val testBook6 = Book("book6", 2020, "different_author")

  val testLentBook = Book("lent", 1013, "Gall Anonim", id = "4".some, status = Lent("Siemomysł"))


  "At the beginning library" should "contains no books" in {
    val testSubject = new Library
    assert(testSubject.listAllBooks().isEmpty)
  }

  "The library" should "allow to add new book" in {
    val testSubject = new Library
    val addResult = testSubject.addBook("book", 2019, "author")
    assert(addResult.isDefined)
    assert(addResult.get.title == "book")
    assert(addResult.get.year == 2019)
    assert(addResult.get.author == "author")
  }

  it should "allow to list all books in the library distincly with information how many is available or lent" in {
    val startState = new TrieMap[String, Book]
    val id = testBookWithId1.id.get
    startState.addOne((id, testBookWithId1))
    val interpreter = new BookRepositoryInterpreter(startState)
    val testSubject = new Library(interpreter)
    testSubject.addBook("book", 2019, "author")
    testSubject.addBook("book4", 2019, "someone")
    testSubject.addBook("book5", 2019, "other")
    testSubject.addBook("book6", 2020, "different_author")
    testSubject.lentBook(id, "tester")
    assert(testSubject.listAllBooks().nonEmpty)
  }

  it should "allow to lend a book by id" in {
    val startState = new TrieMap[String, Book]
    val id = testBookWithId1.id.get
    val tester = "Tester"
    startState.addOne((id, testBookWithId1))
    val interpreter = new BookRepositoryInterpreter(startState)
    val testSubject = new Library(interpreter)
    assert(
      testSubject.lentBook(id, tester)
        .contains(testBookWithId1.copy(status = Lent(tester)))
    )
  }

  it should "allow to see all book's details by id" in {
    val startState = new TrieMap[String, Book]
    val id = testBookWithId1.id.get
    val idLent = testLentBook.id.get
    startState.addOne((id, testBookWithId1))
    startState.addOne((idLent, testLentBook))
    val interpreter = new BookRepositoryInterpreter(startState)
    val testSubject = new Library(interpreter)
    assert(testSubject.getBookDetails(id).contains(testBookWithId1))
    assert(testSubject.getBookDetails(idLent).contains(testLentBook))
  }

  "The existing, not lent book" should "be removable from the library by id" in {
    val startState = new TrieMap[String, Book]
    val id = testBookWithId1.id.get
    startState.addOne((id, testBookWithId1))
    val interpreter = new BookRepositoryInterpreter(startState)
    val testSubject = new Library(interpreter)
    assert(testSubject.removeBook(id).contains(testBookWithId1))
  }

  "The existing lent book" should " not be removable from the library" in {
    val startState = new TrieMap[String, Book]
    val id = testLentBook.id.get
    startState.addOne((id, testLentBook))
    val interpreter = new BookRepositoryInterpreter(startState)
    val testSubject = new Library(interpreter)
    assert(testSubject.removeBook(id).isEmpty)
  }

  "Removing non existing book" should "not be possible" in {
    val testSubject = new Library()
    testSubject.removeBook("1")
    assert(testSubject.listAllBooks().isEmpty)
  }


}
