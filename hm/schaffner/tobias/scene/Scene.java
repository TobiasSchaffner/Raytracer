package edu.hm.schaffner.tobias.scene;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import edu.hm.schaffner.tobias.geometry.Ray;
import edu.hm.schaffner.tobias.scene.primitive.Primitive;
import edu.hm.schaffner.tobias.scene.primitive.Intersection;
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
 * Interface Scene. Combines SceneObjects, Viewport and the Camera. Also this Scene is the Factory
 * for all kinds of SceneObjects.
 * 
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
@SuppressFBWarnings("UCF_USELESS_CONTROL_FLOW")
public interface Scene {

  /**
   * every scene has a looker, and this method returns this looker.
   * 
   * @return belonging looker of the scene.
   */
  Looker getLooker();

  /**
   * finds nearest intersection.
   * 
   * @param ray
   *          the ray which intersects
   * @return the nearest Intersection as Intersectionobject
   */
  Optional<Intersection> findIntersection(final Ray ray);

  /**
   * Returns the PrimitiveObject in this scene.
   * 
   * @return Primitive Object f.e. Sphere
   */
  Optional<Primitive> getPrimitive();

  /**
   * The lighting Point.
   * 
   * @return The defined Point Object
   */
  Optional<Point> getLight();

  /**
   * Generates new Scene.
   * 
   * @param args
   *          : 1.String as kind of Scene and then needed Parameters for the wanted Scene. If the
   *          String is empty this methods generates a default Scene. (A
   *          ScriptedScene("looker[0 0 5] [0 0 0] 2 2", "sphere [0 0 -5] 1", "ambient 1"))
   * @return New Scene
   * @throws ClassNotFoundException
   *           If the provided Class is not fount
   * @throws IOException
   *           input output Error
   */
  static Scene make(String... args) throws ClassNotFoundException, IOException {

    assert args != null : "Reference can not be null!";

    final String firstArgument = args[0];

    if ("ScriptedScene".equals(firstArgument) || firstArgument.isEmpty() && args.length == 1) {
      return makeScriptedScene(args);
    }

    if ("LoadedScene".equals(firstArgument)) {
      return makeLoadedScene(args);
    }

    else {
      throw new ClassNotFoundException();
    }
  }

  /**
   * Method to create ScriptedScenes.
   * 
   * @param args
   *          The arguments of the make Method
   * @return The Scene
   */
  static Scene makeScriptedScene(String... args) {

    assert args != null : "Referenz must not be null!";

    if (args[0].isEmpty() && args.length == 1)
      return new ScriptedScene("looker[0 0 5] [0 0 0] 2 2", "sphere [0 0 -5] 1", "ambient 1");
    else
      return new ScriptedScene(Arrays.copyOfRange(args, 1, args.length));
  }

  /**
   * Method to create Loaded Scenes.
   * 
   * @param args
   *          The arguments of the make Method
   * @return The Scene
   * @throws IOException
   *           Input Output Error
   */
  static Scene makeLoadedScene(String... args) throws IOException {

    assert args != null : "Referenz can not be null!";

    if (args.length == 2)
      return new LoadedScene(args[1]);
    else
      throw new IllegalArgumentException();
  }
}