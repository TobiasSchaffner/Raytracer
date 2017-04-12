package edu.hm.schaffner.tobias.scene.primitive;

import java.util.List;
import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.geometry.Vector;
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
 * Interface Primitive.
 * Primitives are the Objects of our scene.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public interface Primitive {

  /**
   * Intersection Method that has to be implemented.
   * 
   * @param ray
   *          A Ray that eventualy hits the object.
   * @return intersections A List with the Intersection Element in it if the ray hits the object.
   */
  List<Intersection> intersections(final Ray ray);

  /**
   * returns the normalVector of the primitive. In this case the vector that defines plane.
   * 
   * @param intersection
   *          The intersectionPoint.
   * @return Vector the normalVector
   */
  Vector getNormal(Point intersection);

  /**
   * returns Surface of the Primitive.
   * 
   * @return the Surface
   */
  Surface getSurface();

}