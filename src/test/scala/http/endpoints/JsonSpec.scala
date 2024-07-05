package http.endpoints

import domain.views.MediaTypeViewWithIron.NonEmptyString
import domain.views.{MediaTypeView, MediaTypeViewWithIron}
import helpers.Generators
import zio.*
import zio.json.*
import zio.test.*
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.Empty
import io.github.iltotore.iron.constraint.string.Trimmed

import java.nio.charset.Charset

object JsonSpec extends ZIOSpecDefault with Generators {

  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("Json examples")(
      test("can convert a type into a json string") {
        check(mediaTypeGen) { mediaType =>
          val jsonString = mediaType.toJsonPretty
          val expectedRes: String =
            s"""
               |{
               |  "typeOf" : "${mediaType.typeOf}"
               |}
               |""".stripMargin.trim

          assertTrue(jsonString == expectedRes)
        }
      },
      test("can convert a json string into a case class") {
        check(mediaTypeGen) { mediaType =>
          val jsonString: String =
            s"""
               |{
               |  "typeOf" : "${mediaType.typeOf}"
               |}
               |""".stripMargin.trim

          assertTrue(
            jsonString.fromJson[MediaTypeView]
              .contains(mediaType)
          )
        }
      },
      test("can convert a json string into a case class when using iron/refined types") {
        check(mediaTypeWithIronGen) { mediaType =>
          val jsonString: String =
            s"""
               |{
               |  "typeOf" : "${mediaType.typeOf}"
               |}
               |""".stripMargin.trim

          assertTrue(
            jsonString.fromJson[MediaTypeViewWithIron]
              .contains(mediaType)
          )
        }
      },
      test("can convert a type into a json string [with iron]") {
        check(mediaTypeWithIronGen) { mediaType =>
          val expectedJsonString: String =
            s"""
               |{
               |  "typeOf" : "${mediaType.typeOf}"
               |}
               |""".stripMargin.trim

          val result = mediaType.toJsonPretty

          assertTrue(
            result == expectedJsonString
          )
        }
      },
      test("errors when the json body doesn't align with the refined type") {
        val jsonString: String =
          s"""
             |{
             |  "typeOf" : "    "
             |}
             |""".stripMargin.trim

        for {
          res <- ZIO.fromEither(jsonString.fromJson[MediaTypeViewWithIron]).flip
        } yield assertTrue(
            res.contains("The provided String should not be empty or blank")
        )
      },
      test("creates the case class from the provided json string") {
        val jsonString: String =
          s"""
             |{
             |  "typeOf" : "DVD"
             |}
             |""".stripMargin.trim


        for {
          res <- ZIO.fromEither(jsonString.fromJson[MediaTypeViewWithIron])
          refinedString <- ZIO.fromEither("DVD".refineEither[NonEmptyString])
        } yield assertTrue(
            MediaTypeViewWithIron(refinedString) == res
        )
      }
    )

}
