package io.sarl.demos.ants;

import com.google.common.base.Objects;
import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.core.OpenEventSpace;
import io.sarl.demos.ants.Ant;
import io.sarl.demos.ants.Environment;
import io.sarl.demos.ants.GuiRepaint;
import io.sarl.demos.ants.Settings;
import io.sarl.demos.ants.StartEnvironment;
import io.sarl.demos.ants.gui.EnvironmentGui;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.scoping.extensions.cast.PrimitiveCastExtensions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The boids simulation launching the SARL environment with the corresponding agent and ensuring the communication between agents and the GUI
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(10)
@SuppressWarnings("all")
public class Simulation implements EventListener {
  public static final UUID id = UUID.randomUUID();
  
  /**
   * SRE Kernel instance
   */
  private SREBootstrap kernel;
  
  /**
   * The default SARL context where environment and boids are spawned
   */
  private AgentContext defaultSARLContext;
  
  /**
   * The list with the position of every town
   */
  private ArrayList<double[]> posList;
  
  /**
   * The distance matrix
   */
  private double[][] distMatrix;
  
  /**
   * The name of the file to open
   */
  private String fileName;
  
  /**
   * Identifier of the environment
   */
  private UUID environment;
  
  private int width = Settings.EnvtWidth;
  
  private int height = Settings.EnvtHeight;
  
  private int antsCount;
  
  /**
   * Boolean specifying id the simulation is started or not.
   */
  private boolean isSimulationStarted = false;
  
  /**
   * the vent space used to establish communication between GUI and agents,
   * Especially enabling GUI to forward start event to the environment,
   * respectively the environment to send GUIRepain at each simulation step to the GUI
   */
  private OpenEventSpace space;
  
  /**
   * The Graphical user interface
   */
  private EnvironmentGui myGUI;
  
  public Simulation(final String fileName) {
    this.antsCount = 0;
    this.fileName = fileName;
  }
  
  /**
   * Sets the file name to open
   * @param _fileName : String, the file name to open
   */
  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }
  
  public void start() {
    this.posList = this.parsor(this.getPath(this.fileName));
    this.antsCount = ((Object[])Conversions.unwrapArray(this.posList, Object.class)).length;
    this.distMatrix = this.getDistMatrix(this.posList);
    this.launchAllAgents();
    this.isSimulationStarted = true;
  }
  
  public void stop() {
    this.killAllAgents();
    this.isSimulationStarted = false;
  }
  
  private void launchAllAgents() {
    try {
      this.kernel = SRE.getBootstrap();
      this.defaultSARLContext = this.kernel.startWithoutAgent();
      this.environment = UUID.randomUUID();
      this.kernel.startAgentWithID(Environment.class, this.environment, Integer.valueOf(this.height), Integer.valueOf(this.width), this.distMatrix, Integer.valueOf(this.antsCount));
      this.launchAllAnts();
      EventSpace _defaultSpace = this.defaultSARLContext.getDefaultSpace();
      this.space = ((OpenEventSpace) _defaultSpace);
      EnvironmentGui _environmentGui = new EnvironmentGui(this.space, this.height, this.width, this.fileName, this.posList);
      this.myGUI = _environmentGui;
      this.space.register(this);
      StartEnvironment _startEnvironment = new StartEnvironment();
      this.space.emit(Simulation.id, _startEnvironment, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void launchAllAnts() {
    int antNum = 0;
    for (int i = 0; (i < this.antsCount); i++) {
      {
        antNum++;
        this.launchAnt(("Ant" + Integer.valueOf(antNum)));
      }
    }
  }
  
  @SuppressWarnings({ "constant_condition", "discouraged_reference" })
  private void launchAnt(final String antName) {
    try {
      UUID id_a = UUID.randomUUID();
      this.kernel.startAgentWithID(Ant.class, id_a, this.environment, antName, this.distMatrix);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Pure
  private void killAllAgents() {
  }
  
  @Override
  @Pure
  public UUID getID() {
    return Simulation.id;
  }
  
  /**
   * Methods managing event coming from agents
   */
  @Override
  public void receiveEvent(final Event event) {
    if ((event instanceof GuiRepaint)) {
      this.myGUI.setTour(((GuiRepaint)event).tour);
      this.myGUI.repaint();
    }
  }
  
  /**
   * Returns the path to get the file
   * @param file : String, the file to get
   * @return String : the complete path to get this file
   */
  @Pure
  public String getPath(final String file) {
    String fileSep = System.getProperty("file.separator");
    String _property = System.getProperty("user.dir");
    String path = ((((_property + fileSep) + "TSP") + fileSep) + file);
    return path;
  }
  
  /**
   * TSP parsor
   * @param path : String, the file path to parse
   * @return ArrayList<double[]> : the coordonates of each town
   */
  public ArrayList<double[]> parsor(final String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<double[]> storing = new ArrayList<double[]>();
      while (sc.hasNextLine()) {
        {
          String line = sc.nextLine();
          boolean _equals = "NODE_COORD_SECTION".equals(line);
          if (_equals) {
            while (sc.hasNextLine()) {
              {
                String next = sc.next();
                boolean _notEquals = (!Objects.equal(next, "EOF"));
                if (_notEquals) {
                  double[] list = new double[3];
                  list[0] = (next == null ? 0 : PrimitiveCastExtensions.doubleValue(next));
                  String _next = sc.next();
                  list[1] = (_next == null ? 0 : PrimitiveCastExtensions.doubleValue(_next));
                  String _next_1 = sc.next();
                  list[2] = (_next_1 == null ? 0 : PrimitiveCastExtensions.doubleValue(_next_1));
                  storing.add(list);
                }
              }
            }
          }
        }
      }
      sc.close();
      return storing;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Prints the coordinates of each town
   * @param coord : ArrayList<Double[]> the list of coordinates
   */
  public void printCoord(final ArrayList<double[]> coord) {
    for (final double[] f : coord) {
      double _get = f[0];
      double _get_1 = f[1];
      double _get_2 = f[2];
      System.out.println(((((("Town n°: " + Double.valueOf(_get)) + " | x=") + Double.valueOf(_get_1)) + " | y=") + Double.valueOf(_get_2)));
    }
  }
  
  /**
   * Return the distance matrix computed from the coordinates list
   * @param coord : ArrayList<double[]>, the list of coordinates
   * @return double[][], the distance matrix
   */
  @Pure
  public double[][] getDistMatrix(final ArrayList<double[]> coordList) {
    int size = coordList.size();
    double[][] distMatrix = new double[size][size];
    for (int i = 0; (i < size); i++) {
      {
        double[] distLine = new double[size];
        for (int j = 0; (j < size); j++) {
          {
            double _get = coordList.get(i)[1];
            double _get_1 = coordList.get(j)[1];
            double dist = Math.pow((_get - _get_1), 2);
            double _get_2 = coordList.get(i)[2];
            double _get_3 = coordList.get(j)[2];
            double _pow = Math.pow((_get_2 - _get_3), 2);
            dist = (dist + _pow);
            dist = Math.pow(dist, 0.5);
            distLine[j] = dist;
          }
        }
        distMatrix[i] = distLine;
      }
    }
    return distMatrix;
  }
  
  /**
   * Prints the distance matrix
   * @param distMatrix : the distance matrix to print
   * @param maxSize : the maximum size of value to print. If maxSize = 5, it will only print the 5x5 matrix (the top left corner)
   */
  public void printDistMatrix(final double[][] distMatrix, final int maxSize) {
    int maxSize2 = maxSize;
    int _size = ((List<double[]>)Conversions.doWrapArray(distMatrix)).size();
    if ((maxSize > _size)) {
      maxSize2 = ((List<double[]>)Conversions.doWrapArray(distMatrix)).size();
    }
    System.out.println((("Distance Matrix (size printed: " + Integer.valueOf(maxSize2)) + "):"));
    for (int i = 0; (i < maxSize2); i++) {
      {
        for (int j = 0; (j < maxSize2); j++) {
          {
            double _get = distMatrix[i][j];
            System.out.print((" " + Integer.valueOf(((int) _get))));
            double _get_1 = distMatrix[i][j];
            if ((_get_1 < 10)) {
              System.out.print("    ");
            } else {
              double _get_2 = distMatrix[i][j];
              if ((_get_2 < 100)) {
                System.out.print("   ");
              } else {
                double _get_3 = distMatrix[i][j];
                if ((_get_3 < 1000)) {
                  System.out.print("  ");
                } else {
                  double _get_4 = distMatrix[i][j];
                  if ((_get_4 < 10000)) {
                    System.out.print(" ");
                  }
                }
              }
            }
          }
        }
        System.out.println();
      }
    }
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Simulation other = (Simulation) obj;
    if (!java.util.Objects.equals(this.fileName, other.fileName)) {
      return false;
    }
    if (!java.util.Objects.equals(this.environment, other.environment)) {
      return false;
    }
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    if (other.antsCount != this.antsCount)
      return false;
    if (other.isSimulationStarted != this.isSimulationStarted)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.fileName);
    result = prime * result + java.util.Objects.hashCode(this.environment);
    result = prime * result + this.width;
    result = prime * result + this.height;
    result = prime * result + this.antsCount;
    result = prime * result + (this.isSimulationStarted ? 1231 : 1237);
    return result;
  }
}
