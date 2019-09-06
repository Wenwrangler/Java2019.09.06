package Shopping;
import Goods.Goods;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class ShoppingCart extends Goods{

    public ShoppingCart(String name, double price, String describe, long quantity) {
        super(name, price, describe, quantity);
    }
}


public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HashMap<String, ArrayList<Goods> > goodsHashMap1 = new HashMap<String, ArrayList<Goods> >();
        HashMap<String, ArrayList<Goods> > goodsHashMap2 = new HashMap<String, ArrayList<Goods> >();
        ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
        try {
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("商品数据.txt"),"gbk"));
            String line;
            while((line = in.readLine()) != null){
                String[] temp = line.split("&……%");
                Goods tempGoods = new Goods(temp[0],Double.parseDouble(temp[1]),temp[2],Long.parseLong(temp[3]));
                if(goodsHashMap1.containsKey(temp[0])){
                    goodsHashMap1.get(temp[0]).add(tempGoods);
                }else{
                    ArrayList<Goods> arrayListGoods = new ArrayList<Goods>();
                    arrayListGoods.add(tempGoods);
                    goodsHashMap1.put(temp[0],arrayListGoods);
                }
                if(goodsHashMap2.containsKey(temp[2])){
                    goodsHashMap2.get(temp[2]).add(tempGoods);
                }else{
                    ArrayList<Goods> arrayListGoods = new ArrayList<Goods>();
                    arrayListGoods.add(tempGoods);
                    goodsHashMap2.put(temp[2],arrayListGoods);
                }

            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        System.out.println(goodsHashMap1.toString());
        System.out.print("请输入需要购买的物品或物品用途说明：");
        Scanner sc = new Scanner(System.in);
        {
            String str = sc.nextLine();
            int idx = 0;
            ArrayList<String> list = new ArrayList<String>();
            if (goodsHashMap1.containsKey(str)) {
                for (int i = 0; i < goodsHashMap1.get(str).size(); i++) {
                    System.out.println(goodsHashMap1.get(str).get(i).toString());
                    list.add(goodsHashMap1.get(str).get(i).toString());
                    idx++;
                }
            } else if (goodsHashMap2.containsKey(str)) {
                for (int i = 0; i < goodsHashMap2.get(str).size(); i++) {
                    System.out.println(goodsHashMap2.get(str).get(i).toString());
                    list.add(goodsHashMap2.get(str).get(i).toString());
                    idx++;
                }
            } else {
                System.out.println("未查找到此物品！");
                return;
            }
            System.out.print("请输入加入到购物车的物品行号：");
            while (true){
                try {
                    int x = sc.nextInt();
                    if(x==-1)
                        break;
                    String[] temp = list.get(x).split("    ");
                    shoppingCarts.add(new ShoppingCart(temp[0],Double.parseDouble(temp[1]),temp[2],Long.parseLong(temp[3])));
                    for (int i = 0; i < goodsHashMap1.get(temp[0]).size(); i++) {
                        if(goodsHashMap1.get(temp[0]).get(i).getName().equals(temp[0])&&goodsHashMap1.get(temp[0]).get(i).getDescribe().equals(temp[2])){
                            goodsHashMap1.get(temp[0]).get(i).setQuantity(goodsHashMap1.get(temp[0]).get(i).getQuantity()-1);
                            break;
                        }
                    }
                    System.out.println("加入成功~！");
                }catch (Exception e){
                    System.out.println("加入失败~！");
                }
                System.out.print("输入-1结束输入：");
            }
        }
        System.out.println("这是你的购物车清单~ /r 结账请输入1 删除某一项请输入2 增加某一项请输入3 退出请输入-1");
        System.out.println(shoppingCarts.toString());
        int n = sc.nextInt();
        switch (n){
            case 1:


                break;
            case 2:


                break;
            case 3:



                break;
            default:
                return;
        }

    }
}
