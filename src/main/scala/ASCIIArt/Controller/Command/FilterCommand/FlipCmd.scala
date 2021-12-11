package ASCIIArt.Controller.Command.FilterCommand

import ASCIIArt.Image.PixelGrid.Pixel.GreyscalePixel
import ASCIIArt.Image.{GreyscaleImage, Image}
import ASCIIArt.ImageFilter.TransformFilter.FlipFilter.{FlipX, FlipY, GreyscaleFlipFilter}

class FlipCmd(axis: String) extends FilterCommand {
  override def Execute(target: Image[GreyscalePixel]): Image[GreyscalePixel] = {
    axis match {
      case "x" => new GreyscaleFlipFilter(FlipX).apply(target)
      case "y" => new GreyscaleFlipFilter(FlipY).apply(target)
      case _   => throw new Exception("Invalid axis!")
    }
  }
}