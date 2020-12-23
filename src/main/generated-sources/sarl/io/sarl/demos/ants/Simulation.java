package io.sarl.demos.ants;

import com.google.common.base.Objects;
import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.core.OpenEventSpace;
import io.sarl.demos.ants.Boid;
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
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The boids simulation launching the SARL environment with the corresponding agent and ensuring the communication between agents and the GUI
 * @author Nicolas Gaud
 */
@SarlSpecification("0.11")
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
    ArrayList<ArrayList<Integer>> test2 = this.getDistMatrix(test);
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
    try {
      double _random = Math.random();
      double _random_1 = Math.random();
      Vector2d initialPosition = new Vector2d(((_random - 0.5) * this.width), ((_random_1 - 0.5) * this.height));
      double _random_2 = Math.random();
      double _random_3 = Math.random();
      Vector2d initialVitesse = new Vector2d((_random_2 - 0.5), (_random_3 - 0.5));
      UUID b = UUID.randomUUID();
      this.kernel.startAgentWithID(Boid.class, b, this.environment, p, initialPosition, initialVitesse, boidName);
      PerceivedAntBody _perceivedAntBody = new PerceivedAntBody(p, b, initialPosition, initialVitesse);
      this.boidBodies.put(b, _perceivedAntBody);
      if (Settings.isLogActivated) {
        System.out.println(((("Lancement d\'un boid à la position " + initialPosition) + " et avec une vitesse de ") + initialVitesse));
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
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
                  String _next = sc.next();
                  String _next_1 = sc.next();
                  Float[] list = ((Float[])Conversions.unwrapArray(Collections.<Float>unmodifiableSet(CollectionLiterals.<Float>newHashSet(Float.valueOf((next == null ? 0 : PrimitiveCastExtensions.floatValue(next))), Float.valueOf((_next == null ? 0 : PrimitiveCastExtensions.floatValue(_next))), Float.valueOf((_next_1 == null ? 0 : PrimitiveCastExtensions.floatValue(_next_1))))), Float.class));
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
   * @param coord : ArrayList<Float[]>, the list of coordinates
   * @return ArrayList<ArrayList<Integer>>, the distance matrix
   */
  @Pure
  public ArrayList<ArrayList<Integer>> getDistMatrix(final ArrayList<Float[]> coordList) {
    return null;
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
    if (!java.util.Objects.equals(this.environment, other.environment))
      return false;
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
    result = prime * result + Integer.hashCode(this.width);
    result = prime * result + Integer.hashCode(this.height);
    result = prime * result + Integer.hashCode(this.boidsCount);
    result = prime * result + Boolean.hashCode(this.isSimulationStarted);
    return result;
  }
}
