package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event sent to reset ants
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class StartAnt extends Event {
  public final double[][] pheromones;
  
  public StartAnt(final double[][] pheromones) {
    this.pheromones = pheromones;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the StartAnt event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("pheromones", this.pheromones);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1522123733L;
}
