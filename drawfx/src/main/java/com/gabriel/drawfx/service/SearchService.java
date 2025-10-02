package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;
import java.util.List;

public final class SearchService {

    private static final int DEFAULT_SEARCH_RADIUS = 5;

    public Shape findShapeAtPoint(List<Shape> shapes, Point point) {
        return findShapeAtPoint(shapes, point, DEFAULT_SEARCH_RADIUS);
    }

    public Shape findShapeAtPoint(List<Shape> shapes, Point point, int radius) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (isPointInShape(shape, point, radius)) {
                return shape;
            }
        }
        return null;
    }

    private boolean isPointInShape(Shape shape, Point point, int radius) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();

        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);

        Rectangle bounds = new Rectangle(x - radius, y - radius, width + 2 * radius, height + 2 * radius);
        return bounds.contains(point);
    }

    public int findScaleAnchor(Shape shape, Point point, int searchRadius) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();

        int x1 = Math.min(start.x, end.x);
        int y1 = Math.min(start.y, end.y);
        int x2 = Math.max(start.x, end.x);
        int y2 = Math.max(start.y, end.y);

        int midX = (x1 + x2) / 2;
        int midY = (y1 + y2) / 2;

        if (isNear(point, new Point(x1, y1), searchRadius)) return 0;
        if (isNear(point, new Point(midX, y1), searchRadius)) return 1;
        if (isNear(point, new Point(x2, y1), searchRadius)) return 2;
        if (isNear(point, new Point(x2, midY), searchRadius)) return 3;
        if (isNear(point, new Point(x2, y2), searchRadius)) return 4;
        if (isNear(point, new Point(midX, y2), searchRadius)) return 5;
        if (isNear(point, new Point(x1, y2), searchRadius)) return 6;
        if (isNear(point, new Point(x1, midY), searchRadius)) return 7;

        return -1;
    }

    private boolean isNear(Point p1, Point p2, int radius) {
        double distance = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        return distance <= radius;
    }
}
