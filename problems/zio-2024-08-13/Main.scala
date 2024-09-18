//> using dep dev.zio::zio:2.1.9

package scalarank.zio_2024_08_13

import zio.*

import java.io.File
import java.util.stream.Collectors
import scala.io.BufferedSource

def listTextFiles(path: FileName): Task[Seq[File]] = ZIO
  .attemptBlocking(new File(path).listFiles)
  .map(_.toSeq.filter(f => f.getName.endsWith(".txt")))

def getFileContents(file: File): Task[FileContents] =
  ZIO.attemptBlocking(
    FileContents(scala.io.Source.fromFile(file).getLines().mkString)
  )

def countWordsInFile(
    file: File
): Task[(FileName, WordCount)] =
  getFileContents(file).map[(FileName, WordCount)]((fileContents: String) =>
    (FileName(file.getName), WordCount(countWords(fileContents)))
  )

def countWords(fileContents: FileContents): WordCount = WordCount(
  fileContents.split("\\s").length
)

def makeWordCountFibers(
    matchingFiles: Seq[File]
): Task[Seq[Fiber.Runtime[Throwable, (FileName, WordCount)]]] =
  ZIO.collectAllPar(matchingFiles.map(file => countWordsInFile(file).fork))

def prettyPrintFileWordCount(fileName: FileName, wordCount: WordCount) =
  s"File $fileName has $wordCount words"

object Main extends ZIOAppDefault:

  def run =
    for {
      matchingFiles <- listTextFiles(FileName("./random-text-files"))
        .map(_.sortBy(_.getName))
      fibers <- makeWordCountFibers(matchingFiles)
      counts <- ZIO
        .foreachPar(fibers)(_.join)
        .map(_.map(prettyPrintFileWordCount))
      _ <- ZIO.foreach(counts)(x => Console.printLine(x))
    } yield ()

// File 1.txt has 4400 words
// File 2.txt has 4485 words
// File 3.txt has 1807 words

opaque type FileName = String
object FileName:
  def apply(s: String): FileName = s

opaque type FileContents = String
object FileContents:
  def apply(s: String): FileContents = s

opaque type WordCount = Int
object WordCount:
  def apply(c: Int): WordCount = c
