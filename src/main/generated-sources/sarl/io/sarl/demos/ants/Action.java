package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event from a boid to the environment containing his corresponding influence for the current simulation step.
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(15)
@SuppressWarnings("all")
public class Action extends Event {
  public /* Vector2d */Object influence;
  
  @SyntheticMember
  public Action() {
    super();
  }
  
  @SyntheticMember
  public Action(final Address source) {
    super(source);
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
   * Returns a String representation of the Action event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("influence", this.influence);
  }
}
