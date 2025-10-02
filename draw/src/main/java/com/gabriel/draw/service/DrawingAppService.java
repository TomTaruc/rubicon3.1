package com.gabriel.draw.service;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.EditMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.MoverService;
import com.gabriel.drawfx.service.ScalerService;
import com.gabriel.drawfx.service.SearchService;

import javax.swing.*;
import java.awt.*;

public class DrawingAppService implements AppService {

    private final Drawing drawing;
    private final MoverService moverService;
    private final ScalerService scalerService;
    private final SearchService searchService;
    private JPanel drawingView;

    public DrawingAppService() {
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        searchService = new SearchService();
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Ellipse);
        drawing.setEditMode(EditMode.NONE);
        drawing.setColor(Color.BLACK);
    }

    @Override
    public void undo() {
        // This is handled by the command service wrapper
    }

    @Override
    public void redo() {
        // This is handled by the command service wrapper
    }

    @Override
    public ShapeMode getShapeMode() {
        return drawing.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        drawing.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return drawing.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawing.setDrawMode(drawMode);
    }

    @Override
    public EditMode getEditMode() {
        return drawing.getEditMode();
    }

    @Override
    public void setEditMode(EditMode editMode) {
        this.drawing.setEditMode(editMode);
    }

    @Override
    public Color getColor() {
        return drawing.getColor();
    }

    @Override
    public void setColor(Color color) {
        if (color != null) {
            drawing.setColor(color);
        }
    }

    @Override
    public Color getFill() {
        return drawing.getFill();
    }

    @Override
    public void setFill(Color color) {
        drawing.setFill(color);
    }

    @Override
    public void move(Shape shape, Point newLoc) {
        moverService.move(shape, newLoc);
    }

    @Override
    public void scale(Shape shape, Point newEnd) {
        if (shape != null && newEnd != null) {
            shape.setEnd(newEnd);
        }
    }

    @Override
    public void create(Shape shape) {
        if (shape != null) {
            shape.setId(this.drawing.getShapes().size());
            this.drawing.getShapes().add(shape);
            repaint();
        }
    }

    @Override
    public void delete(Shape shape) {
        if (shape != null) {
            drawing.getShapes().remove(shape);
            repaint();
        }
    }

    @Override
    public void close() {
        int result = JOptionPane.showConfirmDialog(
            drawingView,
            "Are you sure you want to exit?",
            "Exit Application",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public Object getModel() {
        return drawing;
    }

    @Override
    public JPanel getView() {
        return drawingView;
    }

    @Override
    public void setView(JPanel panel) {
        this.drawingView = panel;
    }

    @Override
    public void repaint() {
        if (drawingView != null) {
            SwingUtilities.invokeLater(() -> drawingView.repaint());
        }
    }

    @Override
    public SearchService getSearchService() {
        return searchService;
    }

    @Override
    public ScalerService getScalerService() {
        return scalerService;
    }

    @Override
    public void setSelectedShape(Shape shape) {
        for (Shape s : drawing.getShapes()) {
            s.setSelected(false);
        }
        if (shape != null) {
            shape.setSelected(true);
        }
        repaint();
    }

    @Override
    public Shape getSelectedShape() {
        for (Shape s : drawing.getShapes()) {
            if (s.isSelected()) {
                return s;
            }
        }
        return null;
    }

    @Override
    public void clearAll() {
        drawing.getShapes().clear();
        repaint();
    }
}