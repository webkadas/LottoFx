package com.example.lottofx;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Analyzer;
import model.FinalResult;
import model.Huzas;
import model.Popup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.time.LocalDate;
import java.util.*;

public class HelloController implements Initializable {
    List<Huzas> huzasok = new ArrayList<>();
    FinalResult fr;
    Popup popup = new Popup();

    @FXML
    ComboBox orderCombo,orderComboActual;
    @FXML
    ListView gyakorisagListView,gyakorisagListView1;
    @FXML
    private AnchorPane menuAnchor, generalAnchor, tippAnchor, actualAnchor, startPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox resultHBox;
    @FXML
    private RadioButton radio1Button, radio2Button, radio3Button, radio4Button, radio5Button;
    @FXML
    private Label  talalatDarabLabel, actualWeekLabel, generalWeekLabel, suggestLabel, startLabel,actOneLabel, actTwoLabel, actThreeLabel,actFourLabel, actFiveLabel;
    @FXML
    private VBox resultVbox,resultByTalalatVBox;

    @FXML
    private Button randomButton;

    @FXML
    private TextField textNum1,textNum2,textNum3,textNum4,textNum5;

    @FXML
    protected void getSelectedOrderActual(ActionEvent event){
        List<Huzas> actualHuzasok = new ArrayList<>();
        if (orderComboActual.getSelectionModel().getSelectedIndex()==0){
            gyakorisagListView1.getItems().clear();

            for (Huzas x:huzasok){  // az aktuális hét húzásait külön LIST-be teszi
                if (x.getWeek()==actualWeek) {
                    actualHuzasok.add(x);
                }

            }

            Analyzer gyakorisag = new Analyzer(actualHuzasok);
            for (Map.Entry<Integer, Integer> y:gyakorisag.sortedListByKey()){
                gyakorisagListView1.getItems().add("#"+y.getKey()+" - " + y.getValue()+" db.");
            }
        }
        if (orderComboActual.getSelectionModel().getSelectedIndex()==1){
            gyakorisagListView1.getItems().clear();

            for (Huzas x:huzasok){  // az aktuális hét húzásait külön LIST-be teszi
                if (x.getWeek()==actualWeek) {
                    actualHuzasok.add(x);
                }

            }

            Analyzer gyakorisag = new Analyzer(actualHuzasok);
            for (Map.Entry<Integer, Integer> y:gyakorisag.sortedListByValue()){
                gyakorisagListView1.getItems().add("#"+y.getKey()+" - " + y.getValue()+" db.");
            }
        }
        if (orderComboActual.getSelectionModel().getSelectedIndex()==2){
            gyakorisagListView1.getItems().clear();

            for (Huzas x:huzasok){  // az aktuális hét húzásait külön LIST-be teszi
                if (x.getWeek()==actualWeek) {
                    actualHuzasok.add(x);
                }

            }
            Analyzer gyakorisag = new Analyzer(actualHuzasok);
            for (Map.Entry<Integer, Integer> y:gyakorisag.sortedListByValueRev()){
                gyakorisagListView1.getItems().add("#"+y.getKey()+" - " + y.getValue()+" db.");
            }
        }

    }
    @FXML
    protected void getSelectedOrder(ActionEvent event){

        if (orderCombo.getSelectionModel().getSelectedIndex()==0){
            gyakorisagListView.getItems().clear();
            Analyzer gyakorisag = new Analyzer(huzasok);
            for (Map.Entry<Integer, Integer> x:gyakorisag.sortedListByKey()){
                gyakorisagListView.getItems().add(x);
            }
        }
        if (orderCombo.getSelectionModel().getSelectedIndex()==1){
            gyakorisagListView.getItems().clear();
            Analyzer gyakorisag = new Analyzer(huzasok);
            for (Map.Entry<Integer, Integer> x:gyakorisag.sortedListByKeyRev()){
                gyakorisagListView.getItems().add(x);
            }
        }
        if (orderCombo.getSelectionModel().getSelectedIndex()==2){
            gyakorisagListView.getItems().clear();
            Analyzer gyakorisag = new Analyzer(huzasok);
            for (Map.Entry<Integer, Integer> x:gyakorisag.sortedListByValue()){
                gyakorisagListView.getItems().add(x);
            }
        }
        if (orderCombo.getSelectionModel().getSelectedIndex()==3){
            gyakorisagListView.getItems().clear();
            Analyzer gyakorisag = new Analyzer(huzasok);
            for (Map.Entry<Integer, Integer> x:gyakorisag.sortedListByValueRev()){
                gyakorisagListView.getItems().add(x);
            }
        }
    }
    @FXML
    protected void onSimpleButtonClick(){
        radio1Button.setVisible(false);
        radio2Button.setVisible(false);
        radio3Button.setVisible(false);
        radio4Button.setVisible(false);
        radio5Button.setVisible(false);
        Set<Integer> tippek = new HashSet<>();
        boolean sas = true;
        if (textNum1.getText().length()==0 || textNum2.getText().length()==0 || textNum3.getText().length()==0 || textNum4.getText().length()==0 || textNum5.getText().length()==0) {
            popup.setLabel("TÖLTS KI MINDEN MEZŐT","Hiányzó adat");
            popup.display();

            sas = false;
        }
        try {
            Integer temp = Integer.parseInt(textNum1.getText());
            if (temp.intValue()<1 ) {
                sas = false;
                popup.setLabel("Hibás formátum", "Csak számformátum megengedett");
                popup.display();
            }
        } catch (IllegalFormatException e){

        }
        if (sas) {

            tippek.add(Integer.parseInt(textNum1.getText()));
            tippek.add(Integer.parseInt(textNum2.getText()));
            tippek.add(Integer.parseInt(textNum3.getText()));
            tippek.add(Integer.parseInt(textNum4.getText()));
            tippek.add(Integer.parseInt(textNum5.getText()));
            if (tippek.size()!=5) {
                popup.setLabel("Nem lehetnek egyező számok","Egyező számok");
                popup.display();
            } else checkResult(tippek);
        }

    }

    private void checkResult(Set<Integer> tippek) { // HA MINDEN JÓ ITT JÖN AZ ÖSSZEVETÉS
        resultVbox.getChildren().clear();
        radio1Button.setSelected(false);
        radio2Button.setSelected(false);
        radio3Button.setSelected(false);
        radio4Button.setSelected(false);
        radio5Button.setSelected(false);
        List<Integer> tippek2 = new ArrayList<>(tippek);
        // resultVbox.getChildren().removeAll();
        fr = new FinalResult();
        int sas=0;
        for (Huzas x:huzasok){

            sas = 0;
            for (Integer y : x.getNumbers()){
                for (int i = 0; i < tippek2.size(); i++) {
                    if (y==tippek2.get(i)) {
                        sas++;
                        break;
                    }
                }
           }

            if (sas==0) fr.getTalalatok0().add(x);
            else if (sas==1) fr.getTalalatok1().add(x);
            else if (sas==2) fr.getTalalatok2().add(x);
            else if (sas==3) fr. getTalalatok3().add(x);
            else if (sas==4) fr.getTalalatok4().add(x); else fr.getTalalatok5().add(x);
        }

        visibleRadio(fr);



    }
    @FXML
    protected void resultFromRadio(ActionEvent e){
        Node node = (Node) e.getSource();
        if (node.getId().equals("radio1Button")){
            eredmenyKiir(fr.getTalalatok1());
        } else if (node.getId().equals("radio2Button")){
            eredmenyKiir(fr.getTalalatok2());
        } else if (node.getId().equals("radio3Button")){
            eredmenyKiir(fr.getTalalatok3());
        } else if (node.getId().equals("radio4Button")){
            eredmenyKiir(fr.getTalalatok4());
        } else {
            eredmenyKiir(fr.getTalalatok5());
        }
    }

    @FXML
    protected void randomNumber(){
        int nums[] = new int[5];
        int db=0;
        boolean exists;
        Random rng = new Random();
        while (db<5){
            exists = false;
            int rngTipp = rng.nextInt(90)+1;
            if (db==0) {
                nums[0] = rngTipp;
                db+=1;
            } else
            {
                for (int i = 0; i < db; i++) {
                    if (nums[i]==rngTipp){
                        exists = true;
                        break;
                    }
                }
                if (!exists){
                    nums[db] = rngTipp;
                    db+=1;
                }
            }

        }
        textNum1.setText(nums[0]+"");textNum2.setText(nums[1]+"");textNum3.setText(nums[2]+"");textNum4.setText(nums[3]+"");textNum5.setText(nums[4]+"");
    }
    private void eredmenyKiir(List<Huzas> talalatok) {
        resultVbox.getChildren().clear();
        talalatDarabLabel.setText("");
        talalatDarabLabel.setText(talalatok.size()+" találat");
        for (Huzas x:talalatok){
            TextField textDate = new TextField(x.getYear()+" - "+x.getWeek()+". hét:    "+x.numbersKiir());
            textDate.setEditable(false);
            resultVbox.getChildren().add(textDate);
        }
    }

    int actualWeek=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL urlPath = null;
        BufferedReader br = null;

        List<AnchorPane> anchorList = new ArrayList<>();
        anchorList.add(generalAnchor);
        anchorList.add(actualAnchor);
        anchorList.add(tippAnchor);
        anchorList.add(startPane);



        orderCombo.getItems().add("szám növekvő");
        orderCombo.getItems().add("szám csökkenő");
        orderCombo.getItems().add("előfodulás alapján növekvő");
        orderCombo.getItems().add("előfodulás alapján csökkenő");
        orderComboActual.getItems().add("szám szerint növekvő");
        orderComboActual.getItems().add("gyakoriság szerint növekvő");
        orderComboActual.getItems().add("gyakoriság szerint csökkenő");

        Menu mTippCheck = new Menu("Tipp ellenőrzése");
        Menu mNumAppear = new Menu("Számok gyakorisága..");

        MenuItem mNumAppearGeneral = new MenuItem("Általános");
        MenuItem mNumAppearActual = new MenuItem("Aktuális hét");
        MenuItem mTippCustom = new MenuItem("Saját / Random tippek");


        Calendar cal = Calendar.getInstance();

        actualWeek = cal.get(Calendar.WEEK_OF_YEAR);



        // create menuitems
       /* MenuItem m1 = new MenuItem("Tipp ellenrzése");
        MenuItem m2 = new MenuItem("menu item 2");
        MenuItem m3 = new MenuItem("menu item 3");

        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);
*/
        // create a menubar
        MenuBar mb = new MenuBar();
        mNumAppear.getItems().add(mNumAppearGeneral);
        mNumAppear.getItems().add(mNumAppearActual);
        mTippCheck.getItems().add(mTippCustom);


        // add menu to menubar
        mb.getMenus().add(mTippCheck);
        mb.getMenus().add(mNumAppear);
        menuAnchor.getChildren().add(mb);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {

                if (((MenuItem)e.getSource()).getText().equals("Általános")){
                    System.out.println("ÁLTALÁNOS");
                    for (AnchorPane x:anchorList){
                        if (x.getId().equals("generalAnchor")) x.setVisible(true); else x.setVisible(false);
                    }


                }
                if (((MenuItem)e.getSource()).getText().equals("Aktuális hét")){

                    for (AnchorPane x:anchorList){
                        if (x.getId().equals("actualAnchor")) x.setVisible(true); else x.setVisible(false);
                    }

                    List<Huzas> actualHuzasok = new ArrayList<>();
                    for (Huzas x:huzasok){  // az aktuális hét húzásait külön LIST-be teszi
                        if (x.getWeek()==actualWeek) {
                            actualHuzasok.add(x);
                        }

                    }
                    Analyzer gyakorisag = new Analyzer(actualHuzasok);
                    for (Map.Entry<Integer, Integer> y:gyakorisag.sortedListByKey()){
                        gyakorisagListView1.getItems().add("#"+y.getKey()+" - " + y.getValue()+" db.");

                    }
                    List<Integer> fiveRandomTipp = new ArrayList<>();
                    int gyakFigyelo=0, i=-1;


                            while (gyakFigyelo<5) {
                                i++;
                            if (i == 0) fiveRandomTipp.add(gyakorisag.sortedListByValueRev().get(i).getKey());
                            else {
                                if (gyakorisag.sortedListByValueRev().get(i).getValue() != gyakorisag.sortedListByValueRev().get(i - 1).getValue()) gyakFigyelo += 1;

                            }
                            if (gyakFigyelo<5) fiveRandomTipp.add(gyakorisag.sortedListByValueRev().get(i).getKey());

                        }

                    suggestLabel.setText("Javaslat a hétre: "+suggestForWeek(fiveRandomTipp));
                    /*Analyzer gyakorisag = new Analyzer(actualHuzasok);
                    for (Map.Entry<Integer, Integer> x:gyakorisag.sortedListByKey()){
                        gyakorisagListView.getItems().add(x);
                    }*/
                    actualWeekLabel.setText("Aktuális hét: "+actualWeek);


                }
                if (((MenuItem)e.getSource()).getText().equals("Saját tippek")){
                    System.out.println("EZ A");
                    for (AnchorPane x:anchorList){
                        if (x.getId().equals("tippAnchor")) x.setVisible(true); else x.setVisible(false);
                    }
                }
            }
        };

        mNumAppearGeneral.setOnAction(event);
        mNumAppearActual.setOnAction(event);
        mTippCustom.setOnAction(event);
        mNumAppearActual.setOnAction(event);
        //mNumAppearGeneral.setOnAction(anchorEvent);
        //mNumAppearActual.setOnAction(anchorEvent);

        try {
            urlPath = new URL("https://bet.szerencsejatek.hu/cmsfiles/otos.csv");
            URLConnection urlConn = urlPath.openConnection();
            InputStreamReader inputStream = new InputStreamReader(urlConn.getInputStream());
            br = new BufferedReader(inputStream);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String line = new String("");
        while (true){
            try {
                if (!((line = br.readLine())!=null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Huzas huzas = new Huzas();
            String[] darabok = line.split(";");

            if (darabok[0].length()==4) huzas.setYear(Integer.parseInt(darabok[0]));
            huzas.setWeek(Integer.parseInt(darabok[1]));
            if (darabok[2].length()>0) {
                String tep = darabok[2].substring(0,darabok[2].length()-1).replace(".","-");
                LocalDate date1 = LocalDate.parse(tep);
                huzas.setDate(date1);
            } else huzas.setDate(null);
            huzas.setTalalat(4, Integer.parseInt(darabok[3]));
            huzas.setNyeremeny(4, darabok[4]);

            huzas.setTalalat(3, Integer.parseInt(darabok[5]));
            huzas.setNyeremeny(3, darabok[6]);

            huzas.setTalalat(2, Integer.parseInt(darabok[7]));
            huzas.setNyeremeny(2, darabok[8]);

            huzas.setTalalat(1, Integer.parseInt(darabok[9]));
            huzas.setNyeremeny(1, darabok[10]);

            huzas.setNumbers(0,Integer.parseInt(darabok[11]));
            huzas.setNumbers(1,Integer.parseInt(darabok[12]));
            huzas.setNumbers(2,Integer.parseInt(darabok[13]));
            huzas.setNumbers(3,Integer.parseInt(darabok[14]));
            huzas.setNumbers(4,Integer.parseInt(darabok[15]));

//            System.out.println(Integer.parseInt(String.valueOf(darabok[0])));
            huzasok.add(huzas);

        }
        System.out.print("Méret: "+huzasok.size());
        startLabel.setText("Aktuális nyerőszámok ("+ actualWeek+". hét): ");
        actOneLabel.setText(huzasok.get(0).getNumbers()[0]+"");
        actTwoLabel.setText(huzasok.get(0).getNumbers()[1]+"");
        actThreeLabel.setText(huzasok.get(0).getNumbers()[2]+"");
        actFourLabel.setText(huzasok.get(0).getNumbers()[3]+"");
        actFiveLabel.setText(huzasok.get(0).getNumbers()[4]+"");
    }

    void visibleRadio(FinalResult fr){
        if (fr.getTalalatok1().size()>0) radio1Button.setVisible(true);
        if (fr.getTalalatok2().size()>0) radio2Button.setVisible(true);
        if (fr.getTalalatok3().size()>0) radio3Button.setVisible(true);
        if (fr.getTalalatok4().size()>0) radio4Button.setVisible(true);
        if (fr.getTalalatok5().size()>0) radio5Button.setVisible(true);
    }
    private String suggestForWeek(List<Integer> numbers){
        String result="";
        Random rand = new Random();
        int rng = 0;
        for (int i = 0; i < 5; i++) {
            rng = rand.nextInt(numbers.size()-1);
            result += String.valueOf(numbers.get(rng))+" ";
            numbers.remove(rng);
        }


        return result;
    }
}