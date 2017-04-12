package edu.hm.schaffner.tobias.geometry;

import edu.hm.schaffner.tobias.Util;

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
 * Class GeoData as the ABC for geometric Objects.
 * Every extending Class inherits its Position in the 3D coordinate system. Furthermore they inherit
 * standard calculations: minus, dotproduct.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
abstract class GeoData {

  /** X Coordinate of the point or vector. */
  private final double xCoordinate;
  /** Y Coordinate of the point or vector. */
  private final double yCoordinate;
  /** Z Coordinate of the point or vector. */
  private final double zCoordinate;

  /**
   * Constructor. Just written down to have a better overview.
   * 
   * @param x
   *          Coordinate
   * @param y
   *          Coordinate
   * @param z
   *          Coordinate
   **/
  GeoData(final double x, final double y, final double z) {
    this.xCoordinate = x;
    this.yCoordinate = y;
    this.zCoordinate = z;
  }

  protected double getX() {
    return xCoordinate;
  }

  protected double getY() {
    return yCoordinate;
  }

  protected double getZ() {
    return zCoordinate;
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final boolean equals(final Object object) {

    assert object != null : "Reference can't be null!";
    
    if (this == object)
      return true;

    if (this.getClass() != object.getClass())
      return false;

    final GeoData other = (GeoData) object;

    return Util.nearly(xCoordinate, other.xCoordinate) && Util.nearly(yCoordinate, other.yCoordinate)
        && Util.nearly(zCoordinate, other.zCoordinate);
  }

  

  /**
   * Computation of the dotProdukt.
   *
   * @param other
   *          Vector to compute
   * @return a double of the dotProduct
   * @see http://en.wikipedia.org/wiki/Dot_product
   */
  public double dot(final GeoData other) {
    return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
  }

  /**
   * Computation of a Vector out of two Points by substracting them. 
   *
   * @param other
   *          Point to subtract
   * @return a Vector from one Point to the other
   * @see http://en.wikipedia.org/wiki/Euclidean_vector
   */

  public Vector minus(final GeoData other) {
    return new Vector(this.getX() - other.getX(), this.getY() - other.getY(), this.getZ()
        - other.getZ());
  }

}