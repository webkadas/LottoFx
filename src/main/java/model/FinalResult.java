package model;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@lombok.Data
public class FinalResult{


    List<Huzas> talalatok0 = new ArrayList<>();
    List<Huzas> talalatok1 = new ArrayList<>();
    List<Huzas> talalatok2 = new ArrayList<>();
    List<Huzas> talalatok3 = new ArrayList<>();
    List<Huzas> talalatok4 = new ArrayList<>();
    List<Huzas> talalatok5 = new ArrayList<>();


public String numbers(Integer[] szamok){
    String result="";
    for (int i = 0; i < szamok.length; i++) {
        result += " "+szamok[i];
    }
    return result;
}

}
