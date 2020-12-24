package io.sarl.demos.ants.gui;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
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
  
  private ArrayList<double[]> posList;
  
  private /* Map<UUID, PerceivedAntBody> */Object boids;
  
  public void setBoids(final /* Map<UUID, PerceivedAntBody> */Object boids) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field EnvironmentGuiPanel.boids refers to the missing type PerceivedAntBody");
  }
  
  public EnvironmentGuiPanel(final int iheight, final int iwidth, final ArrayList<double[]> _posList) {
    super();
    this.width = iwidth;
    this.height = iheight;
    this.posList = _posList;
  }
  
  @Override
  public void paint(final Graphics g) {
    if (((this.myCanvas != null) && (this.myGraphics != null))) {
      final Color bgColor = new Color(0.6F, 0.6F, 0.6F);
      this.myCanvas.setColor(bgColor);
      this.myCanvas.fillRect(0, 0, ((this.width * 2) - 1), ((this.height * 2) - 1));
      this.myCanvas.setColor(Color.BLACK);
      this.myCanvas.drawRect(0, 0, ((this.width * 2) - 1), ((this.height * 2) - 1));
      for (final double[] pos : this.posList) {
        this.paintBoid(((Graphics2D) this.myCanvas), pos);
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
  
  public void paintBoid(final Graphics2D g, final double[] pos) {
    double _get = pos[1];
    int posX = ((int) _get);
    double _get_1 = pos[2];
    int posY = ((int) _get_1);
    Shape circle = new Ellipse2D.Double(posX, posY, 15, 15);
    g.setColor(Color.RED);
    g.fill(circle);
    g.draw(circle);
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
  private static final long serialVersionUID = -3710425576L;
}
