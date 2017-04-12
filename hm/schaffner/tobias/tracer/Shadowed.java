package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;

import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.geometry.Vector;
import edu.hm.schaffner.tobias.scene.Scene;
import edu.hm.schaffner.tobias.scene.primitive.Intersection;

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
 * Shadowed calculates if a second object is placed between the object itself and the light in the
 * scene. If so, the object is not enlighted directly.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public class Shadowed implements LightModel {

  /**
   * The calculate method for the shadow checks if theres an object placed between the intersection
   * and the light. If so the class returns 0. Else 1.
   * 
   * @param scene
   *          to get the light
   * @param intersection
   *          to calculate the shadowed value for.
   * @return a double between 1 and 0 working like a boolean
   */
  @Override
  public double calculate(Scene scene, Optional<Intersection> intersection) {

    assert scene != null : "Reference can't be null!";

    // No intersection at all?
    if (!intersection.isPresent() || !scene.getLight().isPresent())
      return 0.0;

    // Vector light to nearest intersection
    final Vector lightVector = scene.getLight().get().minus(intersection.get().getLocation())
        .normalized();

    final Vector rayVector = scene.getLooker().getCamera().minus(intersection.get().getLocation())
        .normalized();
    final double schiedermeierConstant = 10e-10;

    final Vector movingVector = rayVector.mult(schiedermeierConstant);

    final Ray secondaryRay = new Ray(intersection.get().getLocation().add(movingVector),
        lightVector);

    // possible interferingObject
    final Optional<Intersection> interferingObjectOption = scene.findIntersection(secondaryRay);

    // length of the not normalized lightvector, distance between intersection and light
    final double distanceToLight = scene.getLight().get().minus(intersection.get().getLocation())
        .length();

    // distance between intersection and the interfering object if it exists
    if (!interferingObjectOption.isPresent()
        || interferingObjectOption.get().getLocation().minus(intersection.get().getLocation())
            .length()
            - distanceToLight > 0)
      return 1.0;
    else
      return 0.0;
  }
}
