package edu.hm.schaffner.tobias.raster;

import java.util.stream.Stream;

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
 * The Arrayraster Class defines an 2D Array of a given Size. Every Place in the Array will be one
 * Pixel on the ViewPort.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Viewport
 */
public class ArrayRaster implements Raster {

  /** The maximal brightness for .png Files. */
  private static final int MAXIMUM_BRIGHTNESS = 255;

  /** The half pixel we have to add to hit the middle of the Pixel. */
  private static final double HALF_PIXEL = 0.5;

  /** The Width Resolution. */
  private final int xResolution;

  /** The Height Resolution. */
  private final int yResolution;

  /** The resulting Array. */
  private final int[][] raster;

  /**
   * The Constructor generates the Array out of the Resolution.
   * 
   * @param xResolution
   *          The Width Resolution
   * @param yResolution
   *          The Height Resolution
   * 
   */
  public ArrayRaster(int xResolution, int yResolution) {

    if (xResolution < 1 || yResolution < 1) {
      throw new IllegalArgumentException();
    }

    this.xResolution = xResolution;
    this.yResolution = yResolution;

    raster = new int[yResolution][xResolution];
  }

  @Override
  public int getWidth() {
    return xResolution;
  }

  @Override
  public int getHeight() {
    return yResolution;
  }

  /**
   * Returns the brightness to the position on the raster.
   * 
   * @param yposition
   *          the position on the y scale.
   * @param xposition
   *          the position on the x scale
   * @return the brightness betweeen 0 and the set maximum. (255 for .png)
   */
  @Override
  public int getPixel(int yPosition, int xPosition) {
    if (yPosition < 0 || yPosition >= yResolution || xPosition < 0 || xPosition >= xResolution) {
      throw new IllegalArgumentException("Coordinates are not within the raster");
    }
    return raster[yPosition][xPosition];
  }

  /**
   * Sets the brightness to the position on the raster.
   * 
   * @param yPosition
   *          the position on the y scale.
   * @param xPosition
   *          the position on the x scale
   * @param brightness
   *          between 0 and the set maximum. (255 for .png)
   * 
   */
  public void setPixel(final int yPosition, final int xPosition, final int brightness) {

    assert yPosition >= 0 && yPosition < this.getHeight() : "yPosition out of range";
    assert xPosition >= 0 && xPosition < this.getWidth() : "xPosition out of range";
    assert brightness >= 0 && brightness <= MAXIMUM_BRIGHTNESS : "brightness out of range";
    
    raster[yPosition][xPosition] = brightness;
  }

  /**
   * Renders the created raster. Every place in the array will be filled with the brightness
   * returned by the traced ray.
   * 
   * @param rayTracer
   *          is the raytracer object for the scene.
   * @return the filled raster.
   */
  @Override
  public Raster render(final Raytracer rayTracer) {

    assert rayTracer != null : "Reference can't be null!";

    assert xResolution >= 1 : "at least one coloumn";
    assert yResolution >= 1 : "at least one line";

    // Trace rays through viewport pixels from top to bottom, each row left
    // to right
    Stream
        .iterate(0, naturalNumber -> naturalNumber + 1)
        .limit(yResolution)
        .forEach(
            xValue -> {
              Stream
                  .iterate(0, naturalNumber -> naturalNumber + 1)
                  .limit(xResolution)
                  .forEach(
                      yValue -> raster[yValue][xValue] = (int) (MAXIMUM_BRIGHTNESS * rayTracer
                          .tracePrimary((2.0 / xResolution) * xValue + (2.0 / xResolution)
                              * HALF_PIXEL - 1, (2.0 / yResolution) * yValue + (2.0 / yResolution)
                              * HALF_PIXEL - 1)));
            });

    return this;
  }
}
