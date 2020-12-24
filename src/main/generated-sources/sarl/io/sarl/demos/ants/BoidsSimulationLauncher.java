package io.sarl.demos.ants;

import io.sarl.demos.ants.Simulation;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;

/**
 * The main class configuring the various boids populations and launching the simulation
 * 
 * @author Nicolas Gaud
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class BoidsSimulationLauncher {
  /**
   * @param args command line arguments
   */
  public static void main(final String... args) {
    String fileName = "berlin52.txt";
    Simulation simu = new Simulation(fileName);
    simu.start();
  }
  
  @SyntheticMember
  public BoidsSimulationLauncher() {
    super();
  }
}
