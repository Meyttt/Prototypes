import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.*;

public class Parser {
   static int expectedCellCount = 4;
    /**
     *
     * @param file
     * @return
     */
    public static ArrayList<Person> parse(File file){
        ArrayList<Person> personList= new ArrayList<Person>();
        Workbook workbook = createWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        Person person = null;
        while (it.hasNext()){
            Row row = it.next();
            if(row.getPhysicalNumberOfCells()==expectedCellCount){
                if(person!=null)personList.add(person);
                person = readMainRow(row);
            }else{
                person=readSecondaryRow(row,person);
            }
        }
        personList.add(person);
        return personList;

    }

    public static Workbook createWorkBook(File file){
        InputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(file);
            workbook = new HSSFWorkbook(is);
        }catch(OfficeXmlFileException e){
            try{
                is = new FileInputStream(file);
                workbook= new XSSFWorkbook(is);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static Person readMainRow(Row mainRow){
        Person person = new Person();
        person.setFio(mainRow.getCell(mainRow.getFirstCellNum()).getStringCellValue());
        Vector<Date> record = new Vector<Date>(2);
        record.add(mainRow.getCell(mainRow.getFirstCellNum()+1).getDateCellValue());
        record.add(mainRow.getCell(mainRow.getFirstCellNum()+2).getDateCellValue());
        person.getRecords().add(record);
        person.setDescriptions(mainRow.getCell(mainRow.getFirstCellNum()+3).getStringCellValue());
        return person;
    }

    private static Person readSecondaryRow(Row row, Person person){
        Vector<Date> dates = new Vector<Date>();
        dates.add(row.getCell(row.getFirstCellNum()).getDateCellValue());
        dates.add(row.getCell(row.getFirstCellNum()+1).getDateCellValue());
        person.getRecords().add(dates);
        return person;
    }

    public static void main(String[] args) {
        File file = new File("testData/skud.xlsx");
        ArrayList<Person> persons = parse(file);
        persons.stream().forEach(person-> System.out.println(person));
    }

}
