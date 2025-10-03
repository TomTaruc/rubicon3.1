package com.gabriel.draw.controller;

import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ScaleShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.EditMode;
import com.gabriel.drawfx.ScalingMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.SearchService;
import com.gabriel.drawfx.service.ScalerService;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;
import java.util.List;

public class DrawingController implements MouseListener, MouseMotionListener {
    private Point startPoint;
    private Point endPoint;
    private final DrawingView drawingView;
    private Shape currentShape;
    private Shape selectedShape;
    private int selectedAnchor = -1;
    private Point dragOffset;
    private Point shapeStartLocation;
    private Point shapeStartEnd;
    private final AppService appService;
    private final ActionController actionController;
    private EditMode currentEditMode;

    public DrawingController(AppService appService, DrawingView drawingView, ActionController actionController) {
        this.appService = appService;
        this.drawingView = drawingView;
        this.actionController = actionController;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.isShiftDown()) {
            createShapeAtPoint(e.getPoint());
        }
    }
    
    private void createShapeAtPoint(Point point) {
        // Prevent shape creation when in Move or Scale mode
        if (appService.getEditMode() != EditMode.NONE) {
            return;
        }

        Point endPoint = new Point(point.x + 50, point.y + 50);

        switch (appService.getShapeMode()) {
            case Line:
                currentShape = new Line(point, endPoint);
                break;
            case Rectangle:
                currentShape = new Rectangle(point, endPoint);
                break;
            case Ellipse:
                currentShape = new Ellipse(point, endPoint);
                break;
            default:
                return;
        }

        currentShape.setColor(appService.getColor());
        appService.create(currentShape);
        actionController.updateUIState();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickPoint = e.getPoint();
        Drawing drawing = (Drawing) appService.getModel();
        List<Shape> shapes = drawing.getShapes();
        SearchService searchService = appService.getSearchService();
        ScalerService scalerService = appService.getScalerService();
        
        Shape clickedShape = searchService.findShapeAtPoint(shapes, clickPoint);

        if (clickedShape != null) {
            appService.setSelectedShape(clickedShape);

            selectedShape = clickedShape;
            shapeStartLocation = new Point(clickedShape.getLocation());
            shapeStartEnd = new Point(clickedShape.getEnd());

            EditMode editMode = appService.getEditMode();

            // In MOVE mode, don't check for anchors - always prepare for moving
            if (editMode == EditMode.MOVE) {
                Point shapeLocation = clickedShape.getLocation();
                dragOffset = new Point(clickPoint.x - shapeLocation.x, clickPoint.y - shapeLocation.y);
                selectedAnchor = -1;
            } else if (editMode == EditMode.SCALE) {
                // In SCALE mode, find the anchor for scaling
                int anchorIndex = searchService.findScaleAnchor(
                    clickedShape,
                    clickPoint,
                    scalerService.getAnchorSearchRadius()
                );
                selectedAnchor = anchorIndex;
            } else {
                selectedAnchor = -1;
            }

            return;
        }
        
        appService.setSelectedShape(null);

        // Prevent shape creation when in Move or Scale mode
        if (appService.getEditMode() != EditMode.NONE) {
            return;
        }

        if (appService.getDrawMode() == DrawMode.Idle && !e.isShiftDown()) {
            startPoint = e.getPoint();

            switch (appService.getShapeMode()) {
                case Line:
                    currentShape = new Line(startPoint, startPoint);
                    break;
                case Rectangle:
                    currentShape = new Rectangle(startPoint, startPoint);
                    break;
                case Ellipse:
                    currentShape = new Ellipse(startPoint, startPoint);
                    break;
                default:
                    return;
            }

            currentShape.setColor(appService.getColor());
            drawingView.setPreviewShape(currentShape);
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedShape != null && currentEditMode != null) {
            // Record the move or scale command for undo/redo
            if (currentEditMode == EditMode.MOVE) {
                Point finalLocation = new Point(selectedShape.getLocation());
                if (!finalLocation.equals(shapeStartLocation)) {
                    MoveShapeCommand moveCommand = new MoveShapeCommand(
                        appService, selectedShape, shapeStartLocation, finalLocation
                    );
                    // Use RecordCommand since the move was already applied during drag
                    CommandService.RecordCommand(moveCommand);
                    actionController.updateUIState();
                }
            } else if (currentEditMode == EditMode.SCALE) {
                Point finalEnd = new Point(selectedShape.getEnd());
                if (!finalEnd.equals(shapeStartEnd)) {
                    ScaleShapeCommand scaleCommand = new ScaleShapeCommand(
                        appService, selectedShape, shapeStartEnd, finalEnd
                    );
                    // Use RecordCommand since the scale was already applied during drag
                    CommandService.RecordCommand(scaleCommand);
                    actionController.updateUIState();
                }
            }

            selectedShape = null;
            selectedAnchor = -1;
            dragOffset = null;
            shapeStartLocation = null;
            shapeStartEnd = null;
            currentEditMode = null;
            appService.repaint();
            return;
        }

        selectedShape = null;
        selectedAnchor = -1;
        dragOffset = null;
        shapeStartLocation = null;
        shapeStartEnd = null;
        currentEditMode = null;

        if (appService.getDrawMode() == DrawMode.MousePressed && !e.isShiftDown()) {
            endPoint = e.getPoint();

            drawingView.clearPreviewShape();

            appService.scale(currentShape, endPoint);

            appService.create(currentShape);
            appService.setDrawMode(DrawMode.Idle);

            actionController.updateUIState();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateCursor();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawingView.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point currentPoint = e.getPoint();

        if (selectedShape != null) {
            EditMode editMode = appService.getEditMode();

            if (editMode == EditMode.MOVE) {
                // Always move in MOVE mode regardless of anchor
                if (dragOffset != null) {
                    currentEditMode = EditMode.MOVE;
                    Point newLocation = new Point(currentPoint.x - dragOffset.x, currentPoint.y - dragOffset.y);
                    appService.move(selectedShape, newLocation);
                    appService.repaint();
                }
                return;
            } else if (editMode == EditMode.SCALE) {
                // Only scale if we have a valid anchor
                if (selectedAnchor >= 0) {
                    currentEditMode = EditMode.SCALE;
                    ScalerService scalerService = appService.getScalerService();
                    scalerService.scaleByAnchor(selectedShape, selectedAnchor, currentPoint, ScalingMode.DIRECTIONAL);
                    appService.repaint();
                }
                return;
            }
        }

        if (appService.getDrawMode() == DrawMode.MousePressed && !e.isShiftDown()) {
            endPoint = e.getPoint();

            appService.scale(currentShape, endPoint);
            drawingView.setPreviewShape(currentShape);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateCursor();
    }
    
    private void updateCursor() {
        Cursor cursor;
        switch (appService.getShapeMode()) {
            case Line:
                cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                break;
            case Rectangle:
            case Ellipse:
                cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                break;
            default:
                cursor = Cursor.getDefaultCursor();
        }
        drawingView.setCursor(cursor);
    }
}
