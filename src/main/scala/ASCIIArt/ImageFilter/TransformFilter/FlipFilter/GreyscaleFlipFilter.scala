package ASCIIArt.ImageFilter.TransformFilter.FlipFilter

import ASCIIArt.Image.PixelGrid.Pixel.GreyscalePixel
import ASCIIArt.Image.PixelGrid.PixelGrid
import ASCIIArt.Image.{GreyscaleImage, Image}

/**
 * Specialized flip filter class for Greyscale images
 * Implements the flip filter abstract methods for all axes
 * @param axis
 */
class GreyscaleFlipFilter(axis: FlipAxisEnum)
    extends FlipFilter[GreyscalePixel](axis) {
  override def XFlip(image: Image[GreyscalePixel]): Image[GreyscalePixel] = {
    var newPixelGrid = new PixelGrid[GreyscalePixel]()
    for (y <- 0 until image.getHeight()) {
      var newPixelLine = Vector[GreyscalePixel]()
      for (x <- 0 until image.getWidth())
        newPixelLine =
          newPixelLine.appended(image.getPixel(x, image.getHeight() - y - 1))
      newPixelGrid = newPixelGrid.appended(newPixelLine)
    }
    GreyscaleImage(newPixelGrid)
  }

  override def YFlip(image: Image[GreyscalePixel]): Image[GreyscalePixel] = {
    var newPixelGrid = new PixelGrid[GreyscalePixel]()
    for (y <- 0 until image.getHeight()) {
      var newPixelLine = Vector[GreyscalePixel]()
      for (x <- 0 until image.getWidth())
        newPixelLine =
          newPixelLine.appended(image.getPixel(image.getWidth() - x - 1, y))
      newPixelGrid = newPixelGrid.appended(newPixelLine)
    }
    GreyscaleImage(newPixelGrid)
  }
}
