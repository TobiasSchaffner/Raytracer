package edu.hm.schaffner.tobias.image;

import edu.hm.schaffner.tobias.raster.Raster;

import java.io.IOException;
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
 * Interface for Image Classes like PGMOut or PNGOut The Interface defines the save Method to save
 * an Image as well as the Fabric Methods to define an Image Object with the given parameters.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
@SuppressFBWarnings("UCF_USELESS_CONTROL_FLOW")
public interface Image {

  /**
   * The save Method to build the Picture.
   * 
   * @param raster
   *          the provided Raster to make the Image of.
   * @throws IOException
   *           InputOutput Error.
   */
  void save(Raster raster) throws IOException;

  /**
   * Generates new Image.
   * 
   * @param args
   *          The Parameters for the Image like kind or path. If the String is empty this methods
   *          generates a default Image. (A PNGOut())
   * @return New Image
   * @throws ClassNotFoundException
   *           if the Class provided is unknown
   */
  static Image make(String... args) throws ClassNotFoundException {

    assert args != null : "Reference can't be null!";

    if (args[0].isEmpty() || "PGMOut".equals(args[0])) {
      return makePGM(args);
    }

    if ("PNGImage".equals(args[0])) {
      return makePNG(args);
    }

    else {
      throw new ClassNotFoundException();
    }
  }

  /**
   * Making the PGM Images.
   * 
   * @param args
   *          provided Arguments by make-Method
   * @return PGM Image
   * @throws IllegalArgumentException
   *           if the Argument doesnt fit.
   */
  static Image makePGM(String... args) throws IllegalArgumentException {
    if (args.length == 1)
      return new PGMOut();
    else
      throw new IllegalArgumentException();
  }

  /**
   * Making the PNG Images.
   * 
   * @param args
   *          provided Arguments by make-Method
   * @return PNG Image
   * @throws IllegalArgumentException
   *           if the Argument doesnt fit.
   */
  static Image makePNG(String... args) throws IllegalArgumentException {
    if (args.length == 1)
      return new PNGOut();
    if (args.length == 2)
      return new PNGOut(args[1]);
    else
      throw new IllegalArgumentException();
  }

}
