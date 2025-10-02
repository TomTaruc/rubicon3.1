package com.gabriel.draw.service;

import com.gabriel.draw.model.Line;
import com.gabriel.drawfx.service.RendererService;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;


public class LineRendererService implements RendererService {

    private static final int HANDLE_SIZE = 6;

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Line line = (Line) shape;
        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }
        g.drawLine(line.getLocation().x, line.getLocation().y, line.getEnd().x, line.getEnd().y);
        
        if (shape.isSelected() && !xor) {
            drawHandles(g, shape);
        }
    }
    
    private void drawHandles(Graphics g, Shape shape) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();
        
        g.setColor(Color.BLUE);
        drawHandle(g, start.x, start.y);
        drawHandle(g, end.x, end.y);
    }
    
    private void drawHandle(Graphics g, int x, int y) {
        g.fillRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.WHITE);
        g.drawRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
    }
}
