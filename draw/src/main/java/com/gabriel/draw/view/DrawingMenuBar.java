package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {

    public DrawingMenuBar(ActionController actionController) {
        super();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(ActionCommand.EXIT);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(exitMenuItem);
        fileMenu.add(exitMenuItem);
        
        add(fileMenu);
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(undoMenuItem);
        editMenu.add(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(redoMenuItem);
        editMenu.add(redoMenuItem);

        editMenu.addSeparator();

        JMenu selectMenu = new JMenu("Select");
        
        JRadioButtonMenuItem selectMoveMenuItem = new JRadioButtonMenuItem("Move");
        selectMoveMenuItem.setActionCommand(ActionCommand.SELECT_MOVE);
        selectMoveMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(selectMoveMenuItem);
        selectMenu.add(selectMoveMenuItem);

        JRadioButtonMenuItem selectScaleMenuItem = new JRadioButtonMenuItem("Scale");
        selectScaleMenuItem.setActionCommand(ActionCommand.SELECT_SCALE);
        selectScaleMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(selectScaleMenuItem);
        selectMenu.add(selectScaleMenuItem);

        JRadioButtonMenuItem selectNoneMenuItem = new JRadioButtonMenuItem("None");
        selectNoneMenuItem.setActionCommand(ActionCommand.SELECT_NONE);
        selectNoneMenuItem.addActionListener(actionController);
        selectNoneMenuItem.setSelected(true);
        actionController.registerMenuItem(selectNoneMenuItem);
        selectMenu.add(selectNoneMenuItem);

        ButtonGroup selectGroup = new ButtonGroup();
        selectGroup.add(selectMoveMenuItem);
        selectGroup.add(selectScaleMenuItem);
        selectGroup.add(selectNoneMenuItem);

        editMenu.add(selectMenu);

        add(editMenu);
        
        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        
        JRadioButtonMenuItem lineMenuItem = new JRadioButtonMenuItem("Line");
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        lineMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(lineMenuItem);
        drawMenu.add(lineMenuItem);
        
        JRadioButtonMenuItem rectangleMenuItem = new JRadioButtonMenuItem("Rectangle");
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        rectangleMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(rectangleMenuItem);
        drawMenu.add(rectangleMenuItem);
        
        JRadioButtonMenuItem ellipseMenuItem = new JRadioButtonMenuItem("Ellipse");
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        ellipseMenuItem.addActionListener(actionController);
        ellipseMenuItem.setSelected(true);
        actionController.registerMenuItem(ellipseMenuItem);
        drawMenu.add(ellipseMenuItem);
        
        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(lineMenuItem);
        shapeGroup.add(rectangleMenuItem);
        shapeGroup.add(ellipseMenuItem);
        
        add(drawMenu);
        
        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        
        JMenuItem colorMenuItem = new JMenuItem("Color...");
        colorMenuItem.setActionCommand(ActionCommand.SET_COLOR);
        colorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        colorMenuItem.addActionListener(actionController);
        actionController.registerMenuItem(colorMenuItem);
        propMenu.add(colorMenuItem);
        
        add(propMenu);
    }
}
