package edu.hm.schaffner.tobias.raster;

import java.util.ArrayList;
import java.util.stream.Stream;

import edu.hm.schaffner.tobias.tracer.Raytracer;

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
 * The ParallelRaster Class defines an 2D Array of a given Size. Every Place in the Array will be
 * one Pixel on the ViewPort. The lines of the Array will be splitted to the Threads of class
 * Rasterthread.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-15
 */
public class ParallelRaster extends ArrayRaster {

  /** The maximal brightness for .png Files. */
  private static final int MAXIMUM_BRIGHTNESS = 255;

  /** The half pixel we have to add to hit the middle of the Pixel. */
  private static final double HALF_PIXEL = 0.5;

  /** The quantity of the threads. */
  private final int threadCount;

  /** Which line is the next line to be calculated by the next thread. */
  private int line;

  /**
   * The Constructor generates the Array out of the Resolution. Uses multiple Threads for the
   * calculation.
   * 
   * @param xResolution
   *          The Width Resolution
   * @param yResolution
   *          The Height Resolution
   * @param threadCount
   *          The quantity of Threads, if it is zero as many Threads will generated as the System
   *          has processors .
   */
  public ParallelRaster(final int xResolution, final int yResolution, final int threadCount) {
    super(xResolution, yResolution);
    if (threadCount < 0)
      throw new IllegalArgumentException("At least one thread needed vor calculation");
    else if (threadCount == 0)
      this.threadCount = Runtime.getRuntime().availableProcessors();
    else
      this.threadCount = threadCount;

  }

  /**
   * Returns line value for a thread and increments it afterwards. Synchronized to make sure that
   * every line is only handed to a thread once.
   * 
   * @return actual line(Y-position).
   */
  synchronized int getLine() {
    return line++;
  }

  /**
   * Creates the threads that will calculate the array and waits for them to die with a join method.
   * The real rendering is now outsourced to the Threads run method.
   * 
   * @param rayTracer
   *          is the raytracer object for the scene.
   * @return the filled raster.
   */
  @Override
  public Raster render(final Raytracer rayTracer) {

    final ArrayList<RasterThread> threadArray = new ArrayList<>();

    assert rayTracer != null : "Reference can not be null!";

    for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
      final RasterThread currentThread = new RasterThread(rayTracer, threadIndex);
      threadArray.add(currentThread);
      currentThread.start();
    }

    for (final RasterThread currentThread : threadArray) {
      try {
        currentThread.join();
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      }
    }

    return this;
  }

  public int getThreadCount() {
    return threadCount;
  }

  /**
   * A Thread class for the lines of the an arrayraster. The number of the Lines will be fetched
   * from a shared object. Every threads will die when the number of lines is higher then the height
   * of the raster.
   *
   * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
   * @version 2015-06-15
   */
  class RasterThread extends Thread {

    /** The line of the Array that is currently printed. */
    private int line;

    /** The Raytracer to be called to calculate the value. */
    private final Raytracer rayTracer;

    /** The ID of the Thread. */
    private final int threadID;

    /**
     * Constructor for the Thread called with the raytracer needed to calculate the brightness of
     * the pixels in the lines.
     * 
     * @param rayTracer
     *          the Raytracer that handles the pixel calculations.
     * @param threadID
     *          of the Thread
     */
    public RasterThread(final Raytracer rayTracer, final int threadID) {

      assert rayTracer != null : "Reference can't be null!";

      this.rayTracer = rayTracer;
      this.threadID = threadID;
    }

    @Override
    public long getId() {
      return threadID;
    }

    /**
     * The run Method gets new lines as long as theres one available and calculates the pixels in a
     * line as usual from left to right.
     */
    public void run() {

      final int width = getWidth();
      final int height = getHeight();
      line = getLine();

      assert rayTracer != null : "Reference can't be null!";
      assert width > 0 : "at least one coloumn needed";
      assert height > 0 : "at least one line needed";

      while (line < getHeight()) {
        Stream
            .iterate(0, naturalNumber -> naturalNumber + 1)
            .limit(getWidth())
            .forEach(
                xValue -> setPixel(
                    line,
                    xValue,
                    (int) (MAXIMUM_BRIGHTNESS * rayTracer.tracePrimary((2.0 / width) * xValue
                        + (2.0 / width) * HALF_PIXEL - 1, (2.0 / height) * line + (2.0 / height)
                        * HALF_PIXEL - 1))));
        line = getLine();
      }
    }
  }
}
