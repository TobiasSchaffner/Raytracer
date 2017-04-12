package edu.hm.schaffner.tobias.scene.primitive;

import java.util.Optional;
import edu.hm.schaffner.tobias.Util;
import edu.hm.schaffner.tobias.geometry.Point;

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
 * Class Intersection. Represents an intersection between a ray and a Object of the
 * scene(Primitive). Characteristics: Intersectionlocation, Intersected Object, Distance to camera,
 * Direction.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Intersection
 */
public final class Intersection {

  /** Point of Intersection. */
  private final Point location;

  /** Kind of the intersected Object. */
  private final Primitive intersectedObject;

  /** describes the distance to camera. */
  private final double distance;

  /** describes the kind of intersection. true is ingoing. false means outgoing. */
  private final boolean intersects;

  /**
   * An Intersection is defined by Location, Kind of Intersected Object, Distance and Kind of the
   * Intersection.
   * 
   * @param location
   *          the Point of intersection
   * @param intersectedObject
   *          the Object thats intersected by the ray
   * @param distance
   *          the distance - camera to intersection
   * @param intersects
   *          true if in - false if outgoing
   */
  public Intersection(final Optional<Point> location, final Optional<Primitive> intersectedObject,
      final double distance, final boolean intersects) {
    assert location != null : "Reference can't be null!";
    assert intersectedObject != null : "Reference can't be null!";

    assert distance > 0 : "No negative intersections possible!";

    if (!location.isPresent() || !intersectedObject.isPresent()) {
      throw new IllegalArgumentException();
    }
    this.location = location.get();
    this.intersectedObject = intersectedObject.get();
    this.distance = distance;
    this.intersects = intersects;
  }

  public Point getLocation() {
    return location;
  }

  public Primitive getIntersectedObject() {
    return intersectedObject;
  }

  public double getDistance() {
    return distance;
  }

  public boolean isIntersects() {
    return intersects;
  }

  /**
   * Hashcode of Intersection.
   *
   * @return it will alway throw an exception while equals will not work as expected
   */
  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  /**
   * Equals Method. New defined with near method for double equalitycheck.
   *
   * @return True if equals - false if not.
   * @param object
   *          the Object that shall be checked for equality
   */
  @Override
  public boolean equals(final Object object) {

    assert object != null : "Reference can not be null!";

    if (this == object)
      return true;

    if (this.getClass() != object.getClass())
      return false;

    final Intersection other = (Intersection) object;
    if (!Util.nearly(distance, other.distance))
      return false;
    if (!intersectedObject.equals(other.intersectedObject))
      return false;
    if (intersects != other.intersects)
      return false;
    return location.equals(other.location);
  }

  /**
   * toString method.
   *
   * @return a fine formated String for intersections
   */
  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    final String space = " ";
    result.append('[').append(location.toString()).append(space)
        .append(intersectedObject.toString()).append(space)
        .append(String.format("%.2f ", distance)).append(space).append(intersects).append(']');

    return result.toString();
  }

}
