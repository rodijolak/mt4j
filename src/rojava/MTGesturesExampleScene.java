/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojava;

import org.mt4j.AbstractMTApplication;
import org.mt4j.MTApplication;
import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.bounds.IBoundingShape;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.gestureAction.DefaultButtonClickAction;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.DefaultRotateAction;
import org.mt4j.input.gestureAction.DefaultScaleAction;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.scaleProcessor.ScaleProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeEvent;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.Direction;
import org.mt4j.input.inputProcessors.componentProcessors.unistrokeProcessor.UnistrokeUtils.UnistrokeGesture;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;



/**
 *
 * @author jolak
 */
public class MTGesturesExampleScene extends AbstractScene {
	private AbstractMTApplication app;
        
	public MTGesturesExampleScene(final AbstractMTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.app = mtApplication;
		this.setClearColor(new MTColor(126, 130, 168, 255));
		this.registerGlobalInputProcessor(new CursorTracer(app, this));

		float verticalPad = 53;
		float horizontalPad = 500;
		
		MTColor white = new MTColor(255,255,255);
		final MTColor textAreaColor = new MTColor(126, 130, 168, 255);
		
		IFont font = FontManager.getInstance().createFont(app, "arial.ttf", 35, white);
		//Add uni-stroke gesture example
		MTTextArea strokeText = new MTTextArea(mtApplication, font);
		strokeText.setFillColor(textAreaColor);
		strokeText.setStrokeColor(textAreaColor);
		strokeText.setPickable(false);
		//strokeText.setText("Draw a stroke gesture here");
		strokeText.setAnchor(PositionAnchor.UPPER_LEFT);
		
		MTRectangle strokeGestureRect = new MTRectangle(getMTApplication(),strokeText.getWidthXY(TransformSpace.LOCAL) + 768,950);
		strokeGestureRect.setFillColor(textAreaColor);
		strokeGestureRect.setStrokeColor(textAreaColor);
		strokeGestureRect.addChild(strokeText);
		strokeGestureRect.setAnchor(PositionAnchor.UPPER_LEFT);
		strokeText.setPositionRelativeToParent(strokeGestureRect.getPosition(TransformSpace.LOCAL));
		
		final MTTextArea recognizedGestureText = new MTTextArea(mtApplication, font);
		recognizedGestureText.setFillColor(textAreaColor);
		recognizedGestureText.setStrokeColor(textAreaColor);
		//recognizedGestureText.setText("Recognized: NO_GESTURE");
		recognizedGestureText.setAnchor(PositionAnchor.UPPER_LEFT);
		recognizedGestureText.setPickable(false);
		strokeGestureRect.setAnchor(PositionAnchor.UPPER_LEFT);
		strokeGestureRect.addChild(recognizedGestureText);
		recognizedGestureText.setPositionRelativeToParent(strokeGestureRect.getPosition(TransformSpace.LOCAL));
		
               // IBoundingShape ibs= getCanvas().getBounds();
               // System.out.println("here"  + ibs.getHeightXY(TransformSpace.LOCAL) + ibs.getWidthXY(TransformSpace.LOCAL));
		getCanvas().addChild(strokeGestureRect);
		strokeGestureRect.setAnchor(PositionAnchor.CENTER);
		strokeGestureRect.setPositionGlobal(new Vector3D(strokeGestureRect.getWidthXY(TransformSpace.GLOBAL)/2f ,9*verticalPad,0));
		this.clearAllGestures(strokeGestureRect);
		
		UnistrokeProcessor up = new UnistrokeProcessor(getMTApplication());
		up.addTemplate(UnistrokeGesture.CIRCLE, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.CIRCLE, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.RECTANGLE, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.RECTANGLE, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.CHECK, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.TRIANGLE, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.TRIANGLE, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.PIGTAIL, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.PIGTAIL, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.ARROW, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.ARROW, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.STAR, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.STAR, Direction.COUNTERCLOCKWISE);
		up.addTemplate(UnistrokeGesture.V, Direction.CLOCKWISE);
		up.addTemplate(UnistrokeGesture.QUESTION, Direction.CLOCKWISE);
		
		strokeGestureRect.registerInputProcessor(up);
		strokeGestureRect.addGestureListener(UnistrokeProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				UnistrokeEvent ue = (UnistrokeEvent)ge;
				switch (ue.getId()) {
				case UnistrokeEvent.GESTURE_STARTED:
					getCanvas().addChild(ue.getVisualization());
					break;
				case UnistrokeEvent.GESTURE_UPDATED:
					break;
				case UnistrokeEvent.GESTURE_ENDED:
					UnistrokeGesture g = ue.getGesture();
					System.out.println("Recognized gesture: " + g);
					recognizedGestureText.setText("Recognized: " + g);
					if(g.equals(g.RECTANGLE)){
						IFont fontA = FontManager.getInstance().createFont(app, "arial.ttf", 35, new MTColor(255,255,255));
						MTTextArea dragRotScaleA = new MTTextArea(mtApplication, fontA);
                                                MTColor mtcolor = MTColor.randomColor();
						dragRotScaleA.setFillColor(mtcolor);
						dragRotScaleA.setStrokeColor(mtcolor);
						dragRotScaleA.setText("Class");
						clearAllGestures(dragRotScaleA);
						dragRotScaleA.registerInputProcessor(new ScaleProcessor(app));
						dragRotScaleA.addGestureListener(ScaleProcessor.class, new DefaultScaleAction());
						dragRotScaleA.registerInputProcessor(new RotateProcessor(app));
						dragRotScaleA.addGestureListener(RotateProcessor.class, new DefaultRotateAction());
						dragRotScaleA.registerInputProcessor(new DragProcessor(app));
						dragRotScaleA.addGestureListener(DragProcessor.class, new DefaultDragAction());
                                                dragRotScaleA.registerInputProcessor(new TapAndHoldProcessor(app, 1200));  
						dragRotScaleA.addGestureListener(TapAndHoldProcessor.class,new TapAndHoldVisualizer(app, dragRotScaleA));
                                                dragRotScaleA.addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
                                                    @Override
                                                    public boolean processGestureEvent(MTGestureEvent ge) {
                                                        TapAndHoldEvent te = (TapAndHoldEvent)ge;
                                                        System.out.println("gesture: " + te.getId() + "  " +te.getElapsedTime());
                                                        if(te.getId() == TapAndHoldEvent.GESTURE_ENDED && te.getElapsedTime()>=1200){
                                                            MTRectangle r = (MTRectangle)ge.getTarget();
                                                            //r.setFillColor(MTColor.randomColor());
                                                            r.removeFromParent();
                                                        }
                                                        return false;
                                                    } 
                                                    
                                                });                                                
						getCanvas().addChild(dragRotScaleA);
						dragRotScaleA.setAnchor(PositionAnchor.UPPER_LEFT);
						//dragRotScaleA.setPositionGlobal(new Vector3D(0,6*53,0));
						System.out.println("Class Diagram Added: " + dragRotScaleA.getID());
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}
	

	private void clearAllGestures(MTComponent comp){
		comp.unregisterAllInputProcessors();
		comp.removeAllGestureEventListeners();
	}
	
	public void onEnter() {}
	
	public void onLeave() {}

}


