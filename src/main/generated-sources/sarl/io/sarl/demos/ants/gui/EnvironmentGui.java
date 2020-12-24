package io.sarl.demos.ants.gui;

import io.sarl.core.OpenEventSpace;
import io.sarl.demos.ants.PerceivedAntBody;
import io.sarl.demos.ants.gui.Closer;
import io.sarl.demos.ants.gui.EnvironmentGuiPanel;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Graphical user interface for ants.
 */
@SarlSpecification("0.10")
@SarlElementType(10)
@SuppressWarnings("all")
public class EnvironmentGui extends Frame {
  private Closer handler;
  
  private EnvironmentGuiPanel panel;
  
  public EnvironmentGui(final OpenEventSpace comspace, final int iheight, final int iwidth, final Map<UUID, PerceivedAntBody> iboids) {
    super();
    Closer _closer = new Closer(this, comspace);
    this.handler = _closer;
    EnvironmentGuiPanel _environmentGuiPanel = new EnvironmentGuiPanel(iheight, iwidth, iboids);
    this.panel = _environmentGuiPanel;
    this.setTitle("Boids Simulation");
    this.setSize(iwidth, iheight);
    this.addWindowListener(this.handler);
    this.add("Center", this.panel);
    this.setVisible(true);
  }
  
  public void setBoids(final Map<UUID, PerceivedAntBody> boids) {
    this.panel.setBoids(boids);
  }
  
  @Override
  public void paint(final Graphics g) {
    super.paint(g);
    this.panel.paint(g);
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
  
  @SyntheticMember
  private static final long serialVersionUID = -518052431L;
}
