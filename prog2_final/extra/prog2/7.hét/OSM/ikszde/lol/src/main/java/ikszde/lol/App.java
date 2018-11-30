package ikszde.lol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 * A simple sample application that shows
 * a OSM map of Europe containing a route with waypoints
 * @author Martin Steiger
 */
public class App
{
    /**
     * @param args the program args (ignored)
     */
    public static void main(String[] args)
    {
        JXMapViewer mapViewer = new JXMapViewer();

        //Létrehozza a JFramet
        JFrame frame = new JFrame("Harom pad");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Kell az OSM használatához
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition egyPad = new GeoPosition(47.5430216, 21.6402673);
        GeoPosition egyMasikPad = new GeoPosition(47.5362762, 21.6204720);
        GeoPosition egyHarmadikPad = new GeoPosition(47.5325072, 21.6221011);


        // Listába teszi a pontokat
        List<GeoPosition> track = Arrays.asList(egyPad, egyMasikPad, egyHarmadikPad);

        // Beállítja a legjobb pozíciót, hogy az összes pont jól látszódjon
        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        // Waypointokat csinál a térképen
        Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
                new DefaultWaypoint(egyPad),
                new DefaultWaypoint(egyMasikPad),
                new DefaultWaypoint(egyHarmadikPad)));

        // ?!
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // felrajzolja a pontokat
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
}