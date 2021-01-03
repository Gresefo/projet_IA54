package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;

/**
 * General configuration of the ants simulation
 */
@SarlSpecification("0.10")
@SarlElementType(11)
@SuppressWarnings("all")
public interface Settings {
  /**
   * Environmental grid default width
   */
  public static final int EnvtWidth = 1000;
  
  /**
   * Environmental grid default height
   */
  public static final int EnvtHeight = 1000;
  
  /**
   * Boolean specifying whether message logs are activated or not
   */
  public static final boolean isLogActivated = true;
  
  /**
   * Specify a pause delay before each ants sends his influence to the environment, and respectively before the environment sends perceptions to ants
   */
  public static final int pause = 0;
  
  /**
   * The number of iteration the ants should do
   */
  public static final int iteration = 10;
  
  /**
   * The limit number of iteration without changes
   * If there was more than nbIterationToConverge iterations without changes, we stop the program and kill all agents
   */
  public static final int nbIterationToConverge = 8;
  
  public static final int alpha = 1;
  
  public static final int beta = 3;
  
  /**
   * Pheromone evaporation rate
   */
  public static final double rho = 0.5;
}
