package io.sarl.demos.ants;

import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.demos.ants.Die;
import io.sarl.demos.ants.Settings;
import io.sarl.demos.ants.StartAnt;
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
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("potential_field_synchronization_problem")
@SarlSpecification("0.11")
@SarlElementType(19)
public class Ant extends Agent {
  private UUID environment;
  
  private double[][] distance;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    int _size = ((List<Object>)Conversions.doWrapArray(occurrence.parameters)).size();
    if ((_size > 2)) {
      Object _get = occurrence.parameters[0];
      if ((_get instanceof UUID)) {
        Object _get_1 = occurrence.parameters[0];
        this.environment = ((UUID) _get_1);
      }
      Object _get_2 = occurrence.parameters[1];
      if ((_get_2 instanceof String)) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        Object _get_3 = occurrence.parameters[1];
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName((_get_3 == null ? null : _get_3.toString()));
      }
      Object _get_4 = occurrence.parameters[2];
      if ((_get_4 instanceof double[][])) {
        Object _get_5 = occurrence.parameters[2];
        this.distance = ((double[][]) _get_5);
      }
    }
    if (Settings.isLogActivated) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Ants activated");
    }
  }
  
  private void $behaviorUnit$StartAnt$1(final StartAnt occurrence) {
    double[][] pheromons = occurrence.pheromons;
    int size = this.distance.length;
    double tourLength = 0.0;
    ArrayList<Integer> memory = new ArrayList<Integer>();
    ArrayList<Integer> citiesToVisit = new ArrayList<Integer>();
    for (int i = 1; (i < size); i++) {
      citiesToVisit.add(Integer.valueOf(i));
    }
    ArrayList<Double> prob = new ArrayList<Double>();
    double probability = 0;
    memory.add(Integer.valueOf(0));
    int currentCity = 0;
    while ((memory.size() != size)) {
      {
        double sum = 0.0;
        for (final Integer i : citiesToVisit) {
          double _pow = Math.pow(pheromons[currentCity][((i) == null ? 0 : (i).intValue())], Settings.alpha);
          double _get = this.distance[currentCity][((i) == null ? 0 : (i).intValue())];
          double _pow_1 = Math.pow((1 / _get), Settings.beta);
          sum = (sum + (_pow * _pow_1));
        }
        for (final Integer i_1 : citiesToVisit) {
          {
            double _pow_2 = Math.pow(pheromons[currentCity][((i_1) == null ? 0 : (i_1).intValue())], Settings.alpha);
            double _get_1 = this.distance[currentCity][((i_1) == null ? 0 : (i_1).intValue())];
            double _pow_3 = Math.pow((1 / _get_1), Settings.beta);
            probability = ((_pow_2 * _pow_3) / sum);
            prob.add(Double.valueOf(probability));
          }
        }
        double cityChosen = Math.random();
        double cumul = 0.0;
        int i_2 = 0;
        while ((cumul < cityChosen)) {
          {
            Double _get_1 = prob.get(i_2);
            cumul = (cumul + ((_get_1) == null ? 0 : (_get_1).doubleValue()));
            i_2 = (i_2 + 1);
          }
        }
        double _get_1 = this.distance[currentCity][((citiesToVisit.get((i_2 - 1))) == null ? 0 : (citiesToVisit.get((i_2 - 1))).intValue())];
        tourLength = (tourLength + _get_1);
        currentCity = ((citiesToVisit.get((i_2 - 1))) == null ? 0 : (citiesToVisit.get((i_2 - 1))).intValue());
        memory.add(Integer.valueOf(currentCity));
        citiesToVisit.remove((i_2 - 1));
        prob.clear();
      }
    }
    double _get = this.distance[((memory.get((size - 1))) == null ? 0 : (memory.get((size - 1))).intValue())][1];
    tourLength = (tourLength + _get);
    memory.add(Integer.valueOf(0));
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_environment;
      
      public $SerializableClosureProxy(final UUID $_environment) {
        this.$_environment = $_environment;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_environment);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, Ant.this.environment);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, Ant.this.environment);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(new TourFound(memory, tourLength), _function);
  }
  
  private void $behaviorUnit$Die$2(final Die occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
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
  private void $guardEvaluator$Die(final Die occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Die$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartAnt(final StartAnt occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartAnt$1(occurrence));
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
    Ant other = (Ant) obj;
    if (!java.util.Objects.equals(this.environment, other.environment))
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
    return result;
  }
  
  @SyntheticMember
  public Ant(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Ant(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Ant(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
