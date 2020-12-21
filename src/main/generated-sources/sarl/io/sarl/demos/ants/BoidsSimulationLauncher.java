package io.sarl.demos.ants;

import io.sarl.demos.ants.Population;
import io.sarl.demos.ants.Simulation;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Color;

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
    Simulation simu = new Simulation();
    Population pRed = new Population(Color.RED);
    Population pGreen = new Population(Color.GREEN);
    Population pBlue = new Population(Color.BLUE);
    for (int i = 0; (i < pRed.popSize); i++) {
      simu.addBoid(pRed);
    }
    for (int i = 0; (i < pGreen.popSize); i++) {
      simu.addBoid(pGreen);
    }
    for (int i = 0; (i < pBlue.popSize); i++) {
      simu.addBoid(pBlue);
    }
    simu.start();
  }
  
  @SyntheticMember
  public BoidsSimulationLauncher() {
    super();
  }
}
