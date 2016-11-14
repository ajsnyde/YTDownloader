//http://stackoverflow.com/questions/36067690/how-do-you-get-jsplitpane-to-keep-the-same-proportional-location-if-the-user-has/36083987#36083987
//http://stackoverflow.com/users/2670571/john
package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JSplitPane;

public class ResizableSplitPane extends JSplitPane {

  private boolean painted;

  private double defaultDividerLocation;

  private double dividerProportionalLocation;

  private int currentDividerLocation;

  private Component first;

  private Component second;

  private boolean dividerPositionCaptured = false;

  public ResizableSplitPane(int splitType, Component first, Component second, Component parent) {
    this(splitType, first, second, parent, 0.5);
  }

  public ResizableSplitPane(int splitType, Component first, Component second, Component parent, double defaultDividerLocation) {
    super(splitType, first, second);
    this.defaultDividerLocation = defaultDividerLocation;
    this.dividerProportionalLocation = defaultDividerLocation;
    this.setResizeWeight(defaultDividerLocation);
    this.first = first;
    this.second = second;
    parent.addComponentListener(new DividerLocator());
    second.addComponentListener(new DividerMovedByUserComponentAdapter());
  }

  public double getDefaultDividerLocation() {
    return defaultDividerLocation;
  }

  public void setDefaultDividerLocation(double defaultDividerLocation) {
    this.defaultDividerLocation = defaultDividerLocation;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    if (painted == false) {
      painted = true;
      this.setDividerLocation(dividerProportionalLocation);
      this.currentDividerLocation = this.getDividerLocation();
    }
  }

  private class DividerLocator extends ComponentAdapter {
    @Override
    public void componentResized(ComponentEvent e) {
      setDividerLocation(dividerProportionalLocation);
      currentDividerLocation = getDividerLocation();
    }
  }

  private class DividerMovedByUserComponentAdapter extends ComponentAdapter {
    @Override
    public void componentResized(ComponentEvent e) {
      // System.out.println("RESIZED: " + dividerPositionCaptured);
      int newDividerLocation = getDividerLocation();
      boolean dividerWasMovedByUser = newDividerLocation != currentDividerLocation;
      // System.out.println(currentDividerLocation + "\t" + newDividerLocation + "\t" + dividerProportionalLocation);
      if (dividerPositionCaptured == false || dividerWasMovedByUser == true) {
        dividerPositionCaptured = true;
        painted = false;
        if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
          dividerProportionalLocation = (double) first.getWidth() / (double) (first.getWidth() + second.getWidth());
        } else {
          dividerProportionalLocation = (double) first.getHeight() / (double) (first.getHeight() + second.getHeight());

        }
        // System.out.println(dividerProportionalLocation);
      }
    }
  }

}
