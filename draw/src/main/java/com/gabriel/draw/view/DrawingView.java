package com.gabriel.draw.view;

import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {
    private final AppService appService;

    public DrawingView(AppService appService) {
        this.appService = appService;
        appService.setView(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
        
        // Enable tooltips for this component
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Enable antialiasing for smoother drawing
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        }
        
        Drawing drawing = (Drawing) appService.getModel();
        if (drawing != null && drawing.getShapes() != null) {
            for (Shape shape : drawing.getShapes()) {
                if (shape != null && shape.getRendererService() != null) {
                    shape.getRendererService().render(g, shape, false);
                }
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        paintComponent(g);
    }
}