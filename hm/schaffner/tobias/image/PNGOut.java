package edu.hm.schaffner.tobias.image;

/* (C) 2015, R. Schiedermeier, rs@cs.hm.edu
 * Oracle Corporation Java 1.8.0_05, Linux i386 3.19.3
 * lilli (Intel CPU U7300/1300 MHz, 2 Cores, 4096 MB RAM)
 */
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import edu.hm.schaffner.tobias.raster.Raster;

/* 
 * Organization: HM, FK07
 * Project: Softwareentwicklung 2, Praktikum
 * 
 * Authors:
 * 
 * Tobias Schaffner
 * Java 1.8.0_31, Windows 7 - 32bit
 * Intel(R) Core(TM) i5-4210U CPU @ 1.70GHz 2.38 GHz, 3GB RAM
 * 
 * Deniz Oktay
 * Windows 7 Professional - 64bit
 * AMD A6-6310 APU with Amd Radeon R4 Graphics 1.80, 8GB RAM 
 */

/**
 * PNGOut creates an Image of the kind PNG.
 * 
 * PNG is a loss less raster graphics file format. PNGout defines a method to save an Image out of a
 * raster and provides factory-methods to create an Image with the given parameters.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Portable_Network_Graphics
 */
public class PNGOut implements Image {

  /**
   * filename.
   */
  private final String fileName;

  /**
   * Custom CTor.
   * 
   * @param fileName
   *          as Storageplace and name; e.g. "//tmp/ball.png"
   */
  public PNGOut(final String fileName) {
    
    assert fileName != null : "Reference can't be null!";
    
    this.fileName = fileName;
  }

  /**
   * DefaultCTor. Sets filename "picture.png"
   */
  public PNGOut() {
    fileName = "picture.png";
  }

  /**
   * Entry-Point. Schreibt ein monochromes png-File. Beispiel: java PNGFileDemo 640 480 224
   * lightgray.png.
   *
   * @param raster
   *          Enth�lt die Bildbreite in Pixel (positiv). Bildhoehe in Pixel (positiv). 3.
   *          Pixelhelligkeit zwischen 0 = schwarz und 255 = weiss. 4. Name der Datei, die das
   *          Programm schreibt.
   * @throws IOException
   *           wenn die Datei nicht geschrieben werden kann.
   */
  public void save(Raster raster) throws IOException {

    assert raster != null : "Reference can't be null!";
    
    final int width = raster.getWidth();
    final int height = raster.getHeight();

    final BufferedImage image = new BufferedImage(width, height, TYPE_INT_ARGB);

    Stream
    .iterate(0, naturalNumber -> naturalNumber + 1)
    .limit(height)
    .forEach(
        xValue -> {
          Stream.iterate(0, naturalNumber -> naturalNumber + 1).limit(width)
              .forEach(yValue -> {
                image.setRGB(xValue, yValue, byteToARGB(raster.getPixel(height - yValue - 1, xValue)));
              });
        });

    ImageIO.write(image, "png", new File(fileName));
  }

  /**
   * Berechnet aus einer Helligkeit den korrespondierenden ARGB-Farbwert.
   *
   * @param brightness
   *          Helligkeit zwischen 0 = schwarz und 255 = weiss.
   * @return Farbwert im ARGB-Format.
   */
  private static int byteToARGB(int brightness) {
    final int bitsInByte = 8;
    final int opaqueBitmask = 0xFF;
    return ((opaqueBitmask << bitsInByte | brightness) << bitsInByte | brightness) << bitsInByte
        | brightness;
  }
}

