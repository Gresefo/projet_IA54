package io.sarl.demos.ants;

import com.google.common.base.Objects;
import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.core.OpenEventSpace;
import io.sarl.demos.ants.Environment;
import io.sarl.demos.ants.GuiRepaint;
import io.sarl.demos.ants.PerceivedAntBody;
import io.sarl.demos.ants.Population;
import io.sarl.demos.ants.Settings;
import io.sarl.demos.ants.Start;
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
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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
   * Identifier of the environment
   */
  private UUID environment;
  
  private int width = Settings.EnvtWidth;
  
  private int height = Settings.EnvtHeight;
  
  /**
   * Map buffering boids before launch/start
   */
  private Map<Population, Integer> boidsToLaunch;
  
  /**
   * Map buffering boids' bodies before launch/start
   */
  private ConcurrentHashMap<UUID, PerceivedAntBody> boidBodies;
  
  private int boidsCount;
  
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
  
  public Simulation() {
    this.boidsCount = 0;
    ConcurrentHashMap<UUID, PerceivedAntBody> _concurrentHashMap = new ConcurrentHashMap<UUID, PerceivedAntBody>();
    this.boidBodies = _concurrentHashMap;
    this.boidsToLaunch = CollectionLiterals.<Population, Integer>newHashMap();
  }
  
  public void start() {
    ArrayList<Float[]> test = this.parsor(this.getPath("berlin52.txt"));
    this.printCoord(test);
    float[][] test2 = this.getDistMatrix(test);
    this.printDistMatrix(test2, 60);
  }
  
  public void stop() {
    this.killAllAgents();
    this.isSimulationStarted = false;
  }
  
  /**
   * Add the boids of a population to the simulation.
   * 
   * @param p - the population to add.
   */
  public void addBoid(final Population p) {
    this.boidsCount++;
    if ((!this.isSimulationStarted)) {
      Integer currentBoidCount = this.boidsToLaunch.get(p);
      if ((currentBoidCount != null)) {
        currentBoidCount++;
      } else {
        Integer _integer = new Integer(1);
        currentBoidCount = _integer;
      }
      this.boidsToLaunch.put(p, currentBoidCount);
    } else {
      this.launchBoid(p, ("Boid" + Integer.valueOf(this.boidsCount)));
    }
  }
  
  private void launchAllAgents() {
    try {
      this.kernel = SRE.getBootstrap();
      this.defaultSARLContext = this.kernel.startWithoutAgent();
      this.environment = UUID.randomUUID();
      this.kernel.startAgentWithID(Environment.class, this.environment, Integer.valueOf(this.height), Integer.valueOf(this.width));
      this.launchAllBoids();
      EventSpace _defaultSpace = this.defaultSARLContext.getDefaultSpace();
      this.space = ((OpenEventSpace) _defaultSpace);
      EnvironmentGui _environmentGui = new EnvironmentGui(this.space, this.height, this.width, this.boidBodies);
      this.myGUI = _environmentGui;
      this.space.register(this);
      Start _start = new Start(this.boidBodies);
      this.space.emit(Simulation.id, _start, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void launchAllBoids() {
    int boidNum = 0;
    Set<Map.Entry<Population, Integer>> _entrySet = this.boidsToLaunch.entrySet();
    for (final Map.Entry<Population, Integer> e : _entrySet) {
      for (int i = 0; (i < e.getValue().doubleValue()); i++) {
        {
          boidNum++;
          this.launchBoid(e.getKey(), ("Boid" + Integer.valueOf(boidNum)));
        }
      }
    }
  }
  
  @SuppressWarnings({ "constant_condition", "discouraged_reference" })
  private void launchBoid(final Population p, final String boidName) {
    throw new Error("Unresolved compilation problems:"
      + "\nVector2d cannot be resolved."
      + "\nVector2d cannot be resolved."
      + "\nThe constructor PerceivedAntBody(Population, UUID, Object, Object) refers to the missing type Object");
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
      this.myGUI.setBoids(((GuiRepaint)event).perceivedAgentBody);
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
    String path = ((((_property + fileSep) + "TSP") + fileSep) + "berlin52.txt");
    return path;
  }
  
  /**
   * TSP parsor
   * @param path : String, the file path to parse
   * @return ArrayList<Float[]> : the coordonates of each town
   */
  public ArrayList<Float[]> parsor(final String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<Float[]> storing = new ArrayList<Float[]>();
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
                  Float[] list = new Float[3];
                  list[0] = Float.valueOf((next == null ? 0 : PrimitiveCastExtensions.floatValue(next)));
                  String _next = sc.next();
                  list[1] = Float.valueOf((_next == null ? 0 : PrimitiveCastExtensions.floatValue(_next)));
                  String _next_1 = sc.next();
                  list[2] = Float.valueOf((_next_1 == null ? 0 : PrimitiveCastExtensions.floatValue(_next_1)));
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
   * @param coord : ArrayList<Float[]> the list of coordinates
   */
  public void printCoord(final ArrayList<Float[]> coord) {
    for (final Float[] f : coord) {
      Object _get = f[0];
      Object _get_1 = f[1];
      Object _get_2 = f[2];
      System.out.println(((((("Town n°: " + _get) + " | x=") + _get_1) + " | y=") + _get_2));
    }
  }
  
  /**
   * Return the distance matrix computed from the coordinates list
   * @param coord : ArrayList<float[]>, the list of coordinates
   * @return float[][], the distance matrix
   */
  @Pure
  public float[][] getDistMatrix(final ArrayList<Float[]> coordList) {
    int size = coordList.size();
    float[][] distMatrix = new float[size][size];
    for (int i = 0; (i < size); i++) {
      {
        float[] distLine = new float[size];
        for (int j = 0; (j < size); j++) {
          {
            Float _get = coordList.get(i)[1];
            Float _get_1 = coordList.get(j)[1];
            double dist = Math.pow((((_get) == null ? 0 : (_get).floatValue()) - ((_get_1) == null ? 0 : (_get_1).floatValue())), 2);
            Float _get_2 = coordList.get(i)[2];
            Float _get_3 = coordList.get(j)[2];
            double _pow = Math.pow((((_get_2) == null ? 0 : (_get_2).floatValue()) - ((_get_3) == null ? 0 : (_get_3).floatValue())), 2);
            dist = (dist + _pow);
            dist = Math.pow(dist, 0.5);
            distLine[j] = ((float) dist);
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
  public void printDistMatrix(final float[][] distMatrix, final int maxSize) {
    int maxSize2 = maxSize;
    int _size = ((List<float[]>)Conversions.doWrapArray(distMatrix)).size();
    if ((maxSize > _size)) {
      maxSize2 = ((List<float[]>)Conversions.doWrapArray(distMatrix)).size();
    }
    System.out.println((("Distance Matrix (size printed: " + Integer.valueOf(maxSize2)) + "):"));
    for (int i = 0; (i < maxSize2); i++) {
      {
        for (int j = 0; (j < maxSize2); j++) {
          {
            float _get = distMatrix[i][j];
            System.out.print((" " + Integer.valueOf(((int) _get))));
            float _get_1 = distMatrix[i][j];
            if ((_get_1 < 10)) {
              System.out.print("    ");
            } else {
              float _get_2 = distMatrix[i][j];
              if ((_get_2 < 100)) {
                System.out.print("   ");
              } else {
                float _get_3 = distMatrix[i][j];
                if ((_get_3 < 1000)) {
                  System.out.print("  ");
                } else {
                  float _get_4 = distMatrix[i][j];
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
    if (!java.util.Objects.equals(this.environment, other.environment)) {
      return false;
    }
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    if (other.boidsCount != this.boidsCount)
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
    result = prime * result + java.util.Objects.hashCode(this.environment);
    result = prime * result + this.width;
    result = prime * result + this.height;
    result = prime * result + this.boidsCount;
    result = prime * result + (this.isSimulationStarted ? 1231 : 1237);
    return result;
  }
}
