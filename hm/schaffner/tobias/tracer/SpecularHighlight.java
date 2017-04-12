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
 * The calculates the specular highlight on the Object. A specular highlight is the bright spot of
 * light that appears on shiny objects when illuminated.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Specular_highlight
 */
public class SpecularHighlight implements LightModel {

  /** Boolean that tells us if the intersection is shadowed. */
  private final BooleanPromise isShadowed;

  /** The intersecting ray to calculate the angle to the light. */
  private final Ray ray;

  /**
   * We need the shadow value and the ray to calculate the brightness.
   * 
   * @param isShadowed
   *          tells us if we have to calculate it at all.
   * @param ray
   *          the ray to calculate it for.
   */
  SpecularHighlight(BooleanPromise isShadowed, Ray ray) {

    assert isShadowed != null : "Reference can't be null!";
    assert ray != null : "Reference can't be null!";

    this.isShadowed = isShadowed;
    this.ray = ray;
  }

  /**
   * Calculate method calculates the mirrored vector and the angle of the mirrored Vector to the
   * light. That angle can be used to calculate to specular highlight function.
   * 
   * @return a double of the calculated brightness.
   * @param scene
   *          to get the light.
   * @param intersection
   *          to calculate the specular highlight for.
   */
  @Override
  public double calculate(Scene scene, Optional<Intersection> intersection) {

    assert scene != null : "Reference may not be null!";

    if (scene.getLight().isPresent() && intersection.isPresent() && isShadowed.get()) {
      // definiton of the normal Vector
      final Vector normalVector = intersection.get().getIntersectedObject()
          .getNormal(intersection.get().getLocation());

      // Vector light to nearest intersection
      final Vector lightVector = scene.getLight().get().minus(intersection.get().getLocation())
          .normalized();

      // the vector from intersection to looker normalised
      final Vector negativeView = ray.getVector().mult(-1);

      // the nearest point over the factor to m
      final double normalFactor = negativeView.dot(normalVector);
      final Vector negativeViewToNormal = negativeView.minus(normalVector.mult(normalFactor));
      final Vector mirroredVector = negativeView.minus(negativeViewToNormal).minus(
          negativeViewToNormal);

      // the cos of the angle between the mirrored and the lightvector.
      double specularAngle = mirroredVector.normalized().dot(lightVector);
      if (specularAngle < 0)
        specularAngle = 0;
      final double specular = intersection.get().getIntersectedObject().getSurface()
          .get(Property.SpecularRatio)
          * Math
              .pow(
                  specularAngle,
                  intersection.get().getIntersectedObject().getSurface()
                      .get(Property.SpecularExponent));

      return specular;
    } else
      return 0.0;
  }
}
