package io.sarl.demos.ants;

import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.demos.ants.Action;
import io.sarl.demos.ants.Die;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.inject.Inject;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
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
  private ConcurrentSkipListSet<UUID> influences;
  
  private Double[][] distMatrix;
  
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
      if ((_get_4 instanceof Double[][])) {
        Object _get_5 = occurrence.parameters[2];
        this.distMatrix = ((Double[][]) _get_5);
      }
      ConcurrentSkipListSet<UUID> _concurrentSkipListSet = new ConcurrentSkipListSet<UUID>();
      this.influences = _concurrentSkipListSet;
    }
  }
  
  private void $behaviorUnit$void$1(final /* Start */Object occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method ants(Object) is undefined for the type Environment"
      + "\nThe method or field ants is undefined"
      + "\nThe method or field ants is undefined"
      + "\nThe constructor GuiRepaint(ConcurrentHashMap<UUID, PerceivedAntBody>) refers to the missing type PerceivedAntBody"
      + "\nThe constructor Perception(ConcurrentHashMap<UUID, PerceivedAntBody>) refers to the missing type PerceivedAntBody"
      + "\nperceivedAgentBody cannot be resolved");
  }
  
  private void $behaviorUnit$Action$2(final Action occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field ants is undefined"
      + "\nThe method or field ants is undefined"
      + "\nThe method or field ants is undefined"
      + "\nThe method or field ants is undefined for the type Environment"
      + "\nThe method or field ants is undefined"
      + "\nThe method or field ants is undefined"
      + "\nThe method applyForce(Vector2d, PerceivedAntBody) from the type Environment refers to the missing type Object"
      + "\nThe field Action.influence refers to the missing type Vector2d"
      + "\nThe constructor GuiRepaint(ConcurrentHashMap<UUID, PerceivedAntBody>) refers to the missing type PerceivedAntBody"
      + "\nThe constructor Perception(ConcurrentHashMap<UUID, PerceivedAntBody>) refers to the missing type PerceivedAntBody"
      + "\ncontainsKey cannot be resolved"
      + "\nget cannot be resolved"
      + "\nsize cannot be resolved");
  }
  
  private void $behaviorUnit$Die$3(final Die occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
  }
  
  protected Object applyForce(final /* Vector2d */Object force, final /* PerceivedAntBody */Object b) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field ants is undefined"
      + "\nlength cannot be resolved"
      + "\n> cannot be resolved"
      + "\ngroup cannot be resolved"
      + "\nmaxForce cannot be resolved"
      + "\nlength cannot be resolved"
      + "\ngroup cannot be resolved"
      + "\nmaxForce cannot be resolved"
      + "\nacceleration cannot be resolved"
      + "\nset cannot be resolved"
      + "\nvitesse cannot be resolved"
      + "\n+= cannot be resolved"
      + "\nlength cannot be resolved"
      + "\n> cannot be resolved"
      + "\ngroup cannot be resolved"
      + "\nmaxSpeed cannot be resolved"
      + "\nlength cannot be resolved"
      + "\ngroup cannot be resolved"
      + "\nmaxSpeed cannot be resolved"
      + "\nposition cannot be resolved"
      + "\n+= cannot be resolved"
      + "\nget cannot be resolved"
      + "\nowner cannot be resolved"
      + "\nacceleration cannot be resolved"
      + "\nvitesse cannot be resolved"
      + "\nposition cannot be resolved"
      + "\nclampToWorld cannot be resolved");
  }
  
  /**
   * The world is circular, this function clamps coordinates to stay within the frame
   */
  protected Object clampToWorld(final /* PerceivedAntBody */Object b) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field ants is undefined"
      + "\nVector2d cannot be resolved."
      + "\nposition cannot be resolved"
      + "\nx cannot be resolved"
      + "\nposition cannot be resolved"
      + "\ny cannot be resolved"
      + "\n> cannot be resolved"
      + "\n-= cannot be resolved"
      + "\n< cannot be resolved"
      + "\n+= cannot be resolved"
      + "\n> cannot be resolved"
      + "\n-= cannot be resolved"
      + "\n< cannot be resolved"
      + "\n+= cannot be resolved"
      + "\nget cannot be resolved"
      + "\nowner cannot be resolved"
      + "\nposition cannot be resolved");
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
  private void $guardEvaluator$void(final /* Start */Object occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    throw new Error("Unresolved compilation problems:"
      + "\nStart cannot be resolved to a type.");
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Action(final Action occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Action$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Die(final Die occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Die$3(occurrence));
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
  protected ConcurrentSkipListSet<UUID> getInfluences() {
    return this.influences;
  }
  
  protected void setInfluences(final ConcurrentSkipListSet<UUID> influences) {
    this.influences = influences;
  }
}
