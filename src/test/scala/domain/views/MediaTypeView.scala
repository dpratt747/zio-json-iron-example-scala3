package domain.views

import zio.json.*

final case class MediaTypeView(typeOf: String)

object MediaTypeView {
  given mediaTypesDecoder: JsonDecoder[MediaTypeView] = DeriveJsonDecoder.gen[MediaTypeView]
  given mediaTypesEncoder: JsonEncoder[MediaTypeView] = DeriveJsonEncoder.gen[MediaTypeView]
}