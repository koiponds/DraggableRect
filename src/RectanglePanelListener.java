import java.awt.Color;
import java.awt.event.*;

public class RectanglePanelListener implements MouseListener, MouseMotionListener{
	
	private RectanglePanel rectPanel;
	
	//***Reference to the Rectangle we are dragging.  This is like Method 2 
	// that we did in class.  Remember, if we save this reference, we can change
	// the state of the Rectangle being dragged, without having to delete and redraw
	// Rectangles constantly
	private Rectangle currentlyDraggingRectangle = null;
	private Rectangle[] rectangles = new Rectangle[200000];
	private int numOfRectangles;

	private int offsetX, offsetY;  
	private boolean dragging;
	
	public RectanglePanelListener(RectanglePanel panel) {
		rectPanel = panel;  // save reference to the panel we are listening on
		rectPanel.addMouseMotionListener(this);
		rectPanel.addMouseListener(this); // register listener with panel
	}

	// mouseClicked (press/release in same spot) means I want to create a new rectangle 
	// mousePressed (press and hold) means I'm going to possibly start dragging a rectangle
	@Override 
	public void mouseClicked(MouseEvent ev) {
		int width = 30;						
		int height = 20;
		Color color;
		if (ev.isMetaDown()) {
			color = Color.BLUE;
		}
		else {
			color = Color.RED;
		}
		// modify state to include a new rectangle
		Rectangle tempRect = new Rectangle(ev.getX(), ev.getY(), width, height, color);
		rectangles[numOfRectangles++] = tempRect;
		rectPanel.addRectangle(tempRect);
		// repaint so user can see this new state
		rectPanel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent ev) {  }

	@Override
	public void mouseExited(MouseEvent ev) {

	}

	@Override
	public void mousePressed(MouseEvent ev) {
		for (int i = 0; i < numOfRectangles; i++) {
			if (rectangles[i].containsPoint(ev.getX(), ev.getY())) {
				currentlyDraggingRectangle = rectangles[i];
			}
		}
		if (currentlyDraggingRectangle != null) {
			if (currentlyDraggingRectangle.containsPoint(ev.getX(), ev.getY())) {
				offsetX = currentlyDraggingRectangle.getX() - ev.getX();
				offsetY = currentlyDraggingRectangle.getY() - ev.getY();
				dragging = true;
			}
		}
	}

	public void mouseReleased(MouseEvent ev) { 
		dragging = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragging) {
			currentlyDraggingRectangle.setX(e.getX() + offsetX);
			currentlyDraggingRectangle.setY(e.getY() + offsetY);
			rectPanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
