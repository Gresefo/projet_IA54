package io.sarl.demos.ants;

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
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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
    this.launchAllAgents();
    this.isSimulationStarted = true;
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
    if (!Objects.equals(this.environment, other.environment))
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
    result = prime * result + Objects.hashCode(this.environment);
    result = prime * result + Integer.hashCode(this.width);
    result = prime * result + Integer.hashCode(this.height);
    result = prime * result + Integer.hashCode(this.boidsCount);
    result = prime * result + Boolean.hashCode(this.isSimulationStarted);
    return result;
  }
}
