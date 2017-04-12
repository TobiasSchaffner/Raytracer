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
 * Half of a line proceeding from an initial point. Consist of a Point and a Vector.
 * 
 * It's a ray in a 3D space.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Line_(geometry)#Ray
 */
public class Ray {

  /** The weight of a ray. */
  public static final double MAX_RAY_WEIGHT = 1;
  
  /** The needed Point to define a Ray. */
  private final Point point;
  /** The needed Vector to define a Ray. */
  private final Vector vector;

  /**
   * A value weight as indicator if this ray should still be traced by the Raytracer. If this value
   * is below the threshold, this ray will be ignored.
   */
  private double weight;


  /**
   * Constructor with vorbidden 0-Vector.
   * 
   * @param point
   *          is the footpoint of the ray
   * @param vector
   *          needed for the ray
   **/
  public Ray(final Point point, final Vector vector) {

    assert point != null : "Reference can't be null!";
    assert vector != null : "Reference can't be null!";

    if (vector.length() == 0)
      throw new IllegalArgumentException();

    this.point = point;
    this.vector = vector.normalized();
  }

  /**
   * Constructor for Ray considering the weight.
   * 
   * @param point
   *          is the footpoint of the ray
   * @param vector
   *          needed direction for the ray
   * @param weight
   *          the needed weigth
   */
  public Ray(final Point point, final Vector vector, final double weight) {

    assert point != null : "Referenz can not be null!";
    assert vector != null : "Referenz can not be null!";

    if (weight < 0 || weight > 1 || vector.length() == 0)
      throw new IllegalArgumentException();

    this.point = point;
    this.vector = vector.normalized();
    this.weight = weight;
  }

  public Point getPoint() {
    return point;
  }

  public Vector getVector() {
    return vector;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object object) {

    assert object != null : "Reference must not be null!";

    if (this == object)
      return true;
    if (this.getClass() != object.getClass())
      return false;
    final Ray other = (Ray) object;
    return point.equals(other.point) && vector.equals(other.vector);
  }

  @Override
  public String toString() {
    return "Ray [point=" + point + ", vector=" + vector + "]";
  }

}
