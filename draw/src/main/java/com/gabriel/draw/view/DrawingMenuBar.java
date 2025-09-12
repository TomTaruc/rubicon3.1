package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {

    public DrawingMenuBar(ActionController actionController) {
        super();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem clearAllMenuItem = new JMenuItem("Clear All");
        clearAllMenuItem.setActionCommand(ActionCommand.CLEAR_ALL);
        clearAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        clearAllMenuItem.addActionListener(actionController);
        clearAllMenuItem.setToolTipText("Clear all shapes from the canvas");
        actionController.registerMenuItem(clearAllMenuItem);
        fileMenu.add(clearAllMenuItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(ActionCommand.EXIT);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(actionController);
        exitMenuItem.setToolTipText("Exit the application");
        actionController.registerMenuItem(exitMenuItem);
        fileMenu.add(exitMenuItem);
        
        add(fileMenu);
        
        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionController);
        undoMenuItem.setToolTipText("Undo the last action");
        actionController.registerMenuItem(undoMenuItem);
        editMenu.add(undoMenuItem);
        
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenuItem.addActionListener(actionController);
        redoMenuItem.setToolTipText("Redo the last undone action");
        actionController.registerMenuItem(redoMenuItem);
        editMenu.add(redoMenuItem);
        
        add(editMenu);
        
        // Draw Menu
        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        
        JRadioButtonMenuItem lineMenuItem = new JRadioButtonMenuItem("Line");
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        lineMenuItem.addActionListener(actionController);
        lineMenuItem.setToolTipText("Draw lines");
        actionController.registerMenuItem(lineMenuItem);
        drawMenu.add(lineMenuItem);
        
        JRadioButtonMenuItem rectangleMenuItem = new JRadioButtonMenuItem("Rectangle");
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        rectangleMenuItem.addActionListener(actionController);
        rectangleMenuItem.setToolTipText("Draw rectangles");
        actionController.registerMenuItem(rectangleMenuItem);
        drawMenu.add(rectangleMenuItem);
        
        JRadioButtonMenuItem ellipseMenuItem = new JRadioButtonMenuItem("Ellipse");
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        ellipseMenuItem.addActionListener(actionController);
        ellipseMenuItem.setToolTipText("Draw ellipses");
        ellipseMenuItem.setSelected(true); // Default selection
        actionController.registerMenuItem(ellipseMenuItem);
        drawMenu.add(ellipseMenuItem);
        
        // Group radio buttons
        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(lineMenuItem);
        shapeGroup.add(rectangleMenuItem);
        shapeGroup.add(ellipseMenuItem);
        
        add(drawMenu);
        
        // Properties Menu
        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        
        JMenuItem colorMenuItem = new JMenuItem("Color...");
        colorMenuItem.setActionCommand(ActionCommand.SET_COLOR);
        colorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        colorMenuItem.addActionListener(actionController);
        colorMenuItem.setToolTipText("Choose drawing color");
        actionController.registerMenuItem(colorMenuItem);
        propMenu.add(colorMenuItem);
        
        add(propMenu);
        
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setActionCommand(ActionCommand.ABOUT);
        aboutMenuItem.addActionListener(actionController);
        aboutMenuItem.setToolTipText("About this application");
        actionController.registerMenuItem(aboutMenuItem);
        helpMenu.add(aboutMenuItem);
        
        add(helpMenu);
    }
}