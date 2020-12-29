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
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The environmental agent in charge of collecting ants influences and computing the new state of the virtual world
 */
@SarlSpecification("0.10")
@SarlElementType(19)
@SuppressWarnings("all")
public class Environment extends Agent {
  @Accessors
  private int width;
  
  @Accessors
  private int height;
  
  @Accessors
  private double[][] distMatrix;
  
  private int numberAnts;
  
  private double[][] pheromons;
  
  private double nnTourLength;
  
  private CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>> tourArray;
  
  private int iteration;
  
  private ArrayList<Double> lastTour;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("Environment");
    int _size = ((List<Object>)Conversions.doWrapArray(occurrence.parameters)).size();
    if ((_size > 1)) {
      Object _get = occurrence.parameters[0];
      if ((_get instanceof Integer)) {
        Object _get_1 = occurrence.parameters[0];
        this.height = ((((Integer) _get_1)) == null ? 0 : (((Integer) _get_1)).intValue());
      }
      Object _get_2 = occurrence.parameters[1];
      if ((_get_2 instanceof Integer)) {
        Object _get_3 = occurrence.parameters[1];
        this.width = ((((Integer) _get_3)) == null ? 0 : (((Integer) _get_3)).intValue());
      }
      Object _get_4 = occurrence.parameters[2];
      if ((_get_4 instanceof double[][])) {
        Object _get_5 = occurrence.parameters[2];
        this.distMatrix = ((double[][]) _get_5);
      }
      Object _get_6 = occurrence.parameters[3];
      if ((_get_6 instanceof Integer)) {
        Object _get_7 = occurrence.parameters[3];
        this.numberAnts = ((((Integer) _get_7)) == null ? 0 : (((Integer) _get_7)).intValue());
      }
    }
    if (Settings.isLogActivated) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Environment activated");
    }
  }
  
  private void $behaviorUnit$StartEnvironment$1(final StartEnvironment occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ENV STARTED");
    CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>> _copyOnWriteArrayList = new CopyOnWriteArrayList<Pair<ArrayList<Integer>, Double>>();
    this.tourArray = _copyOnWriteArrayList;
    ArrayList<Double> _arrayList = new ArrayList<Double>();
    this.lastTour = _arrayList;
    this.iteration = 0;
    this.nnTourLength = this.nearestNeighbour(this.distMatrix);
    int size = this.distMatrix.length;
    this.pheromons = new double[size][size];
    for (int i = 0; (i < size); i++) {
      for (int j = 0; (j < size); j++) {
        if ((i == j)) {
          this.pheromons[i][j] = 0.0;
        } else {
          this.pheromons[i][j] = (this.numberAnts / this.nnTourLength);
        }
      }
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(new StartAnt(this.pheromons));
  }
  
  private void $behaviorUnit$TourFound$2(final TourFound occurrence) {
    synchronized (this.tourArray) {
      Pair<ArrayList<Integer>, Double> _pair = new Pair<ArrayList<Integer>, Double>(occurrence.tour, Double.valueOf(occurrence.length));
      this.tourArray.add(_pair);
      int _size = this.tourArray.size();
      if ((_size == this.numberAnts)) {
        double phero = 0;
        double min = ((this.tourArray.get(0).getValue()) == null ? 0 : (this.tourArray.get(0).getValue()).doubleValue());
        int index = 0;
        int k = 0;
        for (int i = 0; (i < this.numberAnts); i++) {
          for (int j = 0; (j < this.numberAnts); j++) {
            if ((i != j)) {
              double _get = this.pheromons[i][j];
              phero = (_get * (1 - Settings.rho));
              this.pheromons[i][j] = phero;
            }
          }
        }
        for (final Pair<ArrayList<Integer>, Double> tA : this.tourArray) {
          {
            Double _value = tA.getValue();
            phero = (1 / ((_value) == null ? 0 : (_value).doubleValue()));
            Double _value_1 = tA.getValue();
            if ((_value_1.doubleValue() < min)) {
              min = ((tA.getValue()) == null ? 0 : (tA.getValue()).doubleValue());
              index = k;
            }
            k++;
            for (int i = 0; (i < (this.numberAnts - 1)); i++) {
              this.pheromons[((tA.getKey().get(i)) == null ? 0 : (tA.getKey().get(i)).intValue())][((tA.getKey().get((i + 1))) == null ? 0 : (tA.getKey().get((i + 1))).intValue())] = phero;
            }
          }
        }
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        Double _value = this.tourArray.get(index).getValue();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Best tour : " + _value));
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        ArrayList<Integer> _key = this.tourArray.get(index).getKey();
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
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(new GuiRepaint(_key), _function);
        if ((this.iteration == Settings.iteration)) {
          this.printDistMatrix(this.pheromons, 25);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("KILL AGENTS");
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          ArrayList<Integer> _key_1 = this.tourArray.get(index).getKey();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Tour : " + _key_1));
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(new Die());
        } else {
          int size = this.lastTour.size();
          if (((size - 10) >= 0)) {
            int same = 0;
            for (int i = (size - 10); (i < size); i++) {
              Double _value_1 = this.tourArray.get(index).getValue();
              Double _get = this.lastTour.get(i);
              double _minus = DoubleExtensions.operator_minus(_value_1, _get);
              double _abs = Math.abs(_minus);
              if ((_abs < 1.0)) {
                same++;
              }
            }
            if ((same == 10)) {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(("Stopped at iteration : " + Integer.valueOf(this.iteration)));
              this.iteration = (Settings.iteration - 1);
            }
          }
          this.lastTour.add(this.tourArray.get(index).getValue());
        }
        this.tourArray.clear();
        this.iteration++;
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(new StartAnt(this.pheromons));
      }
    }
  }
  
  private void $behaviorUnit$Die$3(final Die occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
  }
  
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
    return tourLength;
  }
  
  protected void printDistMatrix(final double[][] distMatrix, final int maxSize) {
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
            System.out.print((" " + Double.valueOf(_get)));
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
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Logging.class, ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || $0$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING = $0$getSkill(Logging.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LOGGING)", imported = Logging.class)
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(DefaultContextInteractions.class, ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $0$getSkill(DefaultContextInteractions.class)) : $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS)", imported = DefaultContextInteractions.class)
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Schedules.class, ($0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || $0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $0$getSkill(Schedules.class)) : $0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES)", imported = Schedules.class)
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Lifecycle.class, ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $0$getSkill(Lifecycle.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE)", imported = Lifecycle.class)
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
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    if (other.numberAnts != this.numberAnts)
      return false;
    if (Double.doubleToLongBits(other.nnTourLength) != Double.doubleToLongBits(this.nnTourLength))
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
    result = prime * result + this.width;
    result = prime * result + this.height;
    result = prime * result + this.numberAnts;
    result = prime * result + (int) (Double.doubleToLongBits(this.nnTourLength) ^ (Double.doubleToLongBits(this.nnTourLength) >>> 32));
    result = prime * result + this.iteration;
    return result;
  }
  
  @SyntheticMember
  public Environment(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Environment(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Environment(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
  
  @Pure
  protected int getWidth() {
    return this.width;
  }
  
  protected void setWidth(final int width) {
    this.width = width;
  }
  
  @Pure
  protected int getHeight() {
    return this.height;
  }
  
  protected void setHeight(final int height) {
    this.height = height;
  }
  
  @Pure
  protected double[][] getDistMatrix() {
    return this.distMatrix;
  }
  
  protected void setDistMatrix(final double[][] distMatrix) {
    this.distMatrix = distMatrix;
  }
}
