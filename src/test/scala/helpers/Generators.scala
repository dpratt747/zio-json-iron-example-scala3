package helpers

import domain.views.*
import domain.views.MediaTypeViewWithIron.NonEmptyString
import zio.test.Gen
import io.github.iltotore.iron.*

trait Generators {
  val mediaTypeGen: Gen[Any, MediaTypeView] = Gen.alphaNumericStringBounded(1, 100).map(MediaTypeView.apply)
  
  val mediaTypeWithIronGen: Gen[Any, MediaTypeViewWithIron] = Gen.alphaNumericStringBounded(1, 100).map { stringInput =>
    MediaTypeViewWithIron.apply(stringInput.refineUnsafe[NonEmptyString])
  }
}
