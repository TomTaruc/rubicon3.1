package com.gabriel.draw.controller;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    AppService appService;
    public  ActionController(AppService appService){
        this.appService = appService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (ActionCommand.UNDO.equals(cmd)) {
            appService.undo();
        }
        if (ActionCommand.REDO.equals(cmd)) {
            appService.redo();
        }
        if (ActionCommand.LINE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Line);
        }
        if (ActionCommand.RECT.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Rectangle);
        }
        if (ActionCommand.ELLIPSE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Ellipse);
        }
        if (ActionCommand.SETCOLOR.equals(cmd)) {
            Color color = JColorChooser.showDialog(null, "Choose a color", appService.getColor());

            appService.setColor(color);
        }
    }
}
