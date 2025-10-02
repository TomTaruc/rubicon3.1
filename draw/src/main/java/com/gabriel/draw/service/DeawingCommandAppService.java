package com.gabriel.draw.service;

import com.gabriel.draw.command.AddShapeCommand;
import com.gabriel.draw.command.SetDrawModeCommand;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.EditMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.ScalerService;
import com.gabriel.drawfx.service.SearchService;

import javax.swing.*;
import java.awt.*;

public class DeawingCommandAppService implements AppService {
    private final AppService appService;
    
    public DeawingCommandAppService(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void undo() {
        CommandService.undo();
        appService.repaint();
    }

    @Override
    public void redo() {
        CommandService.redo();
        appService.repaint();
    }

    @Override
    public ShapeMode getShapeMode() {
        return appService.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        appService.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return appService.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        appService.setDrawMode(drawMode);
    }

    @Override
    public EditMode getEditMode() {
        return appService.getEditMode();
    }

    @Override
    public void setEditMode(EditMode editMode) {
        appService.setEditMode(editMode);
    }

    @Override
    public Color getColor() {
        return appService.getColor();
    }

    @Override
    public void setColor(Color color) {
        appService.setColor(color);
    }

    @Override
    public Color getFill() {
        return appService.getFill();
    }

    @Override
    public void setFill(Color color) {
        appService.setFill(color);
    }

    @Override
    public void move(Shape shape, Point newLoc) {
        appService.move(shape, newLoc);
    }

    @Override
    public void scale(Shape shape, Point newEnd) {
        appService.scale(shape, newEnd);
    }

    @Override
    public void create(Shape shape) {
        Command command = new AddShapeCommand(appService, shape);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public void delete(Shape shape) {
        appService.delete(shape);
    }

    @Override
    public void close() {
        appService.close();
    }

    @Override
    public SearchService getSearchService() {
        return appService.getSearchService();
    }

    @Override
    public ScalerService getScalerService() {
        return appService.getScalerService();
    }

    @Override
    public Object getModel() {
        return appService.getModel();
    }

    @Override
    public JPanel getView() {
        return appService.getView();
    }

    @Override
    public void setView(JPanel panel) {
        appService.setView(panel);
    }

    @Override
    public void repaint() {
        appService.repaint();
    }
    
    public boolean canUndo() {
        return CommandService.canUndo();
    }
    
    public boolean canRedo() {
        return CommandService.canRedo();
    }
}