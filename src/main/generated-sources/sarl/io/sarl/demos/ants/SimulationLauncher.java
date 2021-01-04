package io.sarl.demos.ants;

import io.sarl.demos.ants.Simulation;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;

/**
 * The main class launching the simulation
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class SimulationLauncher {
  /**
   * @param args command line arguments
   */
  public static void main(final String... args) {
    String fileName = args[0];
    Simulation simu = new Simulation(fileName);
    simu.start();
  }
  
  @SyntheticMember
  public SimulationLauncher() {
    super();
  }
}
