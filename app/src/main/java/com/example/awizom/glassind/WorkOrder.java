package com.example.awizom.glassind;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.awizom.glassind.Model.DataWorkOrder;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkOrder extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory, btnSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    ArrayList<DataWorkOrder> uploadData;

    ListView lvInternalStorage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<>();

        //need to check the permissions
        checkFilePermissions();
        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if (lastDirectory.equals(adapterView.getItemAtPosition(i))) {
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                } else {
                    count++;
                    pathHistory.add(count, (String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                } else {
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count, System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

    }

    /**
     * reads the excel file columns then rows. Stores data as ExcelUploadData object
     *
     * @return
     */
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if (c > 10) {
                        // Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! " );
                        // toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    } else {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        if (value != null && value.trim().length() != 0)
                            sb.append(value + ", ");
                    }
                }
                sb.append("#");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            parseStringBuilder(sb);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage());
        }
    }

    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder) {
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split("#");
        int PINo=0, workOrderNo=0,Qty=0;
        String OrderDate="", PartyName="", Location="",GlassSpecificationColor="",GlassSpecificationBTD="",SizeIn="",SizeMm="",ActualSize="",Hole="",Cut="",Remark="";
        double GlassSpecificationThick=0, AreaInSQM=0;
        //Add to the ArrayList<XYValue> row by row
        for (int i = 0; i < rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try {
                if (i == 0) {
                    String workid = columns[0].split("/")[0];
                    String pid = columns[0].split("/")[1];
                    PINo = Integer.parseInt(pid.trim().split("-")[1]);
                    workOrderNo = Integer.parseInt(workid.trim().split("-")[1]);
                    String workorderdate = columns[3].split(":")[1];
                    OrderDate = workorderdate.trim();
                }
                if (i == 2) {
                    String partyname = columns[1].split("\n")[1];
                    String location = columns[2].split("\n")[0];
                    PartyName = partyname.trim();
                    Location = location.trim();
                }

                if (i >= 6) {

                    if(String.valueOf(columns[0]).trim().equals("Grand Total")) {
                        break;
                    }
                    else {
                        String[] glassSpecification = columns[1].split("\\s+");
                        //String glassSpecificationThick = columns[1].split(" ")[0];
                        //String glassSpecificationColor = columns[1].split(" ")[0];

                        GlassSpecificationThick = Double.parseDouble(glassSpecification[1].trim().split("M")[0]);
                        GlassSpecificationColor = String.valueOf(glassSpecification[2]);
                        GlassSpecificationBTD = String.valueOf(columns[2]);
                        SizeIn = String.valueOf(columns[3]);

                        ActualSize = String.valueOf(columns[4]);
                        Hole = String.valueOf(columns[5]);
                        Cut = String.valueOf(columns[6]);
                        Qty = (int) Math.round(Double.parseDouble(columns[7]));

                        AreaInSQM = Double.parseDouble(columns[8]);

                        //uploadData.add(new DataWorkOrder("", PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, SizeMm, ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark));

                        String cellInfo = "(WorkingDate,PartyName,Location,PINo,workOrderNo,GlassSpecificationThick,GlassSpecificationColor,GlassSpecificationBTD,SizeIn,SizeMm,ActualSize,Hole,Cut,Qty,AreaInSQM,OrderDate,GWaight,Remark): (" +
                                Location + "," + PartyName + "," + PINo + "," + workOrderNo + "," + GlassSpecificationThick + "," + GlassSpecificationColor + "," + GlassSpecificationBTD +
                                "," + SizeIn + "," + SizeMm + "," + ActualSize + "," + Hole + "," + Cut + "," + Qty + "," + AreaInSQM + "," + OrderDate  + "," + Remark + ")";

                        Toast.makeText(this, "ParseStringBuilder: Data from row: " + cellInfo, Toast.LENGTH_SHORT).show();

                    }

                }



               //Toast.makeText(this, "ParseStringBuilder: Data from row: " + cellInfo, Toast.LENGTH_SHORT).show();

                  // Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
               // uploadData.add(new DataWorkOrder("", PartyName, Location, PINo, workOrderNo, GlassSpecificationThick, GlassSpecificationColor, GlassSpecificationBTD, SizeIn, SizeMm, ActualSize, Hole, Cut, Qty, AreaInSQM, OrderDate, 0, Remark));

            } catch (NumberFormatException e) {

                 Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());
               // Toast.makeText(this, "parseStringBuilder: NumberFormatException: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        //printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for (int i = 0; i < uploadData.size(); i++) {


            String WorkingDate = uploadData.get(i).getWorkingDate();
            String PartyName = uploadData.get(i).getPartyName();
            String Location = uploadData.get(i).getLocation();
            int PINo = uploadData.get(i).getPINo();
            int workOrderNo = uploadData.get(i).getWorkOrderNo();
            double GlassSpecificationThick = uploadData.get(i).getGlassSpecificationThick();
            String GlassSpecificationColor = uploadData.get(i).getGlassSpecificationColor();
            String GlassSpecificationBTD = uploadData.get(i).getGlassSpecificationBTD();
            String SizeIn = uploadData.get(i).getSizeIn();
            String SizeMm = uploadData.get(i).getSizeMm();
            String ActualSize = uploadData.get(i).getActualSize();
            String Hole = uploadData.get(i).getHole();
            String Cut = uploadData.get(i).getCut();
            int Qty = uploadData.get(i).getQty();
            double AreaInSQM = uploadData.get(i).getAreaInSQM();
            String OrderDate = uploadData.get(i).getOrderDate();
            double GWaight = uploadData.get(i).getGWaight();
            String Remark = uploadData.get(i).getRemark();
            String GlassSpecification = uploadData.get(i).getGlassSpecificationBTD();
            //Log.d(TAG, "printDataToLog: (WorkingDate,PartyName,Location,PINo,workOrderNo,GlassSpecificationThick,GlassSpecificationColor,GlassSpecificationBTD,SizeIn,SizeMm,ActualSize,Hole,Cut,Qty,AreaInSQM,OrderDate,GWaight,Remark): (" +WorkingDate+","+Location+","+PartyName+","+PINo+","+workOrderNo+","+GlassSpecificationThick+","+GlassSpecificationColor+","+GlassSpecificationBTD+","+SizeIn+","+SizeMm+","+ActualSize+","+Hole+","+Cut+","+Qty+","+AreaInSQM+","+OrderDate+","+GWaight+","+Remark+")");
            Toast.makeText(this, "printDataToLog: (WorkingDate,PartyName,Location,PINo,workOrderNo,GlassSpecificationThick,GlassSpecificationColor,GlassSpecificationBTD,SizeIn,SizeMm,ActualSize,Hole,Cut,Qty,AreaInSQM,OrderDate,GWaight,Remark): (" + WorkingDate + "," + Location + "," + PartyName + "," + PINo + "," + workOrderNo + "," + GlassSpecificationThick + "," + GlassSpecificationColor + "," + GlassSpecificationBTD + "," + SizeIn + "," + SizeMm + "," + ActualSize + "," + Hole + "," + Cut + "," + Qty + "," + AreaInSQM + "," + OrderDate + "," + GWaight + "," + Remark + ")", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Returns the cell as a string from the excel file
     *
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");//retert
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = "" + numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage());
        }
        return value;
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try {
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            } else {
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++) {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        } catch (NullPointerException e) {
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
