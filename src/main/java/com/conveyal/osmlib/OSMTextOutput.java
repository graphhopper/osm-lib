package com.conveyal.osmlib;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

/**
 * Write OSM ways and nodes out to a simple human-readable text format.
 */
public class OSMTextOutput {

    OutputStream out;
    PrintStream print;
    OSM osm;
    // NodeTracker nodesSeen;

    public OSMTextOutput (OutputStream out, OSM osm) {
        this.osm = osm;
        this.out = out;
        this.print = new PrintStream(out);
        // nodesSeen = new NodeTracker();
    }

    public void printWay(long wayId) {
        Way way = osm.ways.get(wayId);
        print.print("W ");
        print.print(wayId);
        print.print(' ');
        printTags(way);
        print.print('\n');
        for (long nodeId : way.nodes) {
            // if (nodesSeen.contains(nodeId)) {
            //     printNodeRef(nodeId);
            // }
            // } else {
            printNodeFull(nodeId);
            //  nodesSeen.add(nodeId);
        }
    }

    public void printNodeRef(long nodeId) {
        print.print("N ");
        print.print(nodeId);
        print.print('\n');
    }

    public void printNodeFull(long nodeId) {
        print.print("N ");
        print.print(nodeId);
        print.print(' ');
        Node node = osm.nodes.get(nodeId);
        print.printf(Locale.US, "%2.6f", node.getLat());
        print.print(' ');
        print.printf(Locale.US, "%3.6f", node.getLon());
        print.print(' ');
        printTags(node);
        print.print('\n');
    }

    public void printTags (OSMEntity tagged) {
        if (tagged.hasNoTags()) {
            return;
        }
        for (OSMEntity.Tag tag : tagged.tags) {
            print.print(tag.key);
            print.print("=");
            print.print(tag.value);
            print.print(";");
        }
    }
}
