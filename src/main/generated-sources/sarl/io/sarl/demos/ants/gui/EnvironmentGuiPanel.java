package io.sarl.demos.ants.gui;

import io.sarl.demos.ants.PerceivedAntBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The GUI of the Simulation
 * 
 * @author Nicolas GAUD
 */
@SarlSpecification("0.10")
@SarlElementType(10)
@SuppressWarnings("all")
class EnvironmentGuiPanel extends Panel {
  /**
   * Double buffering management.
   */
  private Graphics myGraphics;
  
  /**
   * Double buffering management.
   */
  private Graphics myCanvas;
  
  /**
   * Double buffering management.
   */
  private Image myImage;
  
  private int width;
  
  private int height;
  
  private Map<UUID, PerceivedAntBody> boids;
  
  public void setBoids(final Map<UUID, PerceivedAntBody> boids) {
    this.boids = boids;
  }
  
  public EnvironmentGuiPanel(final int iheight, final int iwidth, final Map<UUID, PerceivedAntBody> iboids) {
    super();
    this.width = iwidth;
    this.height = iheight;
    this.boids = iboids;
  }
  
  @Override
  public void paint(final Graphics g) {
    if (((this.myCanvas != null) && (this.myGraphics != null))) {
      final Color bgColor = new Color(0.6F, 0.6F, 0.6F);
      this.myCanvas.setColor(bgColor);
      this.myCanvas.fillRect(0, 0, ((this.width * 2) - 1), ((this.height * 2) - 1));
      this.myCanvas.setColor(Color.BLACK);
      this.myCanvas.drawRect(0, 0, ((this.width * 2) - 1), ((this.height * 2) - 1));
      Collection<PerceivedAntBody> _values = this.boids.values();
      for (final PerceivedAntBody boid : _values) {
        this.paintBoid(this.myCanvas, boid);
      }
      this.myGraphics.drawImage(this.myImage, 0, 0, this);
    }
  }
  
  public void update(final Graphics g) {
    this.paint(g);
  }
  
  @Override
  public void doLayout() {
    super.doLayout();
    this.width = (this.getSize().width / 2);
    this.height = (this.getSize().height / 2);
    this.myImage = this.createImage((this.width * 2), (this.height * 2));
    this.myCanvas = this.myImage.getGraphics();
    this.myGraphics = this.getGraphics();
  }
  
  public void paintBoid(final Graphics g, final PerceivedAntBody boid) {
    throw new Error("Unresolved compilation problems:"
      + "\nCannot cast from Object to int"
      + "\nThe method getPosition() from the type PerceivedAntBody refers to the missing type Vector2d"
      + "\nCannot cast from Object to int"
      + "\nThe method getPosition() from the type PerceivedAntBody refers to the missing type Vector2d"
      + "\nThe method getVitesse() from the type PerceivedAntBody refers to the missing type Vector2d"
      + "\nx cannot be resolved"
      + "\ny cannot be resolved"
      + "\nangle cannot be resolved"
      + "\ncos cannot be resolved"
      + "\nsin cannot be resolved");
  }
  
  @Pure
  private static double getAngle(final /* Vector2d */Object v) {
    throw new Error("Unresolved compilation problems:"
      + "\nx cannot be resolved"
      + "\n* cannot be resolved"
      + "\nx cannot be resolved"
      + "\n< cannot be resolved"
      + "\ny cannot be resolved"
      + "\n>= cannot be resolved"
      + "\nx cannot be resolved"
      + "\n>= cannot be resolved"
      + "\ny cannot be resolved"
      + "\n/ cannot be resolved"
      + "\nx cannot be resolved"
      + "\natan cannot be resolved"
      + "\ny cannot be resolved"
      + "\n>= cannot be resolved"
      + "\ny cannot be resolved"
      + "\n/ cannot be resolved"
      + "\nx cannot be resolved"
      + "\natan cannot be resolved"
      + "\ny cannot be resolved"
      + "\n/ cannot be resolved"
      + "\nx cannot be resolved"
      + "\natan cannot be resolved"
      + "\n- cannot be resolved");
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
    EnvironmentGuiPanel other = (EnvironmentGuiPanel) obj;
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
  private static final long serialVersionUID = -2836111295L;
}
