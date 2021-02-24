package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static int m = 0;
    private static int n = 0;

    private static boolean little_helper_of_judgment(char[][]array,Young_Islands current, int dir){
        int j;
        switch(dir){
            //1 dla przeszukiwania do gory
            case 1:
                for (j = current.getX() - 1; j >= 0 ; --j)
                    if (array[j][current.getY()] == 'o')
                        return true;
                return false;
            //2 do przeszukiwania do dolu
            case 2:
                for (j = current.getX() + 1; j < m; ++j)
                    if (array[j][current.getY()] == 'o')
                        return true;
                return false;
            //3 do przeszukiwania w lewo
            case 3:
                for(j = current.getY() - 1 ; j >= 0 ; --j)
                    if(array[current.getX()][j] == 'o')
                        return true;
                return false;
            //4 do przeszukiwania w prawo
            case 4:
                for(j = current.getY() + 1 ; j < n ; ++j)
                    if(array[current.getX()][j] == 'o')
                        return true;
                return false;
        }
        return false;
    }
    //Dla danej funkcji
    //pesymistyczny przypadek bedzie wtedy, gdy sprawdzimy 3 strony mapy, a w czwartej najdluzszej nie bedzie rzeki
    private static boolean loop_of_judgment(char [][] array, Young_Islands current){
        //jezeli do dolu jest blizej niz do gory
        boolean [] check = new boolean[4];
        int left = current.getY();
        int right = n - current.getY();
        int up = current.getX();
        int down = m - current.getX();
        LinkedList<Integer> temp_array_of_sides = new LinkedList<>();
        temp_array_of_sides.add(left);
        temp_array_of_sides.add(down);
        temp_array_of_sides.add(right);
        temp_array_of_sides.add(up);
        Collections.sort(temp_array_of_sides);
        for(int i : temp_array_of_sides){
            if(i == up && !check[0]) {
                if(!little_helper_of_judgment(array, current, 1))
                    return false;
                check[0] = true;
            }
            else if(i == down && !check[1]) {
                if(!little_helper_of_judgment(array, current, 2))
                    return false;
                check[1] = true;
            }
            else if(i == left && !check[2]) {
                if(!little_helper_of_judgment(array, current, 3))
                    return false;
                check[2] = true;
            }
            else if(i == right && !check[3]) {
                if(!little_helper_of_judgment(array, current, 4))
                    return false;
                check[3] = true;
            }
        }
        return true;
    }
    private static int length_of_lil_rivers(char[][] array,int[][] boolarray, Lil_Rivers current) {
        boolarray[current.getX()][current.getY()] = 1;
        boolean[] check = new boolean[4];
        //gora
        if (current.getX() + 1 < m)
            if (array[current.getX() + 1][current.getY()] == 'u' && boolarray[current.getX()+1][current.getY()] == 0 )
                check[0] = true;
        //dol
        if (current.getX() - 1 >= 0)
            if (array[current.getX() - 1][current.getY()] == 'u' && boolarray[current.getX()-1][current.getY()] == 0)
                check[1] = true;
        //prawa
        if(current.getY() + 1 < n)
            if(array[current.getX()][current.getY() + 1] == 'u' && boolarray[current.getX()][current.getY()+1] == 0)
                check[2] = true;
        //lewa
        if(current.getY()  - 1 >= 0)
            if(array[current.getX()][current.getY() - 1] == 'u' && boolarray[current.getX()][current.getY()-1] == 0)
                check[3] = true;
        int a = 0, b = 0, c = 0, d = 0;
        if(check[0])
            a = length_of_lil_rivers(array, boolarray, new Lil_Rivers(current.getX() + 1, current.getY()));
        if(check[1])
            b = length_of_lil_rivers(array, boolarray, new Lil_Rivers(current.getX() - 1, current.getY()));
        if(check[2])
            c = length_of_lil_rivers(array, boolarray, new Lil_Rivers(current.getX(), current.getY() + 1));
        if(check[3])
            d = length_of_lil_rivers(array, boolarray, new Lil_Rivers(current.getX(), current.getY() - 1));
        /*
        if(check[0]) {
            if (check[1]) {
                if (check[2])
                    return Math.max(Math.max(a, b), c) + 1;
                if (check[3])
                    return Math.max(Math.max(a, b), d) + 1;
                return Math.max(a, b) + 1;
            }
            if (check[2]) {
                if (check[3])
                    return Math.max(Math.max(a, c), d) + 1;
                return Math.max(a, c) + 1;
            }
            if(check[3])
                return Math.max(a,d) + 1;
            return a + 1;
        }
        if(check[1]){
            if(check[2]){
                if(check[3])
                    return Math.max(Math.max(b,c),d) + 1;
                return Math.max(b,c) + 1;
            }
            if(check[3])
                return Math.max(b,d) + 1;
            return b + 1;
        }
        if(check[2]){
            if(check[3])
                return Math.max(c,d) + 1;
            return c + 1;
        }
        if(check[3])
            return d + 1;
        return 1;

         */
        return Math.max(Math.max(a, b), Math.max(c, d)) + 1;
    }
    //zwraca wielkosc wysp. Zwroci -1, jezeli nalezy do ladu stalego odcinek
    public static int size_of_young_islands(char[][] array,int[][] boolarray, Young_Islands current){
        int[] help_me_x = new int []{-1, -1, -1,  0, 0,  1, 1, 1};
        int[] help_me_y = new int []{-1, 0, 1, -1, 1, -1, 0, 1};
        int count = 1;
        for(int i = 0; i<8;++i){
            int tempx = current.getX() + help_me_x[i];
            int tempy = current.getY() + help_me_y[i];
            if(tempx >= m || tempx < 0 || tempy >= n || tempy < 0) {
                boolarray[current.getX()][current.getY()] = -1;
                return -1;
            }
            if(boolarray[tempx][tempy] == -1) {
                boolarray[current.getX()][current.getY()] = -1;
                return -1;
            }
            if(array[tempx][tempy] == 'x' && boolarray[tempx][tempy] == 0) {
                //na poczatku byl problem z zaznaczaniem, wiec to na wszelki
                boolarray[current.getX()][current.getY()] = 1;
                boolarray[tempx][tempy] = 1;
                int temporary_value = size_of_young_islands(array, boolarray, new Young_Islands(tempx, tempy));
                if(temporary_value == -1) {
                    boolarray[current.getX()][current.getY()] = -1;
                    return -1;
                }
                else
                    count += temporary_value;
            }
        }
        return count;
    }
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.nanoTime();
        File file = new File("C:\\Users\\matt3\\Desktop\\testy_ASD\\PS2 notki\\duzy1.txt");
        Scanner in = new Scanner(file);
        //wielkosc mapy wzdloz i wszerz
        m = in.nextInt();
        n = in.nextInt();
        //lista rzek
        LinkedList<Lil_Rivers> list_of_rivers = new LinkedList<>();
        //lista poczatkow rzek
        LinkedList<Lil_Rivers> list_of_origins_of_rivers = new LinkedList<>();
        //lista jeziorek
        LinkedList<Still_water> list_of_still_water_positions = new LinkedList<>();
        LinkedList<Young_Islands> list_of_land_nearby_water = new LinkedList<>();
        char[][] array = new char[m][n];
        //************************
        //Zlozonosc:1
        //T(m*n) Θ(n)
        for(int i = 0 ; i < m ; ++i){
            String temp = in.next();
            for(int j =  0 ; j < n ; ++j) {
                array[i][j] = temp.charAt(j);
                //jezeli to rzeka, dodanie jej do listy rzek(z tej listy beda wylaniane ujscia rzek do wody stalej)
                if (temp.charAt(j) == 'u')
                    list_of_rivers.add(new Lil_Rivers(i, j));
                if(temp.charAt(j) == 'o')
                    list_of_still_water_positions.add(new Still_water(i,j));
            }
        }
        in.close();
        //algorytm do najdluzszej rzeki
        //wyszukiwanie poczatkow rzek w kazdym z 4 kierunkow
        //************************
        //Zlozonosc:2
        //zalezy od ilosci rzek. W najgorszym wypadku tutaj wszystkie pixele moga okazac sie rzekami i sprawdzimy nadaremno
        //T(ilosc rzek) -  Θ(n) - tylko dodajemy do tablicy z ujsciami
        for(Lil_Rivers temp_river: list_of_rivers) {
            //prawo
            if (temp_river.getY() + 1 < n)
                if (array[temp_river.getX()][temp_river.getY() + 1] == 'o') {
                    list_of_origins_of_rivers.add(temp_river);
                    continue;
                }
            //dol
            if (temp_river.getX() + 1 < m)
                if (array[temp_river.getX() + 1][temp_river.getY()] == 'o') {
                    list_of_origins_of_rivers.add(temp_river);
                    continue;
                }
            //lewo
            if (temp_river.getY() - 1 >= 0)
                if (array[temp_river.getX()][temp_river.getY() - 1] == 'o') {
                    list_of_origins_of_rivers.add(temp_river);
                    continue;
                }
            //gora
            if (temp_river.getX() - 1 >= 0)
                if (array[temp_river.getX() - 1][temp_river.getY()] == 'o'){
                    list_of_origins_of_rivers.add(temp_river);
                }
            }
        //wyliczanie dlugosci rzek od rozpoczecia rzeki
        //tablica uzywana do okreslenia zajetosci
        //0 oznacza nieuzyte miejsce, 1 uzyte, a -1 (dla ladu) staly lad - niemozliwosc utworzenia wyspy
        int[][] array_of_used = new int[m][n];
        //zmienna tymczasowa a oraz max do wiadomo czego
        int a;
        int max = 0;
        //************************
        //Zlozonosc:3
        // kazde ujscie oraz kazdy pixel w funkcji sprawdzamy tylko raz, wiec wszystko jest uzaleznione od ilosci rzek na mapie
        //pesymistycznie:beda ciagle rozwidlenia/dlugosc rzek bedzie taka sama dla calej mapy i bedziemy porownywac te same wartosci
        //T(ilosc ujsc*(ilosc_pikseli rzek-ilosc ujsc))
        for(Lil_Rivers temp_river: list_of_origins_of_rivers) {
            a = length_of_lil_rivers(array, array_of_used, temp_river);
            if(a > max)
                max = a;
        }


        //******************************
        //algorytm do najwiekszej wyspy
        //******************************

        //tablice pomocnicze
        //dwie tablice oznaczajace kierunek patrzenia
        int[] help_me_x = new int []{-1, -1, -1,  0, 0,  1, 1, 1};
        int[] help_me_y = new int []{-1, 0, 1, -1, 1, -1, 0, 1};

        //wrzucanie do listy wysp blisko wody
        if(list_of_still_water_positions.size() != 0) {
            //tymczasowa mapa dla sprawdzenia, czy dodac dany lad do listy
            boolean[][] temporary_boolean_help = new boolean[m][n];
            //************************
            //Zlozonosc:4
            //pesymistycznie:
            //T(ilosc_pikseli_stalej_wody*8)
            for (Still_water temp : list_of_still_water_positions) {
                for (int i = 0; i < 8; ++i) {
                    int tempx = temp.getX() + help_me_x[i];
                    int tempy = temp.getY() + help_me_y[i];
                    if ((tempx >= 0) && (tempx < m) && (tempy < n) && (tempy >= 0))
                        if (array[tempx][tempy] == 'x' && !temporary_boolean_help[tempx][tempy]) {
                            list_of_land_nearby_water.add(new Young_Islands(tempx, tempy));
                            temporary_boolean_help[tempx][tempy] = true;
                        }
                }
            }
        }
        //wybranie z listy stalego ladu wielkosci wysp
        int maximum_of_YOUNG = 0;
        //************************
        //Zlozonosc:5
        //pesymistycznie: przejdzie przez wszystkie petle i wejdzie do funkcji rekurencyjnej. Dla każdego rozpoczęcia będzie
        //T(ilosc_ladu_przy_stalej wodzie*(8+(m-1)+(n-1)+(ilosc_ladu_przy_oceanie+ilosc_pikseli_wysp bez dostepu do wody)) Θ(n)
        //T(ilosc_ladu_przy_stalej wodzie*(8), jezeli
        for(Young_Islands temp: list_of_land_nearby_water){
            boolean not_ready = false;
            for(int i = 0; i<8 ;++i) {
                int tempx = temp.getX() + help_me_x[i];
                int tempy = temp.getY() + help_me_y[i];
                //czy poza krancem
                if (tempx >= m || tempx < 0 || tempy >= n || tempy < 0) {
                    array_of_used[temp.getX()][temp.getY()] = -1;
                    not_ready = true;
                    break;
                }
                //czy ten lad nalezy do ladu stalego
                if (array_of_used[tempx][tempy] == -1) {
                    array_of_used[temp.getX()][temp.getY()] = -1;
                    not_ready = true;
                    break;
                }
            }
            //czy byl juz odwiedzony i czy spelnia warunki wyspy
            if(!not_ready && array_of_used[temp.getX()][temp.getY()] == 0 )
            {
                if(!loop_of_judgment(array, temp)) {
                    array_of_used[temp.getX()][temp.getY()] = -1;
                    continue;
                }
                int temporary_value = size_of_young_islands(array, array_of_used, temp);
                if (temporary_value == -1) {
                    array_of_used[temp.getX()][temp.getY()] = -1;
                    continue;
                }
                else if (maximum_of_YOUNG < temporary_value)
                    maximum_of_YOUNG = temporary_value;
            }
        }
    System.out.println(maximum_of_YOUNG);
    System.out.println(max);
    System.out.println((System.nanoTime()-start)/1000000000.0);
    }
}
//pesymistycznie:
//w najgorszym wypadku by na mapie byly male kawalki malych wód, najlepiej jak najdalej od krawedzi
// beda sprawdzane lady dookola niego, szukajac wod stalych
//dla rzek...najgorszy przypadek by byl, jakby bylo kilka ujsc i w kazdym rozwidlenie z ta sama dlugoscia rzek
//zalozenia:ilosc x(ląd) w pliku : x
//ilosc u(rzeka) w pliku: u
//ilosc o(ocean) w pliku: o
//ilosc ujsc rzek: a
//ilosc ladu przy oceanie: b
//ilosc_pikseli_wysp bez dostepu do wody: c
//zlozonosc ostateczna: m*n+x+a*(a-u)+8*o+b*(8+(m-1)+(n-1)+b+c)