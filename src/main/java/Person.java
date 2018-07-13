import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Person {
    private String fio;
    private ArrayList<Vector<Date>> records;
    private String descriptions;

    public Person() {
        this.fio=null;
        this.records = new ArrayList<>();
        this.descriptions=null;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public ArrayList<Vector<Date>> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Vector<Date>> records) {
        this.records = records;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Работник: "+ this.getFio()+"\r\n");
        stringBuilder.append("Информация о посещении: \r\n");
        for (Vector<Date> vector: records){
            stringBuilder.append("Прошел на территорию в: "+formatTime(vector.firstElement()) +", покинул территорию в: "+formatTime(vector.lastElement())+ " спустя "
                    + getTimeBetween(vector.firstElement(),vector.lastElement())+ "\r\n");
        }
        stringBuilder.append("Сотрудник провел на рабочем месте: "+getAllTime(this.records) +"\r\n"+
                "Примечание: "+this.descriptions+"\r\n\r\n");
        return stringBuilder.toString();
    }

    public static String getTimeBetween(Date date1, Date date2){
        return  getTimeBetween(date2.getTime() - date1.getTime());
    }
    public static String getTimeBetween(long difference){
        return  (difference)/(3600000) +" часов, "+ (difference)%(3600000)/600000 +" минут";
//        return new Date(date2.getTime() - date1.getTime());
    }
    public String getAllTime(ArrayList<Vector<Date>> dates){
        long sum = 0;
        for (Vector<Date> vector: dates){
            sum+=(vector.lastElement().getTime()-vector.firstElement().getTime());
        }
        return getTimeBetween(sum);
    }
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:SS");
        return simpleDateFormat.format(date);
    }
}
