## Library

The heart of a project is a service with bunch of methods to imitate 
library behavior. All methods use a domain specific language
created with the free monad construction based on the Free from scala Cats
library. As a storage the Trie Map is used and all results are lifted to an Option
monad. All errors are simply represented by None. Application
can be easily altered to use different interpreters which may provide
different storage types and different monad as containers for our values.  

### Build and test

The project use simple build tool - sbt. If you do not have it already
installed please download and install version 1.0 from [official site](https://www.scala-sbt.org/1.x/docs/Setup.html).

To build project navigate to project root directory and run command: 
`sbt compile`

To run test navigate to project root directory and run command: `sbt test` 