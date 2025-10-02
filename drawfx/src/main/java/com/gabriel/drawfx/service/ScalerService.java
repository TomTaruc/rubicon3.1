package com.gabriel.drawfx.service;

import com.gabriel.drawfx.ScalingMode;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class ScalerService {

    private static final int ANCHOR_SEARCH_RADIUS = 8;

    public void scale(Shape shape, Point newEnd) {
        shape.setEnd(newEnd);
    }

    public void scaleByAnchor(Shape shape, int anchorIndex, Point newPosition, ScalingMode scalingMode) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();

        int x1 = Math.min(start.x, end.x);
        int y1 = Math.min(start.y, end.y);
        int x2 = Math.max(start.x, end.x);
        int y2 = Math.max(start.y, end.y);

        Point newStart = new Point(x1, y1);
        Point newEnd = new Point(x2, y2);

        switch (anchorIndex) {
            case 0:
                newStart.setLocation(newPosition.x, newPosition.y);
                break;
            case 1:
                newStart.setLocation(x1, newPosition.y);
                newEnd.setLocation(x2, y2);
                break;
            case 2:
                newStart.setLocation(x1, newPosition.y);
                newEnd.setLocation(newPosition.x, y2);
                break;
            case 3:
                newStart.setLocation(x1, y1);
                newEnd.setLocation(newPosition.x, y2);
                break;
            case 4:
                newEnd.setLocation(newPosition.x, newPosition.y);
                break;
            case 5:
                newStart.setLocation(x1, y1);
                newEnd.setLocation(x2, newPosition.y);
                break;
            case 6:
                newStart.setLocation(newPosition.x, y1);
                newEnd.setLocation(x2, newPosition.y);
                break;
            case 7:
                newStart.setLocation(newPosition.x, y1);
                newEnd.setLocation(x2, y2);
                break;
        }

        shape.setLocation(newStart);
        shape.setEnd(newEnd);
    }

    public int getAnchorSearchRadius() {
        return ANCHOR_SEARCH_RADIUS;
    }
}
