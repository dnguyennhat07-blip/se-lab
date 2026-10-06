package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Class storing and managing the torpedoes of a ship
*
* (Deliberately contains bugs for testing purposes)
*/
public class TorpedoStore {

  // RATE_OF_FAILURE to be considered for failure
  // 0.0: never fails, 1.0: always fails
  private double failureRate = 0.0;

  private int torpedoCount = 0;

  // Fix #2: Mezőként újrahasznosított Random példány
  private Random generator = new Random();

  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    // update failure rate if it was specified in an environment variable
    String failureEnv = System.getenv("FAILURE_RATE");
    if (failureEnv != null){
      try {
        this.failureRate = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        this.failureRate = 0.0;
      }
    }
  }

  public boolean fire(int numberOfTorpedos){
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
      // Fix #1: Hiányzó throw pótolva
      throw new IllegalArgumentException("numberOfTorpedos");
    }

    boolean success = false;

    // Fix #2: A meglévő generator mezőt használjuk
    double r = this.generator.nextDouble();

    // Ellenőrizzük, hogy a generált véletlenszám eléri-e a hibázási küszöböt a sikeres kilövéshez
    if (r >= this.failureRate) {
      // Fix #3: Értékadás helyett kivonás (-=)
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulate failure
      success = false;
    }

    return success;
  }

  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}