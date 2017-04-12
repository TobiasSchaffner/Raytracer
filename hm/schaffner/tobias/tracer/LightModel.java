package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;
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
 * The interface for the lightning components defines the only method calculate, which returns the
 * brightness for an intersection in a scene.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
interface LightModel {

  /** Calculation of the brightness for the specific Class representing a Lightmodell. 
   *
   * @param scene the scene to get the light etc.
   * @param intersection the intersection to calculate the brightness for.
   * @return a double of the brightness between 0 (minimum) and 1 (maximum).
   **/
  double calculate(Scene scene, Optional<Intersection> intersection);
}
