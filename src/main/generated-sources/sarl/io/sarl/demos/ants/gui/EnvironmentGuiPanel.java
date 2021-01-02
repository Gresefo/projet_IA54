package io.sarl.demos.ants.gui;

import io.sarl.demos.ants.Settings;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The GUI of the Simulation
 */
@SarlSpecification("0.11")
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
  
  /**
   * The list of town positions
   */
  private ArrayList<double[]> posList;
  
  /**
   * The tour between towns to print
   */
  private ArrayList<Integer> tour;
  
  /**
   * The tour length
   */
  private double tourLength;
  
  private int iteration;
  
  public void setTour(final ArrayList<Integer> tour) {
    this.tour = tour;
  }
  
  public void setTourLength(final double length) {
    this.tourLength = length;
  }
  
  public void setIteration(final int iteration) {
    this.iteration = iteration;
  }
  
  /**
   * Constructor
   * @param iheight : int
   */
  public EnvironmentGuiPanel(final ArrayList<double[]> _posList) {
    super();
    this.posList = _posList;
  }
  
  @Override
  public void paint(final Graphics g) {
    if (((this.myCanvas != null) && (this.myGraphics != null))) {
      final Color bgColor = new Color(0.6F, 0.6F, 0.6F);
      this.myCanvas.setColor(bgColor);
      this.myCanvas.fillRect(0, 0, ((Settings.EnvtWidth * 2) - 1), ((Settings.EnvtHeight * 2) - 1));
      this.myCanvas.setColor(Color.BLACK);
      this.myCanvas.drawRect(0, 0, ((Settings.EnvtWidth * 2) - 1), ((Settings.EnvtHeight * 2) - 1));
      List<Integer> maxCoord = this.getMaxCoord(this.posList);
      if ((this.tour != null)) {
        ArrayList<int[]> linePixelList = this.getRectPixel(this.posList, this.tour, maxCoord);
        for (final int[] line : linePixelList) {
          this.myCanvas.drawLine(line[0], line[1], line[2], line[3]);
        }
        this.myCanvas.drawString(("Tour length : " + Integer.valueOf(((int) this.tourLength))), (Settings.EnvtWidth - 300), (Settings.EnvtHeight - 60));
        this.myCanvas.drawString(((("Number of remaining iterations (over " + Integer.valueOf(Settings.iteration)) + ") : ") + Integer.valueOf((Settings.iteration - this.iteration))), 
          0, (Settings.EnvtHeight - 60));
        Font _font = new Font("Arial", Font.PLAIN, 24);
        this.myCanvas.setFont(_font);
      }
      for (final double[] pos : this.posList) {
        this.paintTown(((Graphics2D) this.myCanvas), pos, maxCoord);
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
    int _width = this.getWidth();
    int _height = this.getHeight();
    this.myImage = this.createImage((_width * 2), (_height * 2));
    this.myCanvas = this.myImage.getGraphics();
    this.myGraphics = this.getGraphics();
  }
  
  /**
   * Paint on the graphic every town in the position list
   * @param g : Graphics2D, the graphic we print in
   * @param pos : double[], the list containing the town positions
   * @param maxCoord : int, the maximum coordinate to do the resizing
   */
  public void paintTown(final Graphics2D g, final double[] pos, final List<Integer> maxCoord) {
    double[] coord = new double[2];
    coord[0] = pos[1];
    coord[1] = pos[2];
    int[] pixel = this.pixelResizing(coord, maxCoord);
    int _get = pixel[0];
    int _get_1 = pixel[1];
    Shape circle = new Ellipse2D.Double((_get - 8), (_get_1 - 8), 16, 16);
    g.setColor(Color.RED);
    g.fill(circle);
    g.draw(circle);
  }
  
  /**
   * Return the maximum coordinate you can find in the position list
   * @param posList : ArrayList<double[]> the list of position of every town
   * @return List<Integer> : - the min X coordinate
   * 						   - the min Y coordinate
   * 						   - the max(maxX - minX, maxY - minY)
   */
  @Pure
  private List<Integer> getMaxCoord(final ArrayList<double[]> posList) {
    List<Integer> result = new ArrayList<Integer>();
    double maxX = 0;
    double minX = 1000000000;
    double maxY = 0;
    double minY = 1000000000;
    for (final double[] pos : posList) {
      {
        double _get = pos[1];
        if ((_get > maxX)) {
          maxX = pos[1];
        }
        double _get_1 = pos[2];
        if ((_get_1 > maxY)) {
          maxY = pos[2];
        }
        double _get_2 = pos[1];
        if ((_get_2 < minX)) {
          minX = pos[1];
        }
        double _get_3 = pos[2];
        if ((_get_3 < minY)) {
          minY = pos[2];
        }
      }
    }
    result.add(Integer.valueOf(((int) minX)));
    result.add(Integer.valueOf(((int) minY)));
    if (((maxX - minX) > (maxY - minY))) {
      result.add(Integer.valueOf(((int) (maxX - minX))));
    } else {
      result.add(Integer.valueOf(((int) (maxY - minY))));
    }
    return result;
  }
  
  /**
   * Return the pixel value based on the size of the screen and the maximum coordinate
   * @param coord : double[], the coordinate to translate in pixel, X and Y
   * @param maxCoord : int, the biggest coordinate in the list
   * @return int[2], the pixels to print
   */
  private int[] pixelResizing(final double[] coord, final List<Integer> maxCoord) {
    int[] result = new int[2];
    double _get = coord[0];
    Integer _get_1 = maxCoord.get(0);
    Integer _get_2 = maxCoord.get(2);
    result[0] = ((int) ((((_get - ((_get_1) == null ? 0 : (_get_1).intValue())) * (Settings.EnvtWidth - 150)) / ((_get_2) == null ? 0 : (_get_2).intValue())) + 25));
    double _get_3 = coord[1];
    Integer _get_4 = maxCoord.get(1);
    Integer _get_5 = maxCoord.get(2);
    result[1] = ((int) ((((_get_3 - ((_get_4) == null ? 0 : (_get_4).intValue())) * (Settings.EnvtHeight - 150)) / ((_get_5) == null ? 0 : (_get_5).intValue())) + 25));
    return result;
  }
  
  /**
   * Find a town coordinates from its ID in the position list
   * @param posList : ArrayList<double[]>, the position list
   * @param id : int, the ID of the town to find
   * @return double[2], the town coordinates
   */
  @SuppressWarnings("discouraged_reference")
  @Pure
  private double[] getCoordFromTownId(final ArrayList<double[]> posList, final int id) {
    double[] result = new double[2];
    int i = 0;
    while (((i < (posList.size() - 1)) && (((int) posList.get(i)[0]) != id))) {
      i++;
    }
    double _get = posList.get(i)[0];
    if ((((int) _get) == id)) {
      result[0] = posList.get(i)[1];
      result[1] = posList.get(i)[2];
    } else {
      System.err.println(("Error, coordinate not found with this ID : " + Integer.valueOf(id)));
    }
    return result;
  }
  
  /**
   * Return the coordinates of the pixel of the rectangle to draw between each town
   * @param posList : ArrayList<double[]> : the position list of every town
   * @param travelorder : ArrayList<Integer> : the path between the town
   * @param maxCoord : int, the max coordinate to do the resizing
   * @return ArrayList<int[]> : the pixel list of each corner of the rectangle to drawa
   */
  @Pure
  private ArrayList<int[]> getRectPixel(final ArrayList<double[]> posList, final ArrayList<Integer> travelOrder, final List<Integer> maxCoord) {
    ArrayList<int[]> rectPixelList = new ArrayList<int[]>();
    int size = travelOrder.size();
    for (int i = 0; (i < (size - 1)); i++) {
      {
        int[] intArray = new int[4];
        double[] coord1 = this.getCoordFromTownId(posList, ((travelOrder.get(i)) == null ? 0 : (travelOrder.get(i)).intValue()));
        double[] coord2 = this.getCoordFromTownId(posList, ((travelOrder.get((i + 1))) == null ? 0 : (travelOrder.get((i + 1))).intValue()));
        int[] pixel1 = this.pixelResizing(coord1, maxCoord);
        int[] pixel2 = this.pixelResizing(coord2, maxCoord);
        intArray[0] = pixel1[0];
        intArray[1] = pixel1[1];
        intArray[2] = pixel2[0];
        intArray[3] = pixel2[1];
        rectPixelList.add(intArray);
      }
    }
    return rectPixelList;
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
    if (Double.doubleToLongBits(other.tourLength) != Double.doubleToLongBits(this.tourLength))
      return false;
    if (other.iteration != this.iteration)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.tourLength);
    result = prime * result + Integer.hashCode(this.iteration);
    return result;
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -14133619198L;
}
