package edu.hm.schaffner.tobias.scene.primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.hm.schaffner.tobias.geometry.Point;
import edu.hm.schaffner.tobias.geometry.Vector;
import edu.hm.schaffner.tobias.geometry.Ray;

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
 * Class Plane. It's a SceneObject(implements Primitive). A plane is a two-dimensional surface.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Plane_(geometry)
 */
public class Plane implements Primitive {

  /** The Point in the Plane. */
  private final Point point;

  /** The normal Vector vertical to the plane. */
  private final Vector vector;

  /** surface of the Plane. */
  private final Surface surface;

  /**
   * Constructor of the plane.
   * 
   * @param point
   *          The Point in the Plane
   * @param normal
   *          gets normalized for easier further computation.
   */
  public Plane(final Point point, final Vector normal) {

    assert point != null : "Reference can't be null!";
    assert normal != null : "Reference can't be null!";

    if (normal.length() == 0)
      throw new IllegalArgumentException();

    this.point = point;
    this.vector = normal.normalized();
    surface = new Surface();
  }

  public Point getPoint() {
    return point;
  }

  public Vector getVector() {
    return vector;
  }

  public Surface getSurface() {
    return surface;
  }

  /**
   * returns the normalVector of the plane. In this case the vector that defines plane.
   * 
   * @param intersection
   *          The intersectionPoint.
   * @return Vector the normalVector
   */
  public Vector getNormal(Point intersection) {
    assert intersection != null : "Reference can not be null!";
    return this.getVector();
  }

  /**
   * Intersection Method for the Plane.
   * 
   * @param ray
   *          A Ray that eventualy hits the Plane.
   * @return intersections A List with the Intersection Element in it if the ray hits the plane.
   * @see http://www.uninformativ.de/bin/RaytracingSchnitttests-76a577a-CC-BY.pdf
   */
  public List<Intersection> intersections(final Ray ray) {

    assert ray != null : "Reference can not be null!";

    // The List for the intersections.
    final List<Intersection> result = new ArrayList<>();
    // double for the Angle between Plane and Ray Vector
    final double dotAngle = vector.dot(ray.getVector());

    // if the angle is bigger the planes Plane is behind the camera
    if (dotAngle >= 0) {
      return result;
    }

    final double parameterD = vector.dot(point);
    final double alpha = (parameterD - ray.getPoint().dot(vector)) / (ray.getVector().dot(vector));
    final Point intersectionPoint = ray.getPoint().add(ray.getVector().mult(alpha));

    // finally create the intersection Object.
    if (alpha > 0) {
      final Intersection intersection = new Intersection(Optional.of(intersectionPoint),
          Optional.of(this), alpha, true);
      result.add(intersection);
    }
    return result;
  }

  @Override
  public String toString() {
    return "Plane:\npoint=" + point + "\nvector=" + vector;
  }

}
