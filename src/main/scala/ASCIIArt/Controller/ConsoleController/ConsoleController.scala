package ASCIIArt.Controller.ConsoleController

import ASCIIArt.Controller.Command.ExportCommand.{ConsoleOutputCmd, ExportCommand, FileOutputCmd}
import ASCIIArt.Controller.Command.FilterCommand._
import ASCIIArt.ImageImporter.FileImageImporter.{GIFFileImageImporter, JPGFileImageImporter, PNGFileImageImporter}
import ASCIIArt.ImageImporter.ImageImporter
import ASCIIArt.ImageImporter.RandomImageImporter.RandomImageImporter

import scala.util.control.Breaks.break

/**
 * Object for parsing commands given in a List[String] of arguments
 * Parses and return an ImageImporter, a List of filter commands and a List of exporters
 */
object ConsoleController {
  def parseArgs(args: List[String])
    : (ImageImporter, List[FilterCommand], List[ExportCommand]) = {
    var filterCommands = List[FilterCommand]()
    var exportCommands = List[ExportCommand]()
    var imageImporter: ImageImporter = new RandomImageImporter()
    var argumentsLeft = args
    var imageSetFlag = false

    while (argumentsLeft != Nil || argumentsLeft.nonEmpty) argumentsLeft match {
      case Nil => break()
      case "--image-random" :: tail =>
        if (imageSetFlag) throw new Exception("Image already set!")
        imageSetFlag = true
        imageImporter = new RandomImageImporter()
        argumentsLeft = tail
      case "--image" :: path :: tail =>
        if (imageSetFlag) throw new Exception("Image already set!")
        imageSetFlag = true
        val format = path.takeRight(path.length - path.lastIndexOf('.') - 1)
        format match {
          case "jpg" => imageImporter = new JPGFileImageImporter(path)
          case "png" => imageImporter = new PNGFileImageImporter(path)
          case "gif" => imageImporter = new GIFFileImageImporter(path)
          case _     => throw new Exception("Unsupported image format!")
        }
        argumentsLeft = tail
      case "--brightness" :: value :: tail =>
        filterCommands = filterCommands.appended(new BrightnessCmd(value))
        argumentsLeft = tail
      case "--flip" :: value :: tail =>
        filterCommands = filterCommands.appended(new FlipCmd(value))
        argumentsLeft = tail
      case "--invert" :: tail =>
        filterCommands = filterCommands.appended(new InvertCmd())
        argumentsLeft = tail
      case "--rotate" :: value :: tail =>
        filterCommands = filterCommands.appended(new RotateCmd(value))
        argumentsLeft = tail
      case "--scale" :: value :: tail =>
        filterCommands = filterCommands.appended(new ScaleCmd(value))
        argumentsLeft = tail
      case "--output-console" :: tail =>
        exportCommands = exportCommands.appended(new ConsoleOutputCmd)
        argumentsLeft = tail
      case "--output-file" :: path :: tail =>
        exportCommands = exportCommands.appended(new FileOutputCmd(path))
        argumentsLeft = tail
      case option :: tail =>
        throw new Exception("Unknown option: " + option + "!")
    }
    if (!imageSetFlag) throw new Exception("Image not set!")
    if (exportCommands.isEmpty) throw new Exception("Exporter not set!")
    (imageImporter, filterCommands, exportCommands)
  }
}
