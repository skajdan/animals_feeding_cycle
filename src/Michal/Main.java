package Michal;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws InterruptedException {



        TreeMap<String, Animal> map = wczytanieDanychDoMapy("D:\\Java\\Projekty\\Projekt_GUI_1\\data\\mammals.txt");


        wyswietlenie_z_unknown(map);
        zwierze_najwieksza_masa(map);
        najwyzszy_wspolczynnik_snu(map);
        zwierzeta_ponad_serdnia_zycia(map);


        List<Animal> animalList = new ArrayList<>();


        map.entrySet()
                .stream().sorted(Comparator.comparing(x -> (x.getValue().body_wt*(-1)))).limit(3)
                .forEach(x-> animalList.add(x.getValue()));

        double[] animalTab = new double[animalList.size()];


        for (int i = 0; i <animalList.size() ; i++) {
            animalTab[i] = animalList.get(i).body_wt;
        }



        for (Animal animal : animalList) {
            System.out.println("AnimalCharacetristics" + animalList
                    .toString().replaceAll("=0.0", "=unknown"));
        }




        Thread karmienie = new Thread(() -> {
            for (int j =0; j<100; j++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < animalList.size(); i++) {

                    animalList.set(i,karmienie_spalanie(animalList.get(i),1));
                }

            }
        });
        Thread spalanie = new Thread(() -> {



            {
                for (int j = 0; j < 100; j++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    double tmp = 0;
                    for (int i = 0; i < animalList.size(); i++) {


                        tmp = animalList.get(i).body_wt;
                        animalList.set(i, karmienie_spalanie(animalList.get(i), 0));


                    }

                    if (j%2!=0) {


                        for (int i = 0; i < animalList.size(); i++) {

                            if(animalList.get(i).body_wt> tmp) {
                                System.out.println("Aktualna masa zwierzęcia wynosi: " + (i + 1) + " "
                                        + animalList.get(i).body_wt + " \n Zwierze zwiekszylo mase");
                            }

                            else if(animalList.get(i).body_wt<tmp){
                                System.out.println("Aktualna masa zwierzęcia wynosi: " + (i + 1) + " "
                                        + animalList.get(i).body_wt + " \n Zwierze zmniejszylo mase");

                            }

                            else{
                                System.out.println("Aktualna masa zwierzęcia wynosi: " + (i + 1) + " "
                                        + animalList.get(i).body_wt + "\n  Zwierze nie zmnienilo masy");
                            }
                        }
                        System.out.println();
                    }

                }

            }



        });

        karmienie.start();
        spalanie.start();
        karmienie.join();
        spalanie.join();



        porownianeMasy(animalList,animalTab);

    }

    public static void porownianeMasy(List<Animal> animalList, double[] animalTab){
        System.out.println("Porownanie masy przed i po: ");

        for (int i =0; i<animalList.size();i++) {
            System.out.println("Zwierze_" + (i + 1) + " przed: " +
                    animalTab[i] + "  " + "po:  " + animalList.get(i).body_wt);

        }

    }

    public static TreeMap<String, Animal> wczytanieDanychDoMapy(String filePath) {
        TreeMap<String, Animal> map = new TreeMap<>((s1, s2) -> {
            if (s1.length() > s2.length())
                return -1;
            else
                return 1;
        });

        IntStream
                .range(0, 62)
                .forEach(x -> {
                            try {
                                String line = Files.readAllLines(Paths.get(filePath)).get(x + 1);
                                String[] values = line.split(";");

                                for (int i = 0; i < values.length; i++)
                                    if (values[i].equals("NA"))
                                        values[i] = "0";


                                Animal animal = new Animal(
                                        Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                                        Double.parseDouble(values[3]), Double.parseDouble(values[4]),
                                        Double.parseDouble(values[5]), Double.parseDouble(values[6]),
                                        Double.parseDouble(values[7]), Integer.parseInt(values[8]),
                                        Integer.parseInt(values[9]), Integer.parseInt(values[10]));

                                map.put(values[0], animal);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                );


        return map;





    }




    public static synchronized Animal karmienie_spalanie(Animal animal , int x){
        if (x==0)
            animal.body_wt = (animal.body_wt) * (0.986);

        else
            animal.body_wt = animal.body_wt * (1+(Math.random() * 0.015 + 0.005));


        return animal;

    }



    public static void wyswietlenie_z_unknown(TreeMap<String,Animal> map){
        for(Map.Entry<String, Animal> entry : map.entrySet())
            System.out.println(entry.getKey()  + entry.getValue().toString()
                    .replaceAll("=0.0","=unknown"));
    }



    public static void zwierze_najwieksza_masa(TreeMap<String,Animal> map){
        System.out.println( map.entrySet()
                .stream()
                .max(Comparator.comparing(x -> x.getValue().body_wt))
                .get());

    }

    public static void najwyzszy_wspolczynnik_snu(TreeMap<String,Animal> map){
        map.entrySet().stream()
                .filter(x -> x.getValue().dreaming * x.getValue().total_sleep != 0)
                .sorted(Comparator.comparing(x -> ((x.getValue().dreaming/x.getValue().total_sleep))*(-1))).limit(3)
                .forEach(x ->System.out.println(x.getKey() +" " +
                        (int)Math.ceil((x.getValue().dreaming/x.getValue().total_sleep)*100)+"%"));

    }
    public static void zwierzeta_ponad_serdnia_zycia(TreeMap<String,Animal> map){
        map.entrySet()
                .stream()
                .filter(x-> {
                            double average = 0;
                            int i =0;
                            for (Map.Entry<String, Animal> entry : map.entrySet()){
                                average += entry.getValue().life_span;
                                i++;
                            }
                            average/=i;

                            return x.getValue().life_span > average;
                        }
                ).forEach(System.out::println);
    }




}


