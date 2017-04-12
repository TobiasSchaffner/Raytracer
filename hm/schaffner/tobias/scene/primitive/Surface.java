package edu.hm.schaffner.tobias.scene.primitive;

import java.util.EnumMap;

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
 * Class Surface. Represents the surface of a Primitive, i.e. determines the look of the Object .
 * Characteristics are ratios of: ambient, diffuse, brightness, reflective and specular. These
 * Characteristics overlay default values. Each can only be changed once.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 * 
 * @see Referenzen
 */
public class Surface {

  /**
   * If the max value is set to a value higher then this constant we decrease it the value of that
   * constant to avoid overflow errors. 
   */
  private static final double MAX_REFLEXION_RATIO = 0.999;
  
  /** Map to save attributes of the surface. */
  private final EnumMap<Property, Double> surfacePropertie2value = new EnumMap<Property, Double>(
      Property.class);

  /**
   * Returns value of chosen Attribute.
   * 
   * @param key
   *          Kind of Attribute
   * @return value of the Attribute
   */
  public double get(Property key) {

    assert key != null : "Reference can't be null!";

    if (surfacePropertie2value.containsKey(key)) {
      return surfacePropertie2value.get(key);
    } else {
      return key.defaultValue;
    }
  }

  /**
   * Sets new value for a chosen Attribute.
   * 
   * @param key
   *          as "kind of Attribute
   * @param value
   *          new value for attribute
   */
  public void set(Property key, double value) {

    double keyValue = value;

    assert key != null : "Reference can't be null!";

    if (!surfacePropertie2value.containsKey(key) && value >= key.minimum && value <= key.maximum) {
      if (key == Property.ReflexionRatio && keyValue > MAX_REFLEXION_RATIO)
        keyValue = MAX_REFLEXION_RATIO;
      surfacePropertie2value.put(key, keyValue);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Enum for Attributnames.
   * 
   * @author Platypus
   *
   */
  public static enum Property {

    /** Ambient Ratio. */
    AmbientRatio(0.0, 1.0, 0.05),

    /** Diffuse Ratio. */
    DiffuseRatio(0.0, 1.0, 0.95),

    /** Reflexion Ratio. */
    ReflexionRatio(0.0, 1.0, 0.0),

    /** SpecularRatio. */
    SpecularRatio(0.0, 1.0, 0.0),

    /** SpecularExpo. */
    SpecularExponent(0.0, 1000.0, 30.0);

    /** The min Value. */
    private final double minimum;

    /** The max Value. */
    private final double maximum;

    /** The default Value. */
    private final double defaultValue;

    /**
     * The Constructor of the Enum.
     * 
     * @param minimum
     *          the minimum Value.
     * @param maximum
     *          the maximum Value.
     * @param defaultValue
     *          the default value.
     */
    Property(double minimum, double maximum, double defaultValue) {
      this.minimum = minimum;
      this.maximum = maximum;
      this.defaultValue = defaultValue;
    }
  }
}