package io.sarl.demos.ants;

import io.sarl.demos.ants.PerceivedAntBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event from the environment to the boids with the global state of the environment, could be optimized to reduce it to the true local perception of each boids
 * @author Nicolas Gaud
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class Perception extends Event {
  public final ConcurrentHashMap<UUID, PerceivedAntBody> perceivedAgentBody;
  
  public Perception(final ConcurrentHashMap<UUID, PerceivedAntBody> bodies) {
    ConcurrentHashMap<UUID, PerceivedAntBody> _concurrentHashMap = new ConcurrentHashMap<UUID, PerceivedAntBody>(bodies);
    this.perceivedAgentBody = _concurrentHashMap;
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
   * Returns a String representation of the Perception event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("perceivedAgentBody", this.perceivedAgentBody);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2599714945L;
}
