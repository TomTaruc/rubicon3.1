package com.gabriel.draw.service;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.MoverService;

import javax.swing.*;
import java.awt.*;

public class DrawingAppService implements AppService {

    private final Drawing drawing;
    private final MoverService moverService;
    private JPanel drawingView;
    
    public DrawingAppService() {
        drawing = new Drawing();
        moverService = new MoverService();
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Ellipse);
        drawing.setColor(Color.BLACK); // Default to black
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
    
    public void clearAll() {
        drawing.getShapes().clear();
        repaint();
    }
}