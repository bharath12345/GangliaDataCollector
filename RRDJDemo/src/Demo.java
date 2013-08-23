/* ============================================================

 * Rrd4j : Pure java implementation of RRDTool's functionality

 * ============================================================

 *

 * Project Info:  http://www.rrd4j.org

 * Project Lead:  Mathias Bogaert (m.bogaert@memenco.com)

 *

 * (C) Copyright 2003-2007, by Sasa Markovic.

 *

 * This library is free software; you can redistribute it and/or modify it under the terms

 * of the GNU Lesser General Public License as published by the Free Software Foundation;

 * either version 2.1 of the License, or (at your option) any later version.

 *

 * Developers:    Sasa Markovic

 *

 *

 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;

 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

 * See the GNU Lesser General Public License for more details.

 *

 * You should have received a copy of the GNU Lesser General Public License along with this

 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,

 * Boston, MA 02111-1307, USA.

 */

import static org.rrd4j.ConsolFun.*;

import org.rrd4j.core.*;

import org.rrd4j.graph.RrdGraph;

import org.rrd4j.graph.RrdGraphDef;

import org.rrd4j.DsType;



import java.awt.*;

import java.io.BufferedOutputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.PrintWriter;

import java.util.Random;



/**

 * Simple demo just to check that everything is OK with this library. Creates two files in your

 * $HOME/rrd4j-demo directory: demo.rrd and demo.png.

 */

public class Demo {

    static final long SEED = 1909752002L;

    static final Random RANDOM = new Random(SEED);

    static final String FILE = "/var/lib/ganglia/rrds/unspecified/ubuntu.local/mem_total";



    static final long START = Util.getTimestamp(2013, 8, 20, 13, 0);

    static final long END = Util.getTimestamp(2013, 8, 21,14, 0);

    static final int MAX_STEP = 300;



    static final int IMG_WIDTH = 500;

    static final int IMG_HEIGHT = 300;



    /**

     * To start the demo, use the following command:

     * java -cp rrd4j-{version}.jar org.rrd4j.demo.Demo

     * @param args the name of the backend factory to use (optional)

     * @throws IOException Thrown

     */

    public static void main(String[] args) throws IOException {

        println("== Starting demo");

        long startMillis = System.currentTimeMillis();

        if (args.length > 0) {

            println("Setting default backend factory to " + args[0]);

            RrdDb.setDefaultFactory(args[0]);

        }

        long start = START;

        long end = END;

        //String rrdPath = Util.getRrd4jDemoPath(FILE + ".rrd");

        String rrdPath = FILE + ".rrd";

        //String xmlPath = Util.getRrd4jDemoPath(FILE + ".xml");

        String xmlPath = FILE + ".xml";

        String rrdRestoredPath = Util.getRrd4jDemoPath(FILE + "_restored.rrd");

        //String imgPath = Util.getRrd4jDemoPath(FILE + ".png");

        //String logPath = Util.getRrd4jDemoPath(FILE + ".log");

       // RrdDef rrdDef = new RrdDef(rrdPath, start - 1, 300);

        //RrdDef rrdDef = new RrdDef(rrdPath, start - 1, 300);


        //System.out.println("DataSource :"+rrdDef.getDsDefs().toString());

        /*for(DsDef dsDef :rrdDef.getDsDefs()){

            System.out.println(dsDef.getDsName() + "-" + dsDef.getDsType());
        }

        System.out.println("Finished Fetching DataSource");  */

        //rrdDef.addDatasource("shade", DsType.GAUGE, 600, 0, Double.NaN);

        //rrdDef.addArchive(AVERAGE, 0.5, 1, 600);

        //rrdDef.addArchive(TOTAL, 0.5, 1, 600);

        //PrintWriter log = new PrintWriter(new BufferedOutputStream(new FileOutputStream(logPath, false)));


        // creation

        /*println("== Creating RRD file " + rrdPath);



        rrdDef.addDatasource("sun", DsType.GAUGE, 600, 0, Double.NaN);

        rrdDef.addDatasource("shade", DsType.GAUGE, 600, 0, Double.NaN);

        rrdDef.addArchive(AVERAGE, 0.5, 1, 600);

        rrdDef.addArchive(AVERAGE, 0.5, 6, 700);

        rrdDef.addArchive(AVERAGE, 0.5, 24, 775);

        rrdDef.addArchive(AVERAGE, 0.5, 288, 797);

        rrdDef.addArchive(MAX, 0.5, 1, 600);

        rrdDef.addArchive(MAX, 0.5, 6, 700);

        rrdDef.addArchive(MAX, 0.5, 24, 775);

        rrdDef.addArchive(MAX, 0.5, 288, 797);

        println(rrdDef.dump());

        log.println(rrdDef.dump());

        println("Estimated file size: " + rrdDef.getEstimatedSize());

        println("== RRD file created.");

        if (rrdDb.getRrdDef().equals(rrdDef)) {

            println("Checking RRD file structure... OK");

        }

        else {

            println("Invalid RRD file created. This is a serious bug, bailing out");

            return;

        }

        rrdDb.close();

        println("== RRD file closed.");



        // update database

        GaugeSource sunSource = new GaugeSource(1200, 20);

        GaugeSource shadeSource = new GaugeSource(300, 10);

        println("== Simulating one month of RRD file updates with step not larger than " +

                MAX_STEP + " seconds (* denotes 1000 updates)");

        long t = start;

        int n = 0;

        rrdDb = new RrdDb(rrdPath);

        Sample sample = rrdDb.createSample();



        while (t <= end + 86400L) {

            sample.setTime(t);

            sample.setValue("sun", sunSource.getValue());

            sample.setValue("shade", shadeSource.getValue());

            log.println(sample.dump());

            sample.update();

            t += RANDOM.nextDouble() * MAX_STEP + 1;

            if (((++n) % 1000) == 0) {

                System.out.print("*");

            }

        }



        rrdDb.close();



        println("");

        println("== Finished. RRD file updated " + n + " times");

        //rrdDb.close();

        */

        // test read-only access!

        RrdDb rrdDb = new RrdDb(rrdPath);
        rrdDb.exportXml(xmlPath);
        RrdDb rrdRestoredDb = new RrdDb(rrdRestoredPath, xmlPath);
        rrdDb.close();

        //RrdDef def = rrdRestoredDb.getRrdDef();
        println("File reopen in read-only mode");

        println("== Last update time was: " + rrdDb.getLastUpdateTime());

        println("== Last info was: " + rrdDb.getInfo());



        // fetch data

        println("== Fetching data for the whole month");

        FetchRequest request = rrdRestoredDb.createFetchRequest(TOTAL, start, end);

        println(request.dump());

        //log.println(request.dump());

        FetchData fetchData = request.fetchData();

        println("== Data fetched. " + fetchData.getRowCount() + " points obtained");

        println(fetchData.toString());

        println("== Dumping fetched data to XML format");

        println(fetchData.exportXml());

        println("== Fetch completed");



        // dump to XML file

        println("== Dumping RRD file to XML file " + xmlPath + " (can be restored with RRDTool)");

        //rrdDb.exportXml(xmlPath);

       // println("== Creating RRD file " + rrdRestoredPath + " from XML file " + xmlPath);

      //  RrdDb rrdRestoredDb = new RrdDb(rrdRestoredPath, xmlPath);



        // close files

        println("== Closing both RRD files");

        rrdDb.close();

        println("== First file closed");

        //rrdRestoredDb.close();

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