package edu.hm.schaffner.tobias.scene;

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
 * Supply a Camera and Viewport for the RayTracer.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public class Looker {

  /** Startpoint of the camera. */
  private final Point camera;

  /** Center of the Viewport. */
  private final Point viewPortMiddle;

  /** Width of the Viewport. */
  private final double viewPortWidth;

  /** Height of the Viewport. */

  private final double viewPortHeight;

  /** Vector between Camera and Center of the Viewport. */
  private final Vector viewVector;

  /** Vector in Y direction from the center of the Viewport. */
  private final Vector viewPortUp;

  /** Vector to the right side of the Viewport starts at the center. */
  private final Vector viewPortRight;

  /**
   * Constructor with value assignment, Computation of the vectors.
   * 
   * @param camera
   *          value for object variable
   * @param viewPortMiddle
   *          value for object variable
   * @param width
   *          value for object variable
   * @param height
   *          value for object variable
   * 
   */
  public Looker(final Point camera, final Point viewPortMiddle, final double width,
      final double height) {
    
    assert camera != null : "Reference can't be null!";
    assert viewPortMiddle != null : "Reference can't be null!";
    
    this.camera = camera;
    this.viewPortMiddle = viewPortMiddle;
    this.viewPortWidth = width;
    this.viewPortHeight = height;

    // The vector from Camera to the Center of the Viewport 
    viewVector = viewPortMiddle.minus(camera);
    // Computation of the vertical vector on viewVector and the Y-Axis
    viewPortRight = viewVector.cross(new Vector(0, 1, 0)).normalized().mult(viewPortWidth / 2);
    // Computation of the vertical vector on the Vector to the right and the viewVector
    viewPortUp = viewPortRight.cross(viewVector).normalized().mult(viewPortHeight / 2);
  }

  public Point getCamera() {
    return camera;
  }

  Point getViewPortMiddle() {
    return viewPortMiddle;
  }

  double getViewPortWidth() {
    return viewPortWidth;
  }

  double getViewPortHeight() {
    return viewPortHeight;
  }

  Vector getViewVector() {
    return viewVector;
  }

  Vector getViewPortRight() {
    return viewPortRight;
  }

  Vector getViewPortUp() {
    return viewPortUp;
  }

  /**
   * Computation of the ray through the middle of a Pixel.
   * 
   * @param xPosition
   *          height of the Pixel
   * @param yPosition
   *          vertical Position of the Pixel
   * @return the new computed ray.
   */
  public Ray getPrimaryRay(final double xPosition, final double yPosition) {
    // The Pixal Coordinates tell us how often we have to add the Vectors to the ViewPortMiddle
    final Point viewPortPoint = viewPortMiddle.add(viewPortUp.mult(yPosition)).add(
        viewPortRight.mult(xPosition));
    return new Ray(camera, viewPortPoint.minus(camera).normalized());
  }

  @Override
  public String toString() {
    return "Looker [camera=" + camera + ", viewPortMiddle=" + viewPortMiddle + ", viewPortWidth="
        + viewPortWidth + ", viewPortHeight=" + viewPortHeight + ", viewVector=" + viewVector
        + ", viewPortUp=" + viewPortUp + ", viewPortRight=" + viewPortRight + "]";
  }
}
