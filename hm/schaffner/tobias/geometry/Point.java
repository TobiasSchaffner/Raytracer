package edu.hm.schaffner.tobias.geometry;

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
 * Point is a geometric Object with Coordinates in the 3D space. Point inherits from GeoData. It's a
 * point in the 3D space. You may add a Vector.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Point_(geometry)
 */
public class Point extends GeoData {

  /**
   * Origin Constant.
   */
  public static final Point ORIGIN = new Point(0, 0, 0);

  /**
   * Constructor. We get everything we need from GeoData.
   * 
   * @param x
   *          Coordinate
   * @param y
   *          Coordinate
   * @param z
   *          Coordinate
   **/
  public Point(final double x, final double y, final double z) {
    super(x, y, z);
  }

  /**
   * Shifts this Point round the given Vector.
   * 
   * @param other
   *          vector to shift point
   * @return a new shifted Point
   */
  public Point add(final Vector other) {

    assert other != null : "Reference can't be null!";

    return new Point(this.getX() + other.getX(), this.getY() + other.getY(), this.getZ()
        + other.getZ());
  }

  @Override
  public String toString() {
    return String.format("[%f, %f, %f]", this.getX(), this.getY(), this.getZ());
  }
}