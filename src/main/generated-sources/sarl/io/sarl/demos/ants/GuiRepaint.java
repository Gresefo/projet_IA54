package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event specifying when the GUI must be refresh according to the new environmental state embodied by the specified map
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(15)
@SuppressWarnings("all")
public class GuiRepaint extends Event {
  public final ArrayList<Integer> tour;
  
  public GuiRepaint(final ArrayList<Integer> tour) {
    this.tour = tour;
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
   * Returns a String representation of the GuiRepaint event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("tour", this.tour);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 581411869L;
}
