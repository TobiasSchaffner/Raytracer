package edu.hm.schaffner.tobias;

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
 * Utility Class for help Methods that are used very often.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public class Util {

  /**
   * Helpmethod for equals to check two doubles.
   *
   * @return True if equal enough.
   * @param have
   *          the first double
   * @param want
   *          the second double
   */
  public static boolean nearly(final double have, final double want) {
    final double gap = 2.41e-10;
    return Math.abs(have - want) < gap;
  }
  
  
}
