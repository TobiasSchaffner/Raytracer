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
 * A Vector is a geometric object that has a magnitude and direction. It can be added to other
 * vectors or multiplied by numbers.
 * 
 * A Vector is a geometric Object and inherits GeoData.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Vector_space http://en.wikipedia.org/wiki/Euclidean_vector
 */
public class Vector extends GeoData {

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
  public Vector(final double x, final double y, final double z) {
    super(x, y, z);
  }

  /**
   * Computation of the vector - length with good my good ol friend Pythagoras.
   *
   * @return a double of the length of the vector
   * @see http://en.wikipedia.org/wiki/Euclidean_vector
   */
  public double length() {
    return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ()
        * this.getZ());
  }

  /**
   * Computation of the normalization.
   *
   * @return the normalized vector
   * @see http://en.wikipedia.org/wiki/Unit_vector
   */
  public Vector normalized() {
    final double length = this.length();
    return new Vector(this.getX() / length, this.getY() / length, this.getZ() / length);
  }

  /**
   * Computation of the crossProdukt.
   * 
   * @param other
   *          Vector to compute
   * @return the crossProductVector
   */
  public Vector cross(final Vector other) {

    assert other != null : "Reference can't be null!";

    return new Vector(this.getY() * other.getZ() - this.getZ() * other.getY(), this.getZ()
        * other.getX() - this.getX() * other.getZ(), this.getX() * other.getY() - this.getY()
        * other.getX());
  }

  /**
   * Multiplication with a Scalar.
   * 
   * @param factor
   *          the Scalar
   * @return shifted vector
   */
  public Vector mult(final double factor) {
    return new Vector(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
  }

  @Override
  public String toString() {
    return String.format("[%f, %f, %f]", this.getX(), this.getY(), this.getZ());
  }
}
