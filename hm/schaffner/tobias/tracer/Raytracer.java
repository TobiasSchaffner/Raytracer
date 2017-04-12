package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;
import java.util.stream.Stream;

import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.scene.primitive.Intersection;
import edu.hm.schaffner.tobias.scene.Scene;

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
 * The Raytracer class gets a scene and calls the lightning Models for a specific Ray or a Point on
 * the viewport.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Ray_tracing_%28graphics%29
 */
public class Raytracer {

  /** the Scene we want to display. */
  private final Scene scene;

  /**
   * Constructor of Raytracer.
   * 
   * @param scene
   *          as a private object variable.
   */
  public Raytracer(final Scene scene) {

    assert scene != null : "Referenz can't be null!";

    this.scene = scene;
  }

  /**
   * Gets the nearest Intersection and calculates the brightness for a ray through a provided
   * position on the viewport.
   * 
   * @param viewPortX
   *          The ViewPortPosition X Value
   * @param viewPortY
   *          The ViewPortPosition Y Value
   * @return double of the brightness.
   */
  public double tracePrimary(double viewPortX, double viewPortY) {
    // Get the primary ray for the current coordinates and the nearest Intersection
    final Ray ray = scene.getLooker().getPrimaryRay(viewPortX, viewPortY);
    return trace(ray);
  }

  /**
   * Traces a given ray and collects the brightness values of the LightModel Classes for the hit
   * intersections.
   * 
   * @param ray
   *          to be traced.
   * @return a double of the brightness between 0 (the minimum) and 1 (the maximum value).
   */
  public double trace(Ray ray) {

    assert ray != null : "Referenz can't be null!";

    final Optional<Intersection> nearestIntersection = scene.findIntersection(ray);
    double brightness;

    // calculating the shadow
    final BooleanPromise isShadowed = new BooleanPromise(() -> new Shadowed().calculate(scene,
        nearestIntersection) > 0);

    brightness = Stream
        .of(new Ambient(), new Diffuse(isShadowed), new SpecularHighlight(isShadowed, ray),
            new Reflexion(ray, this))
        .map(lightModel -> lightModel.calculate(scene, nearestIntersection))
        .mapToDouble(Double::doubleValue).sum();

    if (brightness > 1)
      brightness = 1;

    return brightness;
  }
}
