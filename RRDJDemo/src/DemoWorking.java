import org.rrd4j.core.*;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import static org.rrd4j.ConsolFun.*;
import static org.rrd4j.DsType.COUNTER;
import static org.rrd4j.DsType.GAUGE;

/**
 * Simple demo just to check that everything is OK with this library. Creates two files in your
 * $HOME/rrd4j-demo directory: demo.rrd and demo.png.
 */
public class DemoWorking {
    static final long SEED = 1909752002L;
    static final Random RANDOM = new Random(SEED);
    static final String FILE = "host/Ubuntu Reference/workspace/Projects/AppsOne/StreetFight/RRDJDemo/src/cpu_user";

    static final long START = Util.getTimestamp(2013, 8, 20);
    static final long END = Util.getTimestamp(2013, 8, 21);

    public static void main(String[] args) throws IOException {
        println("== Starting demo");
        long startMillis = System.currentTimeMillis();
        if (args.length > 0) {
            println("Setting default backend factory to " + args[0]);
            RrdDb.setDefaultFactory(args[0]);
        }
        long start = START;
        long end = END;
        String rrdPath = "/host/Ubuntu Reference/workspace/Projects/AppsOne/StreetFight/RRDJDemo/resources/disk_free.rrd";
        String xmlPath = "/host/Ubuntu Reference/workspace/Projects/AppsOne/StreetFight/RRDJDemo/resources/disk_free.xml";
        String rrdRestoredPath = "/host/Ubuntu Reference/workspace/Projects/AppsOne/StreetFight/RRDJDemo/resources/disk_free.rrd";

        println("== Fetching RRD file " + rrdPath);

        // test read-only access!

        RrdDb rrdDb = new RrdDb(rrdPath, true);
        /*
        System.out.println(rrdDb.getArcIndex(AVERAGE, 15));

        println("File reopen in read-only mode");
        println("== Last update time was: " + rrdDb.getLastUpdateTime());
        println("== Last info was: " + rrdDb.getInfo());

        // fetch data
        println("== Fetching data for the whole month");
        FetchRequest request = rrdDb.createFetchRequest(AVERAGE, start, end);
        println(request.dump());

        FetchData fetchData = request.fetchData();
        println("== Data fetched. " + fetchData.getRowCount() + " points obtained");
        println(fetchData.toString());
        println("== Dumping fetched data to XML format");
        println(fetchData.exportXml());
        println("== Fetch completed");
        */
        // dump to XML file

        println("== Dumping RRD file to XML file " + xmlPath + " (can be restored with RRDTool)");
        rrdDb.exportXml(xmlPath);
        println("== Creating RRD file " + rrdRestoredPath + " from XML file " + xmlPath);
        RrdDb rrdRestoredDb = new RrdDb(rrdRestoredPath, xmlPath);

        // close files
        println("== Closing both RRD files");
        rrdDb.close();
        println("== First file closed");
        rrdRestoredDb.close();
        println("== Second file closed");


        println("== Demo completed in " +
                ((System.currentTimeMillis() - startMillis) / 1000.0) + " sec");
    }

    static void println(String msg) {
        //System.out.println(msg + " " + Util.getLapTime());
        System.out.println(msg);
    }

    static void print(String msg) {
        System.out.print(msg);
    }

    static class GaugeSource {
        private double value;
        private double step;

        GaugeSource(double value, double step) {
            this.value = value;
            this.step = step;
        }

        long getValue() {
            double oldValue = value;
            double increment = RANDOM.nextDouble() * step;
            if (RANDOM.nextDouble() > 0.5) {
                increment *= -1;
            }
            value += increment;
            if (value <= 0) {
                value = 0;
            }
            return Math.round(oldValue);
        }
    }
}




