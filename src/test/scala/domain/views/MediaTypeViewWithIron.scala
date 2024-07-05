package domain.views

import domain.views.MediaTypeViewWithIron.NonEmptyString
import zio.json.*
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.char.Whitespace
import io.github.iltotore.iron.constraint.string.*
import io.github.iltotore.iron.constraint.collection.*

final case class MediaTypeViewWithIron(typeOf: String :| NonEmptyString)

object MediaTypeViewWithIron {

  type NonEmptyString = (Not[Blank] & Not[Empty]) DescribedAs "The provided String should not be empty or blank"

  given mediaTypesDecoderForIron: JsonDecoder[String :| NonEmptyString]
  = JsonDecoder.string.mapOrFail(_.refineEither[NonEmptyString])

  given mediaTypesDecoder: JsonDecoder[MediaTypeViewWithIron] = DeriveJsonDecoder.gen[MediaTypeViewWithIron]

  given mediaTypesEncoder: JsonEncoder[MediaTypeViewWithIron] = DeriveJsonEncoder.gen[MediaTypeViewWithIron]
}
