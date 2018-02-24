package wajda

import java.io.Console
import java.security.MessageDigest

import scalaj.http.Http

object Main extends App {

  /**
   * See: https://haveibeenpwned.com/API/v2#SearchingPwnedPasswordsByRange
   */
  val HIBP_RANGE_API: String = "https://api.pwnedpasswords.com/range"
  val RANGE_PREFIX_LENGTH = 5

  val SHA1_MD = MessageDigest getInstance "SHA-1"

  Option(System.console) match {
    case None =>
      println("ERROR: Cannot open console")
      System.exit(1)

    case Some(console) =>
      val sha1Hash = readPasswordSha1(console)
      checkPawnedHashWithKAnonymity(sha1Hash)
  }

  def readPasswordSha1(console: Console): String = {
    val password = new String(console readPassword "Password:")
    (SHA1_MD digest (password getBytes "UTF-8") map ("%02x" format _)).mkString
  }

  def checkPawnedHashWithKAnonymity(sha1Hash: String) = {
    val (rangePrefix, secretSuffix) = sha1Hash.toLowerCase splitAt RANGE_PREFIX_LENGTH
    val pawnedSuffixesWithCounts = (Http(s"$HIBP_RANGE_API/$rangePrefix").asString ensuring (_.code == 200)).body.lines
    val foundPawnedCount = pawnedSuffixesWithCounts.
      map(_ split ":").
      find(_.head.toLowerCase == secretSuffix).
      map(_.last.toInt)
    foundPawnedCount match {
      case None => println(":-) OK")
      case Some(pawnedCount) => println(s":-( PAWNED !!! ${if (pawnedCount == 1) "once" else s"$pawnedCount times"}")
    }
  }

}