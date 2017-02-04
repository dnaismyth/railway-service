package com.flow.railwayservice.service.util;

import java.io.IOException;
import java.io.StringWriter;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

public class LocationUtil {
	/**
     * Example of creating a Point.
     */
    public static Point createPoint(double longitude, double latitude){
        GeometryFactory gf = new GeometryFactory();

        Coordinate coord = new Coordinate(longitude, latitude );
        Point point = gf.createPoint( coord );
        
        return point;
    }
    
    public static Point createPointFromCoordinates(double longitude, double latitude){
    	String wktString = wktWriter(longitude, latitude);
    	return parseWKT(wktString);
    }

    public static String wktWriter(double longitude, double latitude){
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);

        Coordinate coord = new Coordinate(longitude, latitude);
        Point point = gf.createPoint( coord );
        
        StringWriter writer = new StringWriter();
        WKTWriter wktWriter = new WKTWriter(2);
        
        try {
            wktWriter.write( point, writer );
        } catch (IOException e) {            
        }
        
        String wkt = writer.toString();
                
        return wkt;
    }
    
    public static Point parseWKT(String wktString){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        WKTReader reader = new WKTReader( geometryFactory );
        Point point = null;
        try {
            point = (Point) reader.read(wktString);
        } catch (ParseException e) {            
        }
        return point;
    }
 
}
