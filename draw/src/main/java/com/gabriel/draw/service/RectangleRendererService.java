package com.gabriel.draw.service;

import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class RectangleRendererService implements RendererService {

    private static final int HANDLE_SIZE = 6;

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Rectangle line = (Rectangle) shape;
        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }
        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getEnd().x-shape.getLocation().x;
        int height = shape.getEnd().y-shape.getLocation().y;
        if(width < 0) {
            x = shape.getEnd().x;
            width = -width;
        }
        if(height < 0) {
            y = shape.getEnd().y ;
            height = -height;
        }
        g.drawRect(x, y, width, height);
        
        if (shape.isSelected() && !xor) {
            drawHandles(g, shape);
        }
    }
    
    private void drawHandles(Graphics g, Shape shape) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();
        
        int x1 = Math.min(start.x, end.x);
        int y1 = Math.min(start.y, end.y);
        int x2 = Math.max(start.x, end.x);
        int y2 = Math.max(start.y, end.y);
        
        int midX = (x1 + x2) / 2;
        int midY = (y1 + y2) / 2;
        
        g.setColor(Color.BLUE);
        
        drawHandle(g, x1, y1);
        drawHandle(g, midX, y1);
        drawHandle(g, x2, y1);
        drawHandle(g, x2, midY);
        drawHandle(g, x2, y2);
        drawHandle(g, midX, y2);
        drawHandle(g, x1, y2);
        drawHandle(g, x1, midY);
    }
    
    private void drawHandle(Graphics g, int x, int y) {
        g.fillRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.WHITE);
        g.drawRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.BLUE);
    }
}
