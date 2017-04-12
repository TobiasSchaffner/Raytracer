package edu.hm.schaffner.tobias.scene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
 * Loaded scene can read the scripted scene out of a .txt file and hand it to scripted scene.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */

public class LoadedScene extends ScriptedScene {
  /**
   * Generates an Array out of the lines of a File and hands it to the superclass ScriptedScene.
   * 
   * @param fileName
   *          provided Filename to read the lines out.
   * @throws IOException
   *           InputOutputError if the file is not found/readable etc.
   */
  public LoadedScene(final String fileName) throws IOException {
    super(Files.lines(Paths.get(fileName)).toArray(String[]::new));
  }
}
