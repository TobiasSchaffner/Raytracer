package edu.hm.schaffner.tobias.scene.primitive;

import edu.hm.schaffner.tobias.geometry.Point;
import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.geometry.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * Class Sphere. It's a SceneObject(implements Primitive). A sphere is a perfectly round geometric
 * object in 3D space.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Sphere
 * @see http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
 */
public class Sphere implements Primitive {

  /** Center of the Sphere. */
  private final Point midPoint;

  /** radius of the Sphere. */
  private final double radius;

  /** Squared radius of sphere to only compute it one time. */
  private final double squaredRadius;

  /**
   * Surface of the Sphere.
   */
  private final Surface surface;

  /**
   * standard Constructor and computation of squared radius.
   * 
   * @param center
   *          of Sphere
   * @param radius
   *          of Sphere
   **/
  public Sphere(final Point center, final double radius) {

    assert center != null : "Reference can't be null!";

    if (radius == 0)
      throw new NullPointerException();
    if (radius < 0)
      throw new IllegalArgumentException();

    surface = new Surface();
    this.midPoint = center;
    this.radius = radius;
    this.squaredRadius = radius * radius;
  }

  public Point getCenter() {
    return midPoint;
  }

  public double getRadius() {
    return radius;
  }

  public Surface getSurface() {
    return surface;
  }

  /**
   * returns the normalVector of the sphere. In this case the vector that defines plane.
   * 
   * @param intersection
   *          The intersectionPoint.
   * @return Vector the normalVector
   */
  public Vector getNormal(final Point intersection) {

    assert intersection != null : "Reference can't be null!";

    return intersection.minus(this.getCenter()).normalized();
  }

  /**
   * Nearest intersection of ray (point and vector) with the sphere.
   * 
   * @param ray
   *          The ray to check for hits.
   * @return A List with fist Value -1 if there is no intersection. Distances of intersections with
   *         ray otherwise.
   * @see http ://www.siggraph.org/education/materials/HyperGraph/raytrace/rtinter1 .htm
   * @see http://en.wikipedia.org/wiki/Ray-sphere_intersection
   */

  public List<Intersection> intersections(final Ray ray) {

    assert ray != null : "Reference can not be null!";

    final List<Intersection> result = new ArrayList<>();

    // Vektor from point of Ray(camera) to the center of the sphere
    final Vector rayCenterVector = this.getCenter().minus(ray.getPoint());

    // Computation of the factor to get to the nearest point to center
    final double factor = rayCenterVector.dot(ray.getVector());

    // multiply the rayfactor with the factor and add it to the Raypoint
    final Point shortestDistance = ray.getPoint().add(ray.getVector().mult(factor));

    // With the nearest point we can now compute the vector from spherecenter to the nearest point
    final Vector lotVector = shortestDistance.minus(this.getCenter());

    // This will give us the length
    final double lotLength = Math.sqrt(lotVector.dot(lotVector));

    // If the lot is longer then the radius we miss the sphere
    if (lotLength > this.getRadius()) {
      return result;
    }

    // If its shorter, we have two intersections
    if (lotLength < this.getRadius()) {

      // pythagoras helps to get the intersections
      final double solve = Math.sqrt(squaredRadius - (lotLength * lotLength));

      final Point intersectionPointA = shortestDistance.add(ray.getVector().mult(-1 * solve));
      final Point intersectionPointB = shortestDistance.add(ray.getVector().mult(solve));

      if (factor - solve > 0) {
        final Intersection intersectionA = new Intersection(Optional.of(intersectionPointA),
            Optional.of(this), factor - solve, true);
        result.add(intersectionA);
      }
      if (factor + solve > 0) {
        final Intersection intersectionB = new Intersection(Optional.of(intersectionPointB),
            Optional.of(this), factor + solve, false);
        result.add(intersectionB);
      }
      return result;
    }

    // touching rays can be ignored if more performance is needed.
    final Intersection intersection = new Intersection(Optional.of(shortestDistance),
        Optional.of(this), factor, true);
    result.add(intersection);
    return result;
  }

  @Override
  public String toString() {
    return "Sphere:\ncenter=" + midPoint + "\nradius=" + radius + "\nsquaredRadius="
        + squaredRadius;
  }

}