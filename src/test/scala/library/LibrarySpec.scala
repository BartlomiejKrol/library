package library

import org.scalatest.FlatSpec

class LibrarySpec extends FlatSpec {

  "At the beginning library" should "contains no books" in {}

  "Each book in the application" should "have unique id" in {}

  "The library" should "allow to add new book" in {}

  "The existing, not lent book" should "be removable from the library by id" in {}

  "The existing lent book" should " not be removable from the library" in {}

  "Removing non existing book" should "not be possible" in {}

  "The library" should "allow to list all books in the library distincly with information how many is available or lent" in {}

  it should "allow to lend a book by id" in {}

  it should "allow to see all book's details by id" in {}

}
