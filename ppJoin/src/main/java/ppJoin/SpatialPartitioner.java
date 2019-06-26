package ppJoin;

import org.danilopianini.util.FlexibleQuadTree;
import org.danilopianini.util.SpatialIndex;
import ppJoin.Services.ReaderService;
import ppJoin.enums.Settings;
import ppJoin.pojos.Record;

import java.io.*;
import java.util.*;

import static ppJoin.enums.Settings.TEMP_RECORDS_PATH;

public class SpatialPartitioner {

    Integer csvFileCounter = 0;


    private ReaderService readerService = new ReaderService();

    public void partitionData(List<Record> recordList) {

        Double distanceThreshold = 100.0;

        partition2dSpaceForConcurrentSpatialJoin(recordList, distanceThreshold,
                findUpperRightPoint(recordList), findLowerLeftPoint(recordList));

    }

    private Double euclideanDistanceOfRecords (Record r1, Record r2) {
        return Math.hypot(r1.getAxisValueX()-r2.getAxisValueX(), r1.getAxisValueY()-r2.getAxisValueY());
    }

    private Double euclideanDistance (Double x1, Double y1, Double x2, Double y2) {
        return Math.hypot(x1-x2, y1-y2);
    }

    private Record findUpperRightPoint (List<Record> recordList) {
//        Double maxDistance = 0.0;
//        Record upperRightRecord = new Record("", 0, 0, Collections.emptyList());
//        Record OORecord = upperRightRecord;
//        for (Record record : recordList) {
//            Double currentDistance = euclideanDistanceOfRecords(OORecord, record);
//            if (currentDistance > maxDistance) {
//                maxDistance = currentDistance;
//                upperRightRecord = record;
//            }
//        }
//        return upperRightRecord;
        Double x = findMaxRightPoint(recordList).getAxisValueX();
        Double y = findMaxHeightPoint(recordList).getAxisValueY();
        return new Record("", x, y, Collections.emptyList());
    }

    private Record findLowerLeftPoint (List<Record> recordList) {
//        Double minDistance = Double.MAX_VALUE;
//        Record lowerLeftRecord = new Record("", Double.MAX_VALUE, Double.MAX_VALUE, Collections.emptyList());
//        Record OORecord = new Record("", 0, 0, Collections.emptyList());
//        for (Record record : recordList) {
//            Double currentDistance = euclideanDistanceOfRecords(OORecord, record);
//            if (currentDistance < minDistance) {
//                minDistance = currentDistance;
//                lowerLeftRecord = record;
//            }
//        }
//        return lowerLeftRecord;
        Double x = findMaxLeftPoint(recordList).getAxisValueX();
        Double y = findMaxBottomPoint(recordList).getAxisValueY();
        return new Record("",x ,y, Collections.emptyList());
    }

    private Record findMaxLeftPoint (List<Record> recordList) {
        Double maxLeft = Double.MAX_VALUE;
        Record maxLeftPoint = new Record("", 0, 0, Collections.emptyList());
        for (Record record : recordList) {
            if (record.getAxisValueX() < maxLeft) {
                maxLeft = record.getAxisValueX();
                maxLeftPoint = record;
            }
        }
        return maxLeftPoint;
    }

    private Record findMaxRightPoint (List<Record> recordList) {
        Double maxRight = 0.0;
        Record maxRightPoint = new Record("", 0, 0, Collections.emptyList());
        for (Record record : recordList) {
            if (record.getAxisValueX() > maxRight) {
                maxRight = record.getAxisValueX();
                maxRightPoint = record;
            }
        }
        return maxRightPoint;
    }

    private Record findMaxHeightPoint (List<Record> recordList) {
        Double maxHeight = 0.0;
        Record maxHeightPoint = new Record("", 0, 0, Collections.emptyList());
        for (Record record : recordList) {
            if (record.getAxisValueY() > maxHeight) {
                maxHeight = record.getAxisValueY();
                maxHeightPoint = record;
            }
        }
        return maxHeightPoint;
    }

    private Record findMaxBottomPoint (List<Record> recordList) {
        Double maxBottom = Double.MAX_VALUE;
        Record maxBottomPoint = new Record("", 0, 0, Collections.emptyList());
        for (Record record : recordList) {
            if (record.getAxisValueY() < maxBottom) {
                maxBottom = record.getAxisValueY();
                maxBottomPoint = record;
            }
        }
        return maxBottomPoint;
    }


    private void partition2dSpaceForConcurrentSpatialJoin(List<Record> recordList, Double distanceThreshold,
                                                               Record upperRightPoint, Record lowerLeftPoint){

        double[] lowerRightPoint = new double[]{upperRightPoint.getAxisValueX(), lowerLeftPoint.getAxisValueY()};
        double[] upperLeftPoint = new double[]{lowerLeftPoint.getAxisValueX(), upperRightPoint.getAxisValueY()};

        double[] querySpaceLowerLeft = new double[]{lowerLeftPoint.getAxisValueX(), lowerLeftPoint.getAxisValueY()};
        double[] querySpaceUpperRight = new double[]{lowerLeftPoint.getAxisValueX() + distanceThreshold * 2,
                lowerLeftPoint.getAxisValueY()+ distanceThreshold * 2};

        while (querySpaceLowerLeft[1] < upperLeftPoint[1]) {
            while (querySpaceLowerLeft[0] < lowerRightPoint[0]) {

                System.out.println("querySpaceLowerLeft : " + Arrays.toString(querySpaceLowerLeft));
                System.out.println("querySpaceUpperRight : " + Arrays.toString(querySpaceUpperRight));

                List<Record> queryResult = query(recordList, querySpaceLowerLeft, querySpaceUpperRight);
                writeListToCSV(queryResult);

                querySpaceLowerLeft = new double[]{querySpaceLowerLeft[0] + distanceThreshold,
                        querySpaceLowerLeft[1]};

                querySpaceUpperRight = new double[]{querySpaceUpperRight[0] + distanceThreshold,
                        querySpaceUpperRight[1]};



            }
            System.out.println("--------------------------------------------------------------------------------------");
            querySpaceLowerLeft = new double[]{lowerLeftPoint.getAxisValueX(),
                    querySpaceLowerLeft[1] + distanceThreshold};
            querySpaceUpperRight = new double[]{lowerLeftPoint.getAxisValueX() + distanceThreshold * 2,
                    querySpaceUpperRight[1] + distanceThreshold};
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }


    private List<Record> query(List<Record> recordList, double[]querySpaceLowerLeft, double[]querySpaceUpperRight) {
        double lowerLeftX = querySpaceLowerLeft[0];
        double lowerLeftY = querySpaceLowerLeft[1];
        double upperRightX = querySpaceUpperRight[0];
        double upperRightY = querySpaceUpperRight[1];
        List<Record> queryResult =  new ArrayList<>();
        for (Record record : recordList) {
            if (record.getAxisValueX() >= lowerLeftX && record.getAxisValueX() <= upperRightX
                && record.getAxisValueY() >= lowerLeftY && record.getAxisValueY() <= upperRightY) {
                queryResult.add(record);
            }
        }
        return queryResult;
    }

    private void partition2dSpaceForConcurrentSpatialJoinUsingQuadTree(List<Record> recordList, Double distanceThreshold,
                                                          Record upperRightPoint, Record lowerLeftPoint){

        double[] lowerRightPoint = new double[]{upperRightPoint.getAxisValueX(), lowerLeftPoint.getAxisValueY()};
        double[] upperLeftPoint = new double[]{lowerLeftPoint.getAxisValueX(), upperRightPoint.getAxisValueY()};

        // Build a quad tree
        final SpatialIndex<Record> qt = new FlexibleQuadTree<>();

        for (Record record : recordList)
            qt.insert(record, record.getAxisValueX(), record.getAxisValueY());

        double[] querySpaceLowerLeft = new double[]{lowerLeftPoint.getAxisValueX(), lowerLeftPoint.getAxisValueY()};
        double[] querySpaceUpperRight = new double[]{lowerLeftPoint.getAxisValueX() + distanceThreshold * 2,
                lowerLeftPoint.getAxisValueY()+ distanceThreshold * 2};

        while (querySpaceLowerLeft[1] < upperLeftPoint[1]) {
            while (querySpaceLowerLeft[0] < lowerRightPoint[0]) {

                System.out.println("querySpaceLowerLeft : " + Arrays.toString(querySpaceLowerLeft));
                System.out.println("querySpaceUpperRight : " + Arrays.toString(querySpaceUpperRight));

                querySpaceLowerLeft = new double[]{querySpaceLowerLeft[0] + distanceThreshold,
                        querySpaceLowerLeft[1]};

                querySpaceUpperRight = new double[]{querySpaceUpperRight[0] + distanceThreshold,
                        querySpaceUpperRight[1]};

                List<Record> queryResult = qt.query(querySpaceLowerLeft, querySpaceUpperRight);
                writeListToCSV(queryResult);

            }
            System.out.println("--------------------------------------------------------------------------------------");
            querySpaceLowerLeft = new double[]{lowerLeftPoint.getAxisValueX(),
                    querySpaceLowerLeft[1] + distanceThreshold};
            querySpaceUpperRight = new double[]{lowerLeftPoint.getAxisValueX() + distanceThreshold * 2,
                    querySpaceUpperRight[1] + distanceThreshold};
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    public void writeListToCSV(List<Record> recordList) {
        final String CSV_SEPARATOR = ",";
        if (recordList.isEmpty())
            return;
        try {
                csvFileCounter++;
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(TEMP_RECORDS_PATH.getValue()+"partition"+csvFileCounter.toString()+".csv")
                        , "UTF-8"));
            for (Record record : recordList) {
                StringBuffer line = new StringBuffer();
                line.append(record.getTextValue());
                line.append(CSV_SEPARATOR);
                line.append(record.getAxisValueX());
                line.append(CSV_SEPARATOR);
                line.append(record.getAxisValueY());
                bw.write(line.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("NUMBER FORMAT EXCEPTION");
            e.printStackTrace();
        }
    }

}
