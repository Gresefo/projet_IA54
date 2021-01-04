package io.sarl.demos.ants;

import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.demos.ants.Die;
import io.sarl.demos.ants.GuiRepaint;
import io.sarl.demos.ants.Settings;
import io.sarl.demos.ants.Simulation;
import io.sarl.demos.ants.StartAnt;
import io.sarl.demos.ants.StartEnvironment;
import io.sarl.demos.ants.TourFound;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The environmental agent in charge of collecting ants influences and computing the new state of the virtual world
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Environment extends Agent {
  /**
   * Matrix of distance between cities
   */
  private double[][] distMatrix;
  
  /**
   * Number of ants searching a tour
   */
  private int numberAnts;
  
  /**
   * Pheromones matrix for each edge
   */
  private double[][] pheromones;
  
  /**
   * A thread safe List<Pair> to store the list of :
   * 	- List<Integer> : the path with the town ID
   *  - Double : the path length
   */
  private CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>> tourArray;
  
  /**
   * The current iteration number
   */
  private int iteration;
  
  /**
   * Used to check if the result converged
   */
  private ArrayList<Double> lastTour;
  
  private Pair<ArrayList<Integer>, Double> saveBestTour;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("Environment");
    int _size = ((List<Object>)Conversions.doWrapArray(occurrence.parameters)).size();
    if ((_size > 1)) {
      Object _get = occurrence.parameters[0];
      if ((_get instanceof double[][])) {
        Object _get_1 = occurrence.parameters[0];
        this.distMatrix = ((double[][]) _get_1);
      }
      Object _get_2 = occurrence.parameters[1];
      if ((_get_2 instanceof Integer)) {
        Object _get_3 = occurrence.parameters[1];
        this.numberAnts = ((((Integer) _get_3)) == null ? 0 : (((Integer) _get_3)).intValue());
      }
    }
    if (Settings.isLogActivated) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Environment activated");
    }
  }
  
  private void $behaviorUnit$StartEnvironment$1(final StartEnvironment occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ENV STARTED");
    CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>> _copyOnWriteArrayList = new CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>>();
    this.tourArray = _copyOnWriteArrayList;
    ArrayList<Double> _arrayList = new ArrayList<Double>();
    this.lastTour = _arrayList;
    ArrayList<Integer> _arrayList_1 = new ArrayList<Integer>();
    Double _double = new Double((-1));
    Pair<ArrayList<Integer>, Double> _pair = new Pair<ArrayList<Integer>, Double>(_arrayList_1, _double);
    this.saveBestTour = _pair;
    this.iteration = 0;
    double nnTourLength = this.nearestNeighbour(this.distMatrix);
    int size = this.distMatrix.length;
    this.pheromones = new double[size][size];
    for (int i = 0; (i < size); i++) {
      for (int j = 0; (j < size); j++) {
        if ((i == j)) {
          this.pheromones[i][j] = 0.0;
        } else {
          this.pheromones[i][j] = (this.numberAnts / nnTourLength);
        }
      }
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(new StartAnt(this.pheromones));
  }
  
  private void $behaviorUnit$TourFound$2(final TourFound occurrence) {
    synchronized (this.tourArray) {
      Pair<ArrayList<Integer>, Double> _pair = new Pair<ArrayList<Integer>, Double>(occurrence.tour, Double.valueOf(occurrence.length));
      this.tourArray.add(_pair);
      int _size = this.tourArray.size();
      if ((_size == this.numberAnts)) {
        double phero = 0;
        double min = ((this.tourArray.get(0).getValue()) == null ? 0 : (this.tourArray.get(0).getValue()).doubleValue());
        int indexBestTour = 0;
        int k = 0;
        for (int i = 0; (i < this.numberAnts); i++) {
          for (int j = 0; (j < this.numberAnts); j++) {
            if ((i != j)) {
              double _get = this.pheromones[i][j];
              phero = (_get * (1 - Settings.rho));
              this.pheromones[i][j] = phero;
            }
          }
        }
        for (final Pair<ArrayList<Integer>, Double> tA : this.tourArray) {
          {
            Double _value = tA.getValue();
            if ((_value.doubleValue() < min)) {
              min = ((tA.getValue()) == null ? 0 : (tA.getValue()).doubleValue());
              indexBestTour = k;
            }
            k++;
            Double _value_1 = tA.getValue();
            phero = (1 / ((_value_1) == null ? 0 : (_value_1).doubleValue()));
            for (int i = 0; (i < (tA.getKey().size() - 1)); i++) {
              double _get = this.pheromones[((tA.getKey().get(i)) == null ? 0 : (tA.getKey().get(i)).intValue())][((tA.getKey().get((i + 1))) == null ? 0 : (tA.getKey().get((i + 1))).intValue())];
              this.pheromones[((tA.getKey().get(i)) == null ? 0 : (tA.getKey().get(i)).intValue())][
                ((tA.getKey().get((i + 1))) == null ? 0 : (tA.getKey().get((i + 1))).intValue())] = 
                (_get + phero);
            }
          }
        }
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        Double _value = this.tourArray.get(indexBestTour).getValue();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Best tour : " + _value));
        ArrayList<Integer> tmpList = new ArrayList<Integer>();
        for (int i = 0; (i < this.tourArray.get(indexBestTour).getKey().size()); i++) {
          Integer _get = this.tourArray.get(indexBestTour).getKey().get(i);
          tmpList.add(Integer.valueOf((((_get) == null ? 0 : (_get).intValue()) + 1)));
        }
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        Double _value_1 = this.tourArray.get(indexBestTour).getValue();
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID $_id_1;
          
          public $SerializableClosureProxy(final UUID $_id_1) {
            this.$_id_1 = $_id_1;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, $_id_1);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, Simulation.id);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, Simulation.id);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(new GuiRepaint(tmpList, ((_value_1) == null ? 0 : (_value_1).doubleValue()), this.iteration), _function);
        if (((this.saveBestTour.getValue() != null && (this.saveBestTour.getValue().doubleValue() == (-1))) || (this.tourArray.get(indexBestTour).getValue().doubleValue() < 
          this.saveBestTour.getValue().doubleValue()))) {
          ArrayList<Integer> _key = this.tourArray.get(indexBestTour).getKey();
          Double _value_2 = this.tourArray.get(indexBestTour).getValue();
          Pair<ArrayList<Integer>, Double> _pair_1 = new Pair<ArrayList<Integer>, Double>(_key, _value_2);
          this.saveBestTour = _pair_1;
        }
        if ((this.iteration == Settings.iteration)) {
          this.printPheromoneMatrix(this.pheromones, 25);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("KILL AGENTS");
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          ArrayList<Integer> _key_1 = this.tourArray.get(indexBestTour).getKey();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Tour : " + _key_1));
          tmpList.clear();
          for (int i = 0; (i < this.saveBestTour.getKey().size()); i++) {
            Integer _get = this.saveBestTour.getKey().get(i);
            tmpList.add(Integer.valueOf((((_get) == null ? 0 : (_get).intValue()) + 1)));
          }
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          Double _value_3 = this.saveBestTour.getValue();
          class $SerializableClosureProxy_1 implements Scope<Address> {
            
            private final UUID $_id_1;
            
            public $SerializableClosureProxy_1(final UUID $_id_1) {
              this.$_id_1 = $_id_1;
            }
            
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, $_id_1);
            }
          }
          final Scope<Address> _function_1 = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, Simulation.id);
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy_1.class, Simulation.id);
            }
          };
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(new GuiRepaint(tmpList, ((_value_3) == null ? 0 : (_value_3).doubleValue()), this.iteration), _function_1);
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(new Die());
        } else {
          int size = this.lastTour.size();
          int same = 0;
          if ((size >= Settings.nbIterationToConverge)) {
            for (int i = (size - Settings.nbIterationToConverge); (i < size); i++) {
              Double _value_4 = this.tourArray.get(indexBestTour).getValue();
              Double _get = this.lastTour.get(i);
              double _minus = DoubleExtensions.operator_minus(_value_4, _get);
              double _abs = Math.abs(_minus);
              if ((_abs < 1.0)) {
                same++;
              }
            }
          }
          if ((same == Settings.nbIterationToConverge)) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(("Stopped at iteration : " + Integer.valueOf(this.iteration)));
            this.iteration = (Settings.iteration - 1);
          } else {
            this.lastTour.add(this.tourArray.get(indexBestTour).getValue());
            this.tourArray.clear();
            this.iteration++;
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
            _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.emit(new StartAnt(this.pheromones));
          }
        }
      }
    }
  }
  
  private void $behaviorUnit$Die$3(final Die occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
  }
  
  /**
   * Calculate a tour using the nearest neighbor algorithm
   * @param distMatrix : double[][], the matrix distance
   * @return double : the length of the path
   */
  protected double nearestNeighbour(final double[][] distMatrix) {
    double tourLength = 0.0;
    int size = this.distMatrix.length;
    ArrayList<Integer> tour = new ArrayList<Integer>();
    ArrayList<Integer> citiesToVisit = new ArrayList<Integer>();
    for (int i = 1; (i < size); i++) {
      citiesToVisit.add(Integer.valueOf(i));
    }
    tour.add(Integer.valueOf(0));
    int currentCity = 0;
    int index = 0;
    double min = 0.0;
    while ((tour.size() != size)) {
      {
        if ((currentCity == 0)) {
          min = distMatrix[currentCity][1];
          index = 1;
        } else {
          min = distMatrix[currentCity][0];
          index = 0;
        }
        for (final Integer i : citiesToVisit) {
          double _get = distMatrix[currentCity][((i) == null ? 0 : (i).intValue())];
          if ((_get < min)) {
            min = distMatrix[currentCity][((i) == null ? 0 : (i).intValue())];
            index = ((i) == null ? 0 : (i).intValue());
          }
        }
        double _get_1 = distMatrix[currentCity][index];
        tourLength = (tourLength + _get_1);
        currentCity = index;
        tour.add(Integer.valueOf(currentCity));
        citiesToVisit.remove(Integer.valueOf(currentCity));
      }
    }
    double _get = distMatrix[currentCity][0];
    tourLength = (tourLength + _get);
    return tourLength;
  }
  
  /**
   * Prints the pheromone matrix as double
   * @param distMatrix : double [][], the distance matrix to print
   * @param maxSize : int, the maximum size of value to print. If maxSize = 5, it will only print the 5x5 matrix (the top left corner)
   */
  protected void printPheromoneMatrix(final double[][] distMatrix, final int maxSize) {
    int maxSize2 = maxSize;
    int _size = ((List<double[]>)Conversions.doWrapArray(distMatrix)).size();
    if ((maxSize > _size)) {
      maxSize2 = ((List<double[]>)Conversions.doWrapArray(distMatrix)).size();
    }
    System.out.println((("Pheromone Matrix (size printed: " + Integer.valueOf(maxSize2)) + "):"));
    for (int i = 0; (i < maxSize2); i++) {
      {
        for (int j = 0; (j < maxSize2); j++) {
          {
            double _get = distMatrix[i][j];
            String _format = String.format("%.2e", Double.valueOf(_get));
            System.out.print((" " + _format));
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
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartEnvironment(final StartEnvironment occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartEnvironment$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Die(final Die occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Die$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TourFound(final TourFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TourFound$2(occurrence));
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
    Environment other = (Environment) obj;
    if (other.numberAnts != this.numberAnts)
      return false;
    if (other.iteration != this.iteration)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.numberAnts);
    result = prime * result + Integer.hashCode(this.iteration);
    return result;
  }
  
  @SyntheticMember
  public Environment(final UUID arg0, final UUID arg1) {
    super(arg0, arg1);
  }
  
  @SyntheticMember
  @Deprecated
  @Inject
  public Environment(final BuiltinCapacitiesProvider arg0, final UUID arg1, final UUID arg2) {
    super(arg0, arg1, arg2);
  }
  
  @SyntheticMember
  @Inject
  public Environment(final UUID arg0, final UUID arg1, final DynamicSkillProvider arg2) {
    super(arg0, arg1, arg2);
  }
}
