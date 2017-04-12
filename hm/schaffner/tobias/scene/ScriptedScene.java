package edu.hm.schaffner.tobias.scene;

import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import edu.hm.schaffner.tobias.geometry.Point;
import edu.hm.schaffner.tobias.geometry.Vector;
import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.scene.primitive.Intersection;
import edu.hm.schaffner.tobias.scene.primitive.Primitive;
import edu.hm.schaffner.tobias.scene.primitive.Sphere;
import edu.hm.schaffner.tobias.scene.primitive.Plane;
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
 * Class Scripted Scene extends Scene. A new Scene. Awaits Vararg of Strings according to a specific
 * template, and generates appropriate objects(e.g. Looker,Light).
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public class ScriptedScene implements Scene {

  /** The length of a List with the complete looker information. */
  static final int LOOKER_ARGUMENT_SIZE = 9;

  /** The length of a List with the complete light information. */
  static final int LIGHT_ARGUMENT_SIZE = 4;

  /** The length of a List with the complete sphere information. */
  static final int SPHERE_ARGUMENT_SIZE = 5;

  /** The length of a List with the complete plane information . */
  static final int PLANE_ARGUMENT_SIZE = 7;

  /** The resulting looker for the scene. */
  private Looker looker;

  /** A counter for Looker to make shure theres only one present. */
  private int lookerCounter;

  /** The resulting light object for the scene. */
  private Optional<Point> light = Optional.empty();

  /** The List of primitives in the scene. */
  private final List<Primitive> primitives = new ArrayList<>();

  /**
   * The Constructor cleans up the Arguments filtering comment lines etc and calls the method for
   * extracting the scene elements.
   * 
   * @param input
   *          the input String Vararg
   */
  public ScriptedScene(String... input) {

    assert input != null : "Reference can't be null!";

    for (final String stringElement : input) {

      String cleanStringElement = stringElement;

      while (cleanStringElement.length() > 2 && cleanStringElement.charAt(0) == ' ') {
        cleanStringElement = cleanStringElement.substring(1, cleanStringElement.length());
      }

      if (cleanStringElement.isEmpty() || cleanStringElement.charAt(0) == '#') {
        continue;
      }

      final Iterator<String> iterator = new ScriptedScene.Cursor(cleanStringElement);
      final Iterator<String> agrumentSizeCounter = new ScriptedScene.Cursor(cleanStringElement);
      int size = 0;
      while (agrumentSizeCounter.hasNext()) {
        size++;
        agrumentSizeCounter.next();
      }
      extractElement(size, iterator);
    }
    // no or more than one LookerObject
    if (lookerCounter != 1) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Searches for the Element and calls the right Extract-Method.
   * 
   * @param size
   *          the size of the String
   * @param iterator
   *          the Iterator
   */
  private void extractElement(int size, Iterator<String> iterator) {

    assert iterator != null : "Reference can't be null!";

    final String firstArgument = iterator.next();
    if ("looker".equals(firstArgument)) {
      lookerCounter++;
      extractLooker(size, iterator);
    } else if ("sphere".equals(firstArgument)) {
      extractSphere(size, iterator);
    } else if ("light".equals(firstArgument)) {
      extractLight(size, iterator);
    } else if ("plane".equals(firstArgument)) {
      extractPlane(size, iterator);
    } else if ("ambient".equals(firstArgument)) {
      primitives.get(primitives.size() - 1).getSurface()
          .set(Property.AmbientRatio, Double.parseDouble(iterator.next()));
    } else if ("diffuse".equals(firstArgument)) {
      primitives.get(primitives.size() - 1).getSurface()
          .set(Property.DiffuseRatio, Double.parseDouble(iterator.next()));
    } else if ("reflexion".equals(firstArgument)) {
      primitives.get(primitives.size() - 1).getSurface()
          .set(Property.ReflexionRatio, Double.parseDouble(iterator.next()));
    } else if ("specular".equals(firstArgument)) {
      primitives.get(primitives.size() - 1).getSurface()
          .set(Property.SpecularRatio, Double.parseDouble(iterator.next()));
      primitives.get(primitives.size() - 1).getSurface()
          .set(Property.SpecularExponent, Double.parseDouble(iterator.next()));
    } else {
      throw new IllegalArgumentException();
    }

  }

  /**
   * Checks if the length is valid, parses the elements and creates a looker.
   * 
   * @param size
   *          of the handed string chain
   * @param iterator
   *          Iterator for the String
   */
  private void extractLooker(int size, Iterator<String> iterator) {

    assert iterator != null : "Referenz can not be null!";

    if (size == LOOKER_ARGUMENT_SIZE) {
      try {
        final Point camera = new Point(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        final Point viewPortCenter = new Point(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        this.looker = new Looker(camera, viewPortCenter, parse(iterator.next()),
            parse(iterator.next()));
      } catch (NumberFormatException exception) {
        throw new IllegalArgumentException();
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if the length is valid, parses the elements and creates a sphere.
   * 
   * @param size
   *          of the handed string chain
   * @param iterator
   *          Iterator for the String
   */
  private void extractSphere(int size, Iterator<String> iterator) {
    if (size == SPHERE_ARGUMENT_SIZE) {
      try {
        final Point sphereMidPoint = new Point(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        final Sphere sphere = new Sphere(sphereMidPoint, parse(iterator.next()));
        this.primitives.add(sphere);
      } catch (NumberFormatException exception) {
        throw new IllegalArgumentException();
      }

    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if the length is valid, parses the elements and creates a light.
   * 
   * @param size
   *          of the String chain
   * @param iterator
   *          Iterator for the String
   */
  private void extractLight(int size, Iterator<String> iterator) {

    assert iterator != null : "Referenz can not be null!";

    if (size == LIGHT_ARGUMENT_SIZE) {
      try {
        final Point lightPoint = new Point(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        this.light = Optional.of(lightPoint);
      } catch (NumberFormatException exception) {
        throw new IllegalArgumentException();
      }

    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if the length is valid, parses the elements and creates a plane.
   * 
   * @param size
   *          of the String chain
   * @param iterator
   *          Iterator for the String
   */
  private void extractPlane(int size, Iterator<String> iterator) {

    assert iterator != null : "Referenz must not be null!";

    if (size == PLANE_ARGUMENT_SIZE) {
      try {
        final Point planePoint = new Point(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        final Vector planeVector = new Vector(parse(iterator.next()), parse(iterator.next()),
            parse(iterator.next()));
        final Plane plane = new Plane(planePoint, planeVector);
        this.primitives.add(plane);
      } catch (NumberFormatException exception) {
        throw new IllegalArgumentException();
      }

    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Method to read a double out of a given String.
   * 
   * @param string
   *          source of the double
   * @return string as double
   * @throws NumberFormatException
   *           if parse fails
   */
  private double parse(String string) throws NumberFormatException {
    return Double.parseDouble(string);
  }

  /**
   * Searches for the nearest out of a list of intersections for a ray. If the location of the
   * intersection is equal to the point of the ray the intersection will be ignored. (These
   * intersections are evolved by mistake because of rounding errors)
   * 
   * @param ray
   *          the ray to get the nearest intersection for.
   */
  @Override
  public Optional<Intersection> findIntersection(Ray ray) {

    assert ray != null : "Reference must not be null!";

    final List<Intersection> intersectionList = new ArrayList<>();
    for (final Primitive primitive : primitives) {
      intersectionList.addAll(primitive.intersections(ray));
    }
    if (intersectionList.isEmpty()) {
      return Optional.empty();
    }

    Optional<Intersection> nearestIntersection = Optional.empty();

    // Intersections with a location equals the Point of the ray will be ignored
    for (final Intersection intersect : intersectionList) {
      if ((!nearestIntersection.isPresent() || nearestIntersection.get().getDistance() >= intersect
          .getDistance()) && !intersect.getLocation().equals(ray.getPoint()))
        nearestIntersection = Optional.of(intersect);
    }
    return nearestIntersection;
  }

  @Override
  public Optional<Primitive> getPrimitive() {
    return Optional.empty();
  }

  @Override
  public Optional<Point> getLight() {
    return light;
  }

  @Override
  public Looker getLooker() {
    return looker;
  }

  /**
   * A Iterator for Strings.
   */
  private static class Cursor implements Iterator<String> {

    /** Iterator position. */
    private int position;

    /** StringList for this Iterator. */
    private final List<String> element;

    /**
     * A Cursor for the String Iterator.
     * 
     * @param stringElement
     *          the element to be changed
     */
    public Cursor(String stringElement) {

      assert stringElement != null : "Referenz shouldn't be null!";

      final String tmp = stringElement.replace("[", "").replace("]", "").replace("<", "")
          .replace(">", "").replaceAll("\\s{2,}", " ");

      element = new ArrayList<>();
      int substringStart = 0;

      for (int stringPositon = 0; stringPositon < tmp.length(); stringPositon++) {
        if (!Character.isWhitespace(tmp.charAt(stringPositon)) && stringPositon == tmp.length() - 1) {
          element.add(tmp.substring(substringStart, stringPositon + 1));
        }

        if (Character.isWhitespace(tmp.charAt(stringPositon))) {
          element.add(tmp.substring(substringStart, stringPositon));
          substringStart = stringPositon + 1;
        }
      }
    }

    /**
     * checks if a next element exists.
     * 
     * @return true if the next element exists
     */
    public boolean hasNext() {
      return position < element.size();
    }

    /**
     * returnes the next element.
     * 
     * @return next element out of the list.
     */
    public String next() {
      if (position == element.size()) {
        throw new NoSuchElementException();
      }
      return element.get(position++);

    }
  }
}
