package edu.hm.schaffner.tobias.raster;

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
 * A class overwriting the setPixel Method to make the threads visible. Every Thread will have a
 * single brightness. The resulting picture will show the lines in the brigthnesses of the different
 * threads.
 * 
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-15
 */
public class ThreadIdRaster extends ParallelRaster {

  /** The maximal brightness for .png Files. */
  private static final int MAXIMUM_BRIGHTNESS = 255;

  /**
   * The Construct calls the super class, handing the same parameters.(Width, Height and the number
   * of threads to call).
   * 
   * @param xResolution
   *          is the width of the array.
   * @param yResolution
   *          is the height of the array (and the number of lines).
   * @param threadCount
   *          the number of threads to call.
   */
  public ThreadIdRaster(final int xResolution, final int yResolution, final int threadCount) {
    super(xResolution, yResolution, threadCount);
  }

  /**
   * The overwritten setPixel Method. The resulting brightness of each Pixel will be the ID of the
   * Thread.
   * 
   * @param yPosition
   *          is the offset on the y-axis
   * @param xPosition
   *          is the offset on the x-axis
   * @param brightness
   *          is handed by the thread but never used as we visualize the ID of the thread.
   */
  @Override
  public void setPixel(int yPosition, int xPosition, int brightness) {

    if (this.getThreadCount() == 1)
      super.setPixel(yPosition, xPosition, 0);
    else
      super.setPixel(yPosition, xPosition, (int) ((double) MAXIMUM_BRIGHTNESS
          * (int) Thread.currentThread().getId() / (this.getThreadCount() - 1)));
  }
}
