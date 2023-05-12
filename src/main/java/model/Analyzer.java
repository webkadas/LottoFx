package model;

import java.util.*;

public class Analyzer {

    Map<Integer, Integer>  gyakorisag = new HashMap<>();

    public Analyzer(List<Huzas> huzasok){
        for (Huzas x:huzasok){   // Iterálás a HUZASOK-on
            for (Integer num:x.numbers){  // Iteralas a Huzasok huzott számain
                if (this.gyakorisag.containsKey(num)){
                    this.gyakorisag.put(num, this.gyakorisag.get(num) + 1);
                } else {
                    this.gyakorisag.put(num,1);
                }
            }
        }
    }


    public List<Map.Entry<Integer, Integer>> sortedListByKey(){   // VALUE szerint növekvő sorrend
        List<Map.Entry<Integer, Integer>> nlist = new ArrayList<>(this.gyakorisag.entrySet());
        nlist.sort(Map.Entry.comparingByKey());
        return nlist;
    }
    public List<Map.Entry<Integer, Integer>> sortedListByKeyRev(){   // VALUE szerint csökkenő sorrend
        List<Map.Entry<Integer, Integer>> nlist = new ArrayList<>(this.gyakorisag.entrySet());
        nlist.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
        return nlist;
    }
    public List<Map.Entry<Integer, Integer>> sortedListByValue(){   // VALUE szerint csökkenő sorrend
        List<Map.Entry<Integer, Integer>> nlist = new ArrayList<>(this.gyakorisag.entrySet());
        nlist.sort(Map.Entry.comparingByValue());
        return nlist;
    }

    public List<Map.Entry<Integer, Integer>> sortedListByValueRev(){   // VALUE szerint csökkenő sorrend
        List<Map.Entry<Integer, Integer>> nlist = new ArrayList<>(this.gyakorisag.entrySet());
        nlist.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return nlist;
    }
}
