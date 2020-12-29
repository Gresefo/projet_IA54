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
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class GuiRepaint extends Event {
  public final ArrayList<Integer> tour;
  
  public final double length;
  
  public GuiRepaint(final ArrayList<Integer> tour, final double length) {
    this.tour = tour;
    this.length = length;
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
    GuiRepaint other = (GuiRepaint) obj;
    if (Double.doubleToLongBits(other.length) != Double.doubleToLongBits(this.length))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.length);
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
    builder.add("length", this.length);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -3264611693L;
}
