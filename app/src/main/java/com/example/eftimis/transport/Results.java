package com.example.eftimis.transport;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


public class Results extends AppCompatActivity {

    ListView list;
    public static int pack  , kilo ;
    public static boolean fast , breakAble ;
    public static String shipFrom , shipTo ;
    String[] itemname = {
            "Fedex",
            "Arrow",
            "ABH Transport",
            "GLS",
            "ACS",
            "Horizon PHARMA",
            "UPS",
            "Smart"
    };

    Integer[] imgid = {
            R.drawable.fedex,
            R.drawable.cu,
            R.drawable.images,
            R.drawable.fds,
            R.drawable.index_2,
            R.drawable.horizon,
            R.drawable.index,
            R.drawable.smart,
    };

    public static String[] logo = {
            "Fast delivery services with the minimum cost " ,
             "Best quality  , low cost   the best delivery company",
                    "For 60 years we make delivery easy for you " ,
                    "We create the best way to delivery",
                    "Our transport company can achieve every deman  " ,
                    "Best quality  in our services ",
                    "Better quality  , lower cost is the secret for our success" ,
                    "The number one company in transcactions"

    };
    public static    Integer[]  minCost= {4, 4, 3 , 6, 6, 5, 5, 3,};
    public static Integer[] kiloCost= {1, 3, 2 , 3, 2, 1, 3, 3,};
    public static Integer[] breakable= {2, 4, 3 , 6, 2 ,2, 5, 3};
    public static Integer[] fastDelivery= {2, 4, 3 , 6, 2 ,2, 5, 3,};
    public static String[] phones= {"6948754758","6948152748","6932764728","6928714750","6948155724","6942752756","6968252752","6948754758","6948752756"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        kilo = HomepageActivity.weight;
        pack = HomepageActivity.packages;



        OfferAdapter adapter = new OfferAdapter(this , itemname , imgid);
        list = (ListView) findViewById(R.id.offerList);
        list.setAdapter(adapter);


    }

    public ListView getList() {
        return list;
    }

    public void setList(ListView list) {
        this.list = list;
    }

    public static int getPack() {
        return pack;
    }

    public static void setPack(int pack) {
        Results.pack = pack;
    }

    public static int getKilo() {
        return kilo;
    }

    public static void setKilo(int kilo) {
        Results.kilo = kilo;
    }

    public static boolean isFast() {
        return fast;
    }

    public static void setFast(boolean fast) {
        Results.fast = fast;
    }

    public static boolean isBreakAble() {
        return breakAble;
    }

    public static void setBreakAble(boolean breakAble) {
        Results.breakAble = breakAble;
    }

    public String[] getItemname() {
        return itemname;
    }

    public void setItemname(String[] itemname) {
        this.itemname = itemname;
    }

    public Integer[] getImgid() {
        return imgid;
    }

    public void setImgid(Integer[] imgid) {
        this.imgid = imgid;
    }

    public static Integer[] getMinCost() {
        return minCost;
    }

    public static void setMinCost(Integer[] minCost) {
        Results.minCost = minCost;
    }

    public static Integer[] getKiloCost() {
        return kiloCost;
    }

    public static void setKiloCost(Integer[] kiloCost) {
        Results.kiloCost = kiloCost;
    }

    public static Integer[] getBreakable() {
        return breakable;
    }

    public static void setBreakable(Integer[] breakable) {
        Results.breakable = breakable;
    }

    public static Integer[] getFastDelivery() {
        return fastDelivery;
    }

    public static void setFastDelivery(Integer[] fastDelivery) {
        Results.fastDelivery = fastDelivery;
    }
}


