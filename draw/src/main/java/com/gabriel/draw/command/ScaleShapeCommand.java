package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private final Shape shape;
    private final AppService appService;
    private final Point oldEnd;
    private final Point newEnd;

    public ScaleShapeCommand(AppService appService, Shape shape, Point oldEnd, Point newEnd) {
        this.appService = appService;
        this.shape = shape;
        this.oldEnd = new Point(oldEnd);
        this.newEnd = new Point(newEnd);
    }

    @Override
    public void execute() {
        appService.scale(shape, newEnd);
        appService.repaint();
    }

    @Override
    public void undo() {
        appService.scale(shape, oldEnd);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
