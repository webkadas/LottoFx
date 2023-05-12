package model;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@lombok.Data
public class Huzas {
    LocalDate date;
    Integer year, week;
    Integer[] numbers = new Integer[5];
    Integer[] talalatDarabszam = new Integer[5];
    String[] talalatNyeremeny = new String[5];


    public Huzas() {
    }
    public void setTalalat(int hova, int mit){
        this.talalatDarabszam[hova] = mit;
    }
    public void setNyeremeny(int hova, String mit){
        this.talalatNyeremeny[hova] = mit;
    }
    public void setNumbers(int hova, int mit){
        this.numbers[hova] = mit;
    }
    public String numbersKiir(){
        String result="";
        for (int i = 0; i <this.numbers.length ; i++) {
            if (i<this.numbers.length-1) result+=this.numbers[i]+", "; else result+=this.numbers[i]+" ";
        }
        return result;
    }

    public void kiirTalalat(){
        for (Integer x:this.talalatDarabszam){
            System.out.print(x+",");
            System.out.println();
        }
    }

}
