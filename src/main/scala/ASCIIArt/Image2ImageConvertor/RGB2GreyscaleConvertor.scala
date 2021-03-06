package ASCIIArt.Image2ImageConvertor

import ASCIIArt.Image.PixelGrid.Pixel.{GreyscalePixel, RGBPixel}
import ASCIIArt.Image.PixelGrid.PixelGrid
import ASCIIArt.Image.{GreyscaleImage, Image}

/**
 * Image2ImageConvertor specialization for conversion of RGB images to Greyscale images using given scales of conversion
 * @param redScale
 * @param greenScale
 * @param blueScale
 */
class RGB2GreyscaleConvertor(
  val redScale: Double = 0.3,
  val greenScale: Double = 0.59,
  val blueScale: Double = 0.11)
    extends Image2ImageConvertor[RGBPixel, GreyscalePixel] {

  override def Convert(image: Image[RGBPixel]): Image[GreyscalePixel] = {
    var newPixelGrid = new PixelGrid[GreyscalePixel]()
    for (y <- 0 until image.getHeight()) {
      var newPixelLine = Vector[GreyscalePixel]()
      for (x <- 0 until image.getWidth())
        newPixelLine = newPixelLine.appended(ConvertPixel(image.getPixel(x, y)))
      newPixelGrid = newPixelGrid.appended(newPixelLine)
    }
    GreyscaleImage(newPixelGrid)
  }

  def ConvertPixel(pixel: RGBPixel): GreyscalePixel = {
    val red = pixel.red * redScale
    val green = pixel.green * greenScale
    val blue = pixel.blue * blueScale
    GreyscalePixel((red + green + blue).toInt).clamp()
  }
}
