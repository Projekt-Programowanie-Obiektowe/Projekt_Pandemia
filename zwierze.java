package projekt_pandemia;
import java.util.Random;

public class zwierze {
    double wspx = new Random().nextDouble() * 1000;
    double wspy = new Random().nextDouble() * 1000;
    int stan = 0; // 0-zdrowy, 1-chory, 2-ozdrowieniec, 3-niezywy


    zwierze(){}

    void wyswietl(){
        System.out.println(wspx + ", " + wspy);
    }

    void przemieszczenie(){

        wspx = new Random().nextDouble() * 100;
        wspy = new Random().nextDouble() * 100;
    }


    double odleglosc(zwierze z){

        double d, pom1, pom2;
        pom1 = (wspx - z.wspx) * (wspx - z.wspx);
        pom2 = (wspy - z.wspy) * (wspy - z.wspy);

        d = Math.sqrt(pom1 + pom2);

        return d;
    }

    void zmiana_stanu(int s){
        stan = s;
    }



}
