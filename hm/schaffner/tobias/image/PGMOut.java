package edu.hm.schaffner.tobias.image;

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
 * PGMOut builds an human readable gray-scale Image of the format .pgm
 * 
 * The PGM file format is a kind of Netpbm format. The PGMOut Class can either save the output to a
 * file or print it to the console.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Netpbm_format
 */
public class PGMOut implements Image {

  /**
   * Hands the Array to the string-builder and prints the output to System.out.
   *
   * @param raster
   *          is the 2D Int array
   * @see http://en.wikipedia.org/wiki/Euclidean_vector
   */
  public void save(final Raster raster) {

    assert raster != null : "Reference can't be null!";

    System.out.println(this.asString(raster));
  }

  /**
   * Buildes a .pgm format style String out of a 2D int Array.
   * 
   *
   * @param raster
   *          is the 2D Int array
   * @return A String in PGM style
   * @see http://en.wikipedia.org/wiki/Euclidean_vector
   */
  public String asString(final Raster raster) {

    assert raster != null : "Reference can't be null!";

    // We don't allow Arrays with a x or y length of 0
    if (raster.getWidth() < 1 || raster.getHeight() < 1) {
      throw new IllegalArgumentException();
    }

    final StringBuilder result = new StringBuilder();

    // The PGM Info
    result.append("P2\n").append(raster.getWidth()).append(' ').append(raster.getHeight())
        .append("\n255\n");

    Stream
        .iterate(0, naturalNumber -> naturalNumber + 1)
        .limit(raster.getHeight())
        .forEach(
            xValue -> {
              Stream.iterate(0, naturalNumber -> naturalNumber + 1).limit(raster.getWidth())
                  .forEach(yValue -> {
                    result.append(String.format("%3d ", raster.getPixel(yValue, xValue)));
                    if (yValue % raster.getWidth() == raster.getWidth() - 1)
                      result.append(System.getProperty("line.separator"));
                  });
            });

    return result.toString();

  }
}
