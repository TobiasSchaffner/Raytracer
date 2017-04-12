package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;

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
 * Armbient calculates the ambient brightness of the intersected Object.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see http://en.wikipedia.org/wiki/Shading#Ambient_lighting
 */
public class Ambient implements LightModel {

  @Override
  public double calculate(Scene scene, Optional<Intersection> intersection) {
    
    assert scene != null : "Reference can't be null!";
    
    if (intersection.isPresent())
      return intersection.get().getIntersectedObject().getSurface().get(Property.AmbientRatio);
    else
      return 0.0;
  }
}
