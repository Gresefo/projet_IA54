package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Event informing the environment from the real startup of the simulation with the corresponding boids inside
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(15)
@SuppressWarnings("all")
public class StartEnvironment extends Event {
  @SyntheticMember
  public StartEnvironment() {
    super();
  }
  
  @SyntheticMember
  public StartEnvironment(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 588368462L;
}
