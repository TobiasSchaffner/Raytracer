package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;

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
 * The Diffuse class calculates the diffuse brightness with the Lambert cosine law. The law says
 * that the brightness of the surface is directly proportional to the cosine of the angle between
 * the direction of the incident light and the surface normal.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Lambert%27s_cosine_law
 */
public class Diffuse implements LightModel {

  /** The boolean telling us weather the intersection lies in the shadow of not. */
  private final BooleanPromise isShadowed;

  /**
   * We need the shadow value to calculate the brightness.
   * 
   * @param isShadowed
   *          tells us if we have to calculate it at all.
   */
  Diffuse(BooleanPromise isShadowed) {

    assert isShadowed != null : "Reference can not be null!";

    this.isShadowed = isShadowed;
  }

  /**
   * The calculate method returns the diffuse brightness for the intersection. It is directly
   * proportional to the cos and will be calculated by the angle.
   * 
   * @param scene
   *          the scene to get the light from.
   * @param intersection
   *          the intersection to calculate the reflexion brightness for.
   * @return the double of brightness between 0 and 1.
   */
  @Override
  public double calculate(Scene scene, Optional<Intersection> intersection) {

    assert scene != null : "Reference can't be null!";

    if (scene.getLight().isPresent() && intersection.isPresent()) {
      // definiton of the normal Vector
      final Vector normalVector = intersection.get().getIntersectedObject()
          .getNormal(intersection.get().getLocation());

      // definition of the lightVector
      final Vector lightVector = scene.getLight().get().minus(intersection.get().getLocation())
          .normalized();

      // diffuse brightness
      double diffuse = intersection.get().getIntersectedObject().getSurface()
          .get(Property.DiffuseRatio)
          * normalVector.dot(lightVector);

      if (diffuse < 0 || !isShadowed.get())
        diffuse = 0.0;
      return diffuse;
    } else
      return 0.0;
  }

}
