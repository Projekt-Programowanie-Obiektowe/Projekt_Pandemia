package projekt_pandemia;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;


public class populacja_main {
    public static void main(String[] args){


        int liczba_symulacji = 2;


        double p1 = 0.75; //prawdopodobienstwo zarazenia czlowieka od czlowieka (odleglosc)
        double p2 = 0.8; //prawdopodobienstwo zarazenia zwierzecia od zwierzecia (odleglosc)
        double p3 = 0.25; //prawdopobienstwo ze czlowiek umie wybrac zdrowe zwierze
        boolean K = true;  //kwarantanna
        int odleglosc = 10;
        int odleglosc_zwierzeta = 15;

        int ile_ludzi = 200;
        int ile_zwierzat = 3000;

        int iteracje = 700;
        int sukces = 0;


        populacja test = new populacja(ile_ludzi,ile_zwierzat);

        int var;  //dzieki tej zmiennej mozemy wyjsc z ogolnej petli
        int przebieg;  //liczba iteracji ktore sie wykonuje


        float suma_dlugosc_trwania = 0;  //koniec epidemii
        float suma_nr_iteracji_pik = 0;   //suma iteracji gdzie najwiecej chorych
        int nr_iteracji_pik;
        int pik_chorych;    //najwieksza liczba chorych w symulacji

        test.kwarantanna = K;




        for(int s = 0; s < liczba_symulacji; s++) {

            var = 0;
            przebieg = 0;
            sukces = 0;
            nr_iteracji_pik = 0;
            pik_chorych = 0;   //dla kazdej nowej symulacji trzeba wyzerowac

            test = new populacja(ile_ludzi,ile_zwierzat);


            while (var >= 0) {

                //ponizej w kazdej iteracji sprawdzam odleglosc miedzy kazdymi dwoma ludzmi, jak jeden chory - drugi sie zaraza
                for (int j = 0; j < test.l; j++) {
                    for (int k = 0; k < test.l; k++) {


                        czlowiek obiekt_j = test.lista_ludzi.get(j);
                        czlowiek obiekt_k = test.lista_ludzi.get(k);        //tworzymy te obiekty zeby moc operowac nimi i listami w petlach


                        if (obiekt_j.odleglosc(obiekt_k) < odleglosc) {


                            if (obiekt_j.stan == 1) {


                                Random r = new Random();
                                double a = r.nextDouble(); // losowa liczba od 0 do 1

                                if (a <= p1) {  //z prawdopodobienstwem p1

                                    if (obiekt_k.stan != 1) {

                                        obiekt_k.zmiana_stanu(1);
                                        obiekt_k.rokowania_choroby();

                                        test.lista_ludzi.set(k, obiekt_k);

                                        test.liczba_ludzi_chorych++;
                                    }
                                }
                            } else if (obiekt_k.stan == 1) {

                                Random r = new Random();
                                double a = r.nextDouble();

                                if (a <= p1) {

                                    if (obiekt_j.stan != 1) {
                                        obiekt_j.zmiana_stanu(1);
                                        obiekt_j.rokowania_choroby();

                                        test.lista_ludzi.set(j, obiekt_j);

                                        test.liczba_ludzi_chorych++;
                                    }
                                }
                            }
                        }
                    }
                }

                //ponizej w kazdej iteracji sprawdzam odleglosc miedzy kazdymi dwoma zwierzetami, jak jeden chory - drugi sie zaraza
                for (int z = 0; z < test.z; z++) {
                    for (int w = 0; w < test.z; w++) {


                        zwierze obiekt_z = test.lista_zwierzat.get(z);
                        zwierze obiekt_w = test.lista_zwierzat.get(w);


                        if (obiekt_z.odleglosc(obiekt_w) < odleglosc_zwierzeta) {


                            if (obiekt_z.stan == 1) {

                                Random r = new Random();
                                double a = r.nextDouble();

                                if (a <= p2) {  //z prawdopodobienstwem p2

                                    if (obiekt_w.stan != 1) {
                                        obiekt_w.zmiana_stanu(1);
                                        obiekt_w.rokowania_choroby();

                                        test.lista_zwierzat.set(w, obiekt_w);

                                        test.liczba_zwierzat_chorych++;
                                    }
                                }

                            } else if (obiekt_w.stan == 1) {

                                Random r = new Random();
                                double a = r.nextDouble();

                                if (a <= p2) {

                                    if (obiekt_z.stan != 1) {
                                        obiekt_z.zmiana_stanu(1);
                                        obiekt_z.rokowania_choroby();

                                        test.lista_zwierzat.set(z, obiekt_z);

                                        test.liczba_zwierzat_chorych++;
                                    }
                                }
                            }
                        }
                    }
                }


                int zz = test.l / 5; // to samo co floor(test.l/5)

                if (test.l > 0 && test.l < 5) zz = 1;

                for (int j = 0; j < zz; j++) {   //zjadanie zwierzat przez ludzi

                    if (test.z > 0) {  //zeby mialo sens

                        int losowy_czlowiek_indeks = test.losowy_czlowiek_indeks();
                        int losowe_zwierze_indeks = test.losowe_zwierze_indeks();


                        Random r = new Random();
                        double a = r.nextDouble();

                        if (a <= p3) {  //prawd p3 ze czlowiek bedzie umial wybrac zdrowe zwierze do zjedzenia

                            int q = 0;
                            while (q < test.z) {
                                if (test.lista_zwierzat.get(q).stan != 1) {    //pierwsze niechore zwierze
                                    test.usun_zwierze(q);
                                    q = test.z;
                                }
                                q++;

                            }


                        } else {  // nie umie wybrac zdrowego, wiec zjada od razu


                            if (test.lista_zwierzat.get(losowe_zwierze_indeks).stan == 1) {  // jak zwierze chore, to sie zaraza

                                czlowiek obiekt = test.lista_ludzi.get(losowy_czlowiek_indeks);

                                if (obiekt.stan != 1) {

                                    obiekt.zmiana_stanu(1);
                                    obiekt.rokowania_choroby();

                                    test.lista_ludzi.set(losowy_czlowiek_indeks, obiekt);

                                    test.liczba_ludzi_chorych++;
                                }

                                test.liczba_zwierzat_chorych--;
                            }

                            test.usun_zwierze(losowe_zwierze_indeks);  // usuwamy zwierze o wylosowanym indeksie z listy zwierzat
                        }
                    }
                }


                for (int i = 0; i < test.l; i++) {   //choroba, zwiekszanie czasu lub wyzdrowienie/smierc

                    czlowiek obiekt = test.lista_ludzi.get(i);

                    if (obiekt.stan == 1) {  //jesli czlowiek chory to wchodzi dalej

                        if (obiekt.czas_choroby >= obiekt.przewidywany_czas_choroby) {  //minal czas choroby, wiec albo wyzdrowieje albo umrze

                            double a = new Random().nextDouble();
                            if (a <= obiekt.prawd_zgonu) {

                                obiekt.zmiana_stanu(3);

                                test.usun_czlowieka(i);
                                test.liczba_ludzi_chorych--;

                            } else {

                                obiekt.zmiana_stanu(2);
                                obiekt.czas_choroby = 0;   //zeruje czas chorowania
                                test.lista_ludzi.set(i, obiekt);

                                test.liczba_ludzi_chorych--;
                            }

                        } else {
                            obiekt.czas_choroby++;
                            test.lista_ludzi.set(i, obiekt);
                        }
                    }
                }


                for (int i = 0; i < test.z; i++) {  // to samo tylko u zwierzat

                    zwierze obiekt2 = test.lista_zwierzat.get(i);

                    if (obiekt2.stan == 1) {

                        if (obiekt2.czas_choroby >= obiekt2.przewidywany_czas_choroby) {  //minal czas choroby, wiec albo wyzdrowieje albo umrze

                            double a = new Random().nextDouble();
                            if (a <= obiekt2.prawd_zgonu) {

                                obiekt2.zmiana_stanu(3);

                                test.usun_zwierze(i);
                                test.liczba_zwierzat_chorych--;

                            } else {

                                obiekt2.zmiana_stanu(2);
                                obiekt2.czas_choroby = 0;
                                test.lista_zwierzat.set(i, obiekt2);

                                test.liczba_zwierzat_chorych--;
                            }

                        } else {
                            obiekt2.czas_choroby++;
                            test.lista_zwierzat.set(i, obiekt2);
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


                if (test.l > 0 && test.liczba_ludzi_chorych == 0 && test.liczba_zwierzat_chorych == 0) {  //wszyscy zdrowi i sa ludzie
                    sukces = 1;
                    var = -1;
                } else if (test.l == 0) var = -1;   // nie ma juz zadnego czlowieka
                else if (przebieg == iteracje) var = -1; //wykonano wszystkie iteracje



                if(test.liczba_ludzi_chorych > pik_chorych) {  //gdy liczba chorych jest wieksza niz aktualny pik, to zamien

                    pik_chorych = test.liczba_ludzi_chorych;
                    nr_iteracji_pik = przebieg;  //zapamietaj dla ktorego nr iteracji byl pik
                }



        //        System.out.print("Nr iteracji: " + przebieg + ", chorzy ludzie/wszyscy ludzie: " + test.liczba_ludzi_chorych + "/" + test.l +
        //                ", chore zwierzeta/wszystkie zwierzeta: " + test.liczba_zwierzat_chorych + "/" + test.z);


    ///*

                System.out.print("Nr iteracji: " + przebieg + "  ");

                for(int m = 0; m < test.l; m++){
                    System.out.print(test.lista_ludzi.get(m).stan);
                }

    //*/

                System.out.println();

            }


            System.out.println("Liczba iteracji: " + przebieg + ", sukces: " + sukces);


            suma_dlugosc_trwania += przebieg;
            suma_nr_iteracji_pik += nr_iteracji_pik;



            System.out.println();
            System.out.println("******************************************************************************************************");
            System.out.println();

        }



        float sredni_nr_iteracji_pik = suma_nr_iteracji_pik / liczba_symulacji;
        float srednia_dlugosc_trwania = suma_dlugosc_trwania / liczba_symulacji;


        System.out.println("Średnia długość trwania epidemii z " + liczba_symulacji + " symulacji to: " + srednia_dlugosc_trwania);
        System.out.println("Średnia liczba iteracji z " + liczba_symulacji + " symulacji w której wystąpił 'pik' zachorowań: " + sredni_nr_iteracji_pik);

    }
}
