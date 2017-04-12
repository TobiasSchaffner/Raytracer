package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;

import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.geometry.Vector;
import edu.hm.schaffner.tobias.scene.Scene;
import edu.hm.schaffner.tobias.scene.primitive.Intersection;
import edu.hm.schaffner.tobias.scene.primitive.Surface.Property;

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
 * The Reflexion class is based on the Lambertian reflectance and calculates a new Ray based on the
 * given ray. The new Ray leaves the Object with the same angle as the incomming ray then the next
 * intersection is calculated if its present. To avoid infinite loops a decreasing weight is
 * calculated stopping the recursion after a certain times.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Lambertian_reflectance
 */
public class Reflexion implements LightModel {

  /**
   * The minimum weight of a ray to get calculated. 256 because of the sensitivity of our .png
   * pixels.
   */
  private static final double MINWEIGHT = 256;

  /** The ray to calculate the reflexion for. */
  private final Ray ray;

  /** The raytracer object to calculate the next rays intersection with. */
  private final Raytracer raytracer;

  /**
   * Ray and the raytracer to calculate out new rays.
   * 
   * @param ray
   *          the ingoing angle is needed to get the outgoing one.
   * @param raytracer
   *          to calculate new rays.
   */
  Reflexion(Ray ray, Raytracer raytracer) {

    assert ray != null : "Reference can not be null!";
    assert raytracer != null : "Reference can not be null!";

    this.ray = new Ray(ray.getPoint(), ray.getVector(), 1);
    this.raytracer = raytracer;
  }

  /**
   * The calculate method returning the reflection brightness for the intersection. Therefor
   * calculate will be recursive called to get the reflected rays.
   * 
   * @param scene
   *          the scene to get the light from.
   * @param intersection
   *          the intersection to calculate the reflexio brightness for.
   * @return the double of brightness between 0 and 1.
   */
  @Override
  public double calculate(Scene scene, Optional<Intersection> intersection) {

    assert scene != null : "Reference can't be null!";

    if (!intersection.isPresent()
        || intersection.get().getIntersectedObject().getSurface().get(Property.ReflexionRatio) == 0) {
      return 0.0;
    }
    double brightness = 0.0;

    if (ray.getWeight() > 1 / MINWEIGHT) {
      final Ray secondaryRay = getNextRay(intersection, ray.getWeight());
      brightness += raytracer.trace(secondaryRay);
      if (brightness >= 1.0) {
        brightness = 1.0;
      }
    }
    return brightness;
  }

  /**
   * getNextRay gets the intersection and a weight of the current ray. if the current ray is abouve
   * a fixed level this method will be called and a mirrored ray will be calculated.
   * 
   * @param intersection
   *          the intersection to calculate the ray for.
   * @param weight
   *          the weight of the current ray.
   * @return a new ray to calculate the brightness for.
   */
  private Ray getNextRay(Optional<Intersection> intersection, double weight) {

    final double reflectionRatio = intersection.get().getIntersectedObject().getSurface()
        .get(Property.ReflexionRatio);

    // calculation of the m-vector like in diffuse
    final Vector normalVector = intersection.get().getIntersectedObject()
        .getNormal(intersection.get().getLocation());

    // vector from intersection to looker normalised
    final Vector negativeView = ray.getVector().mult(-1);

    // the nearest point over the factor to m
    final double normalFactor = negativeView.dot(normalVector);
    final Vector negativeViewToNormal = negativeView.minus(normalVector.mult(normalFactor));
    final Vector mirroredVector = negativeView.minus(negativeViewToNormal).minus(
        negativeViewToNormal);

    // creating the result ray
    return new Ray(intersection.get().getLocation(), mirroredVector, weight * reflectionRatio);
  }
}
