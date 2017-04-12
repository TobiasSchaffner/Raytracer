package edu.hm.schaffner.tobias.raster;

import java.util.Arrays;

import edu.hm.schaffner.tobias.tracer.Raytracer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
 * Interface Raster. The interface for Raster defines the needed Methods to get the height width a
 * pixel as well as to generate the content of the raster and the factory Methods to build a
 * Raster(s) with the given parameters.
 * 
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see Referenzen
 */
@SuppressFBWarnings("UCF_USELESS_CONTROL_FLOW")
public interface Raster {

  /** Size of the default Arrayraster. */
  int DEFAULTSIZE = 128;

  /** Length of the allowed Arguments. */
  int ARGLENGTH = 3;

  /**
   * ViewPortResolution Width.
   * 
   * @return Value as int
   */
  int getWidth();

  /**
   * ViewPortResolution Height.
   * 
   * @return Value as int
   */
  int getHeight();

  /**
   * ViewPortPixel for a relative Value.
   * 
   * @param yPosition
   *          the relative xPosition
   * @param xPosition
   *          the relative yPosition
   * @return Value as int
   */
  int getPixel(int yPosition, int xPosition);

  /**
   * The method generates an ASCII portable gray map (pgm) as a String.
   * 
   * @param rayTray
   *          the Raytracer used for the calculations. of the viewport. (x = y)
   * @return result is a 2D int Array with the brightness Values 0(black) and 255(white) in it.
   */
  Raster render(Raytracer rayTray);

  /**
   * Generates new Raster.
   * 
   * @param args
   *          : 1.String as kind of Raster and then needed Parameters for the wanted Raster. If the
   *          String is empty this methods generates a default Raster. (An Arrayraster with the
   *          Resolution 128x128)
   * @return New Raster
   * @throws ClassNotFoundException
   *           if the provided Class is unknown
   */
  static Raster make(String... args) throws ClassNotFoundException {

    assert args != null : "Reference can't be null!";

    if (args[0].isEmpty() && args.length == 1) {
      return new ArrayRaster(DEFAULTSIZE, DEFAULTSIZE);
    }

    if ("ArrayRaster".equals(args[0]) && args.length == ARGLENGTH) {
      return new ArrayRaster(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }

    if ("Supersampled".equals(args[0])) {
      return new Supersampled(Raster.make(Arrays.copyOfRange(args, 1, args.length)));
    }

    if ("ParallelRaster".equals(args[0]) && args.length == ARGLENGTH + 1) {
      return new ParallelRaster(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
          Integer.parseInt(args[ARGLENGTH]));
    }

    if ("ThreadIdRaster".equals(args[0]) && args.length == ARGLENGTH + 1) {
      return new ThreadIdRaster(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
          Integer.parseInt(args[ARGLENGTH]));
    }

    else {
      throw new ClassNotFoundException();
    }
  }

}