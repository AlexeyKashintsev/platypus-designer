package com.eas.client.model.gui.view;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Marat
 */
public class EstimatedArc extends ArrayList<Point>{
    int segmentNumber = -1;
    Point center = null;
    
    public EstimatedArc(int aSegmentNumber, Point aCenter)
    {
        super();
        segmentNumber = aSegmentNumber;
        center = aCenter;
    }

    public void setSegmentNumber(int aSegmentNumber) {
        segmentNumber = aSegmentNumber;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public Point getCenter() {
        return center;
    }
}
