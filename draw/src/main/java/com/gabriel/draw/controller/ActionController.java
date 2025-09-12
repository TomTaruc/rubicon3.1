package com.gabriel.draw.controller;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ActionController implements ActionListener {
    private final AppService appService;
    private final List<JMenuItem> menuItems;
    private final List<AbstractButton> toolbarButtons;
    
    public ActionController(AppService appService) {
        this.appService = appService;
        this.menuItems = new ArrayList<>();
        this.toolbarButtons = new ArrayList<>();
    }
    
    public void registerMenuItem(JMenuItem menuItem) {
        menuItems.add(menuItem);
    }
    
    public void registerToolbarButton(AbstractButton button) {
        toolbarButtons.add(button);
    }
    
    public void updateUIState() {
        // Update undo/redo state based on command stacks
        boolean canUndo = canUndo();
        boolean canRedo = canRedo();
        
        for (JMenuItem item : menuItems) {
            String command = item.getActionCommand();
            if (ActionCommand.UNDO.equals(command)) {
                item.setEnabled(canUndo);
            } else if (ActionCommand.REDO.equals(command)) {
                item.setEnabled(canRedo);
            }
        }
        
        for (AbstractButton button : toolbarButtons) {
            String command = button.getActionCommand();
            if (ActionCommand.UNDO.equals(command)) {
                button.setEnabled(canUndo);
            } else if (ActionCommand.REDO.equals(command)) {
                button.setEnabled(canRedo);
            }
        }
        
        // Update shape mode selection
        updateShapeModeSelection();
    }
    
    private void updateShapeModeSelection() {
        ShapeMode currentMode = appService.getShapeMode();
        
        for (JMenuItem item : menuItems) {
            String command = item.getActionCommand();
            if (ActionCommand.LINE.equals(command) || 
                ActionCommand.RECT.equals(command) || 
                ActionCommand.ELLIPSE.equals(command)) {
                item.setSelected(isCurrentShapeMode(command, currentMode));
            }
        }
        
        for (AbstractButton button : toolbarButtons) {
            String command = button.getActionCommand();
            if (ActionCommand.LINE.equals(command) || 
                ActionCommand.RECT.equals(command) || 
                ActionCommand.ELLIPSE.equals(command)) {
                button.setSelected(isCurrentShapeMode(command, currentMode));
            }
        }
    }
    
    private boolean isCurrentShapeMode(String command, ShapeMode currentMode) {
        switch (command) {
            case ActionCommand.LINE:
                return currentMode == ShapeMode.Line;
            case ActionCommand.RECT:
                return currentMode == ShapeMode.Rectangle;
            case ActionCommand.ELLIPSE:
                return currentMode == ShapeMode.Ellipse;
            default:
                return false;
        }
    }
    
    private boolean canUndo() {
        // This would need to be implemented in the command service
        return true; // Placeholder
    }
    
    private boolean canRedo() {
        // This would need to be implemented in the command service
        return true; // Placeholder
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        switch (cmd) {
            case ActionCommand.UNDO:
                appService.undo();
                break;
            case ActionCommand.REDO:
                appService.redo();
                break;
            case ActionCommand.LINE:
                appService.setShapeMode(ShapeMode.Line);
                break;
            case ActionCommand.RECT:
                appService.setShapeMode(ShapeMode.Rectangle);
                break;
            case ActionCommand.ELLIPSE:
                appService.setShapeMode(ShapeMode.Ellipse);
                break;
            case ActionCommand.SET_COLOR:
                Color color = JColorChooser.showDialog(null, "Choose a color", appService.getColor());
                if (color != null) {
                    appService.setColor(color);
                }
                break;
            case ActionCommand.CLEAR_ALL:
                int result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to clear all shapes?",
                    "Clear All",
                    JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    // Implementation would need to be added to AppService
                }
                break;
            case ActionCommand.EXIT:
                appService.close();
                break;
            case ActionCommand.ABOUT:
                JOptionPane.showMessageDialog(
                    null,
                    "Drawing Application v1.0\nA simple drawing tool with shapes and colors.",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE
                );
                break;
        }
        
        updateUIState();
    }
}