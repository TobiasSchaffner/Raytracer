package edu.hm.schaffner.tobias.tracer;

import java.util.Optional;
import java.util.function.BooleanSupplier;

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
 * Delivers a boolean a boolean with the information if there intersection is enlighted or not. This
 * information will only be calculated if needed and if it hasn't already.
 *
 * @author Tobias Schaffner, tobias.schaffner@hm.edu, Deniz Oktay, doktay@hm.edu
 * @version 2015-06-08
 */
public class BooleanPromise {

  /** The suppling lambda provided by the call. */
  private final BooleanSupplier supplier;

  /** The boolean telling us if there's a shadow if already calculated. */
  private Optional<Boolean> promise = Optional.empty();

  /**
   * The constructor takes the supplier.
   * 
   * @param supplier
   *          is the lambda given.
   */

  public BooleanPromise(BooleanSupplier supplier) {

    assert supplier != null : "Referenz can't be null!";

    this.supplier = supplier;
  }

  /**
   * The get method returns the boolean if its already present. If not it will be calculated.
   * 
   * @return the boolean weather theres a shadow or not.
   */
  @SuppressWarnings("PMD.BooleanGetMethodName")
  public boolean get() {
    if (promise.isPresent())
      return promise.get();
    promise = Optional.of(supplier.getAsBoolean());
    return promise.get();
  }
}
