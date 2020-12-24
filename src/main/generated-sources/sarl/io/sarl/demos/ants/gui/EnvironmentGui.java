package io.sarl.demos.ants.gui;

import io.sarl.core.OpenEventSpace;
import io.sarl.demos.ants.gui.Closer;
import io.sarl.demos.ants.gui.EnvironmentGuiPanel;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Graphical user interface for ants.
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class EnvironmentGui extends Frame {
  private Closer handler;
  
  private EnvironmentGuiPanel panel;
  
  public EnvironmentGui(final OpenEventSpace comspace, final int iheight, final int iwidth, final String fileName, final ArrayList<double[]> posList) {
    super();
    Closer _closer = new Closer(this, comspace);
    this.handler = _closer;
    EnvironmentGuiPanel _environmentGuiPanel = new EnvironmentGuiPanel(iheight, iwidth, posList);
    this.panel = _environmentGuiPanel;
    this.setTitle(("TSP Simulation : " + fileName));
    this.setSize(iwidth, iheight);
    this.addWindowListener(this.handler);
    this.add("Center", this.panel);
    this.setVisible(true);
  }
  
  public void setBoids(final /* Map<UUID, PerceivedAntBody> */Object boids) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method setBoids(Map<UUID, PerceivedAntBody>) from the type EnvironmentGuiPanel refers to the missing type PerceivedAntBody");
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
  private static final long serialVersionUID = 1419251547L;
}
