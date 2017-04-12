package edu.hm.schaffner.tobias.raster;

import edu.hm.schaffner.tobias.tracer.Raytracer;

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
 * A supersampling raster will for every Pixel take 4 pixels of the underneath raster and return the
 * average. Thats why it always needs a "real" raster to revere to. Height and with will be half of
 * the one below. Render will handed through.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Supersampling
 */
public class Supersampled implements Raster {

  /** A constant for the number of pixels to devide through. */
  private static final double SAMPLE_SIZE = 4.0;

  /** We add 0.5 to round by casting to int. */
  private static final double ROUND_ADD = 0.5;

  /**
   * The given Raster, to be supersampled.
   */
  private final Raster givenRaster;
  /**
   * New width of the supersampled Raster, half as big as the width of the given Raster.
   */
  private final int width;
  /**
   * New height of the supersampled Raster, half as big as the height of the given Raster.
   */
  private final int height;

  /**
   * Constructor for the new supersampled Raster. The hight and the width of the givenRaster must be
   * even, because the the width and the hight of the new Raster will only be half as big.
   * 
   * @param givenRaster
   *          the raster to be supersampled.
   */
  public Supersampled(Raster givenRaster) {

    assert givenRaster != null : "Reference can't be null!";
    if (givenRaster.getWidth() % 2 != 0 || givenRaster.getHeight() % 2 != 0)
      throw new IllegalArgumentException();
    this.givenRaster = givenRaster;
    this.width = givenRaster.getWidth() / 2;
    this.height = givenRaster.getHeight() / 2;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Overwritten get Pixel Method. We scale the Point to the Area on the Raster underneith and get
   * the pixels right below and one to the right one down. We add 0.5 and cast to int to round to
   * the right value.
   * 
   * @returns the brightness level of the supersampled pixel.
   * @param the
   *          yPosition on the raster.
   * @param the
   *          xPosition on the raster.
   */
  @Override
  public int getPixel(int yPosition, int xPosition) {
    if (yPosition >= this.getWidth() || xPosition >= this.getHeight())
      throw new IllegalArgumentException();

    final double result = (givenRaster.getPixel(yPosition * 2, xPosition * 2)
        + givenRaster.getPixel(yPosition * 2 + 1, xPosition * 2)
        + givenRaster.getPixel(yPosition * 2, xPosition * 2 + 1) + givenRaster.getPixel(
        yPosition * 2 + 1, xPosition * 2 + 1)) / SAMPLE_SIZE;

    return (int) (result + ROUND_ADD);
  }

  @Override
  public Raster render(Raytracer rayTracer) {

    assert rayTracer != null : "Reference can't be null!";

    givenRaster.render(rayTracer);
    return this;
  }
}
