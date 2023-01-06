package projekt_pandemia;
import java.io.*;
import java.lang.Thread;
import java.util.Random;


public class populacja_main {
    public static void main(String[] args){


        double p1 = 0.5; //prawdopodobienstwo zarazenia czlowieka
        double p3 = 0.25; //prawdopobienstwo ze czlowiek umie wybrac zdrowe zwierze

        int iteracje = 70;
        int odleglosc = 5;


        populacja test = new populacja(80,300);

        int var = 0;  //dzieki tej zmiennej mozemy wyjsc z ogolnej petli
        int przebieg = 0;  //liczba iteracji ktore sie wykonuje


        czlowiek obiekt_j = new czlowiek();
        czlowiek obiekt_k = new czlowiek();


        zwierze obiekt_z = new zwierze();
        zwierze obiekt_w = new zwierze();

        while(var>=0){

            //ponizej w kazdej iteracji sprawdzam odleglosc miedzy kazdymi dwoma ludzmi, jak jeden chory - drugi sie zaraza
            for(int j = 0; j < test.l; j++){
                for(int k = 0; k < test.l; k++){


                    obiekt_j = test.lista_ludzi.get(j);
                    obiekt_k = test.lista_ludzi.get(k);        //tworzymy te obiekty zeby moc operowac nimi i listami w petlach


                    if(obiekt_j.odleglosc(obiekt_k) < odleglosc){


                        if(obiekt_j.stan == 1){


                            Random r = new Random();
                            int a = r.nextInt(2);   //UZALEZNIC OD P1

                            if(a==0){  //z jakims prawd
                            obiekt_k.zmiana_stanu(1);
                            test.lista_ludzi.set(k, obiekt_k);
                            }
                        }


                        else if(obiekt_k.stan == 1){

                            Random r = new Random();
                            int a = r.nextInt(2);   //UZALEZNIC OD P1

                            if(a==0){
                            obiekt_j.zmiana_stanu(1);
                            test.lista_ludzi.set(j, obiekt_j);
                            }
                        }
                    }
                }
            }

            //ponizej w kazdej iteracji sprawdzam odleglosc miedzy kazdymi dwoma zwierzetami, jak jeden chory - drugi sie zaraza
            for(int z = 0; z < test.z; z++){
                for(int w = 0; w < test.z; w++){


                    obiekt_z = test.lista_zwierzat.get(z);
                    obiekt_w = test.lista_zwierzat.get(w);


                    if(obiekt_z.odleglosc(obiekt_w) < odleglosc) {


                        if (obiekt_z.stan == 1) {

                            Random r = new Random();
                            int a = r.nextInt(2);   //UZALEZNIC OD P1

                            if (a == 0) {  //z jakims prawd
                                obiekt_w.zmiana_stanu(1);
                                test.lista_zwierzat.set(w, obiekt_w);
                            }

                        } else if (obiekt_w.stan == 1) {

                            Random r = new Random();
                            int a = r.nextInt(2);   //UZALEZNIC OD P1

                            if (a == 0) {
                                obiekt_z.zmiana_stanu(1);
                                test.lista_zwierzat.set(z, obiekt_z);
                            }
                        }
                    }
                }
            }


            for(int j = 0; j < Math.floor(test.l / 5); j++) {   //zjadanie zwierzat przez ludzi

                if(test.z > 0){  //zeby mialo sens

                czlowiek losowy_czlowiek = test.losowy_czlowiek();
                zwierze losowe_zwierze = test.losowe_zwierze();


                Random r2 = new Random();
                int indeks2 = r2.nextInt(4);  //prawd 1/4 ze czlowiek bedzie umial wybrac zdrowego


                if (indeks2 == 0) {  //umie wybrac zdrowego

                   int q = 0;
                   while(q < test.z){
                       if(test.lista_zwierzat.get(q).stan == 0){    //
                           test.lista_zwierzat.remove(q);
                           test.z = test.z - 1;
                           q = test.z;}
                       q++;

                       }

                } else {  // nie umie wybrac zdrowego, wiec je od razu

                    for (int i = 0; i < test.z; i++) {   // usuwamy zwierze z listy

                        if (test.lista_zwierzat.get(i) == losowe_zwierze) {
                            test.lista_zwierzat.remove(i);
                            test.z = test.z - 1;
                        }
                    }


                    if (losowe_zwierze.stan == 1) {  // jak zwierze chore, to sie zaraza
                        losowy_czlowiek.zmiana_stanu(1);

                        for (int i = 0; i < test.l; i++) { // aktualizuje stan czlowieka

                            if (test.lista_ludzi.get(i) == losowy_czlowiek) {
                                test.lista_ludzi.set(i, losowy_czlowiek);
                            }
                        }
                    }
                }
            }
            }




            test.przemieszczenieludzi();  // -> po sprawdzeniu odleglosci robie przemieszczenie kazdego czlowieka
            test.przemieszczeniezwierzat();

            przebieg++;

            try {  //dzieki temu wyswietlaja sie powoli iteracje
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            if(przebieg == iteracje) var = -1;

          for(int s = 0; s < test.l; s++){
              System.out.print(test.lista_ludzi.get(s).stan);
         }



        //    for(int s = 0; s < test.z; s++){
        //        System.out.print(test.lista_zwierzat.get(s).stan);
        //   }


            System.out.println();

        }

    }
}
