package io.sarl.demos.ants;

import io.sarl.demos.ants.Population;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The representation of a boid in the environment : his physical body
 * 
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(10)
@SuppressWarnings("all")
public class PerceivedAntBody {
  @Accessors
  private /* Vector2d */Object position;
  
  @Accessors
  private UUID owner;
  
  @Accessors
  private /* Vector2d */Object vitesse;
  
  @Accessors
  private /* Vector2d */Object acceleration;
  
  @Accessors
  private Population group;
  
  public PerceivedAntBody(final Population igroup, final UUID iowner, final /* Vector2d */Object iposition, final /* Vector2d */Object ispeed) {
    throw new Error("Unresolved compilation problems:"
      + "\nVector2d cannot be resolved."
      + "\nThe field PerceivedAntBody.position refers to the missing type Vector2d"
      + "\nThe field PerceivedAntBody.vitesse refers to the missing type Vector2d"
      + "\nThe field PerceivedAntBody.acceleration refers to the missing type Vector2d");
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
    PerceivedAntBody other = (PerceivedAntBody) obj;
    if (!Objects.equals(this.owner, other.owner)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.owner);
    return result;
  }
  
  @Pure
  public Vector2d getPosition() {
    return this.position;
  }
  
  public void setPosition(final Vector2d position) {
    this.position = position;
  }
  
  @Pure
  public UUID getOwner() {
    return this.owner;
  }
  
  public void setOwner(final UUID owner) {
    this.owner = owner;
  }
  
  @Pure
  public Vector2d getVitesse() {
    return this.vitesse;
  }
  
  public void setVitesse(final Vector2d vitesse) {
    this.vitesse = vitesse;
  }
  
  @Pure
  public Vector2d getAcceleration() {
    return this.acceleration;
  }
  
  public void setAcceleration(final Vector2d acceleration) {
    this.acceleration = acceleration;
  }
  
  @Pure
  public Population getGroup() {
    return this.group;
  }
  
  public void setGroup(final Population group) {
    this.group = group;
  }
}
