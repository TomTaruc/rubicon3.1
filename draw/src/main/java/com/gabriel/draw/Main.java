package com.gabriel.draw;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.view.DrawingMenuBar;
import com.gabriel.draw.service.DeawingCommandAppService;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.view.DrawingToolBar;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.draw.view.DrawingFrame;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default look and feel
        }
        
        SwingUtilities.invokeLater(() -> {
            // Create services
            AppService drawingAppService = new DrawingAppService();
            AppService appService = new DeawingCommandAppService(drawingAppService);

            // Create shared action controller
            ActionController actionController = new ActionController(appService);

            // Create UI components
            DrawingFrame drawingFrame = new DrawingFrame(appService);
            DrawingMenuBar drawingMenuBar = new DrawingMenuBar(actionController);
            DrawingToolBar drawingToolBar = new DrawingToolBar(actionController);
            DrawingView drawingView = new DrawingView(appService);
            
            // Create drawing controller with action controller reference
            DrawingController drawingController = new DrawingController(appService, drawingView, actionController);

            // Setup frame
            drawingFrame.setContentPane(drawingView);
            drawingFrame.setJMenuBar(drawingMenuBar);
            drawingFrame.getContentPane().add(drawingToolBar, BorderLayout.PAGE_START);

            // Configure frame
            drawingFrame.setTitle("Drawing Application - Create shapes with mouse or Shift+Click");
            drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            drawingFrame.setSize(1000, 700);
            drawingFrame.setLocationRelativeTo(null); // Center on screen
            
            // Initial UI state update
            actionController.updateUIState();
            
            // Show frame
            drawingFrame.setVisible(true);
        });
    }
}