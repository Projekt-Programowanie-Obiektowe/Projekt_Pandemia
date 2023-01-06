package projekt_pandemia;
import java.util.ArrayList;
import java.util.Random;

public class populacja{

    int l = 100; // liczba ludzi
    int z = 1; // liczba zwierzat

    ArrayList<czlowiek> lista_ludzi = new ArrayList<czlowiek>();  //lista ktora zmienia swoja dlugosc
    ArrayList<zwierze> lista_zwierzat = new ArrayList<zwierze>();


    populacja(){


        for(int i = 0; i < l; i++){

            lista_ludzi.add(new czlowiek());
        }

        for(int i = 0; i < z; i++) {

            lista_zwierzat.add(new zwierze());
        }


        Random r = new Random();   // tutaj losujemy jedna chora osobe
        int a = r.nextInt(l);

        czlowiek pierwszy = new czlowiek();
        pierwszy = lista_ludzi.get(a);
        pierwszy.zmiana_stanu(1);
        lista_ludzi.set(a, pierwszy);


        Random r2 = new Random();   // tutaj losujemy jednego chorego zwierzaczka
        int b = r.nextInt(z);

        zwierze pierwsze = new zwierze();
        pierwsze = lista_zwierzat.get(b);
        pierwsze.zmiana_stanu(1);
        lista_zwierzat.set(b, pierwsze);

    }


    populacja(int ludzie, int zwierzeta){

        l = ludzie;
        z = zwierzeta;


        for(int i = 0; i < l; i++) {

            lista_ludzi.add(new czlowiek());
        }

        for(int j = 0; j < z; j++) {

            lista_zwierzat.add(new zwierze());
        }

        Random r = new Random();
        int a = r.nextInt(l);


        czlowiek pierwszy = new czlowiek();
        pierwszy = lista_ludzi.get(a);
        pierwszy.zmiana_stanu(1);
        lista_ludzi.set(a, pierwszy);


        Random r2 = new Random();
        int b = r2.nextInt(z);

        zwierze pierwsze = new zwierze();
        pierwsze = lista_zwierzat.get(b);
        pierwsze.zmiana_stanu(1);
        lista_zwierzat.set(b, pierwsze);

    }


    void przemieszczenieludzi(){

        czlowiek obiekt = new czlowiek();

        for (int i = 0; i < l; i++) {

            obiekt = lista_ludzi.get(i);
            obiekt.przemieszczenie();

            lista_ludzi.set(i, obiekt);}}

    void przemieszczeniezwierzat(){

        zwierze obiekt = new zwierze();

        for (int i = 0; i < z; i++) {

            obiekt = lista_zwierzat.get(i);
            obiekt.przemieszczenie();

            lista_zwierzat.set(i, obiekt);}}




    czlowiek losowy_czlowiek(){

        Random r = new Random();
        int indeks = r.nextInt(l);

        czlowiek obiekt = new czlowiek();
        obiekt = lista_ludzi.get(indeks);

        return obiekt;
    }



        zwierze losowe_zwierze() {

            Random r1 = new Random();
            int indeks1 = r1.nextInt(z);

            zwierze obiekt1 = new zwierze();
            obiekt1 = lista_zwierzat.get(indeks1);

            return obiekt1;
    }

}
