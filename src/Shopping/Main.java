package Shopping;
import Goods.Goods;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

class ShoppingCart extends Goods{

    public ShoppingCart(String name, double price, String describe, long quantity) {
        super(name, price, describe, quantity);
    }
}

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HashMap<String, ArrayList<Goods> > goodsHashMap = new HashMap<String, ArrayList<Goods> >();

        ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
        try {
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("商品数据.txt"),"gbk"));
            String line;
            while((line = in.readLine()) != null){
                String[] temp = line.split("&……%");
                Goods tempGoods = new Goods(temp[0],Double.parseDouble(temp[1]),temp[2],Long.parseLong(temp[3]));
                if(goodsHashMap.containsKey(temp[0])){
                    goodsHashMap.get(temp[0]).add(tempGoods);
                }else{
                    ArrayList<Goods> arrayListGoods = new ArrayList<Goods>();
                    arrayListGoods.add(tempGoods);
                    goodsHashMap.put(temp[0],arrayListGoods);
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        System.out.println(goodsHashMap.toString());
        System.out.print("请输入需要购买的物品：");
        Scanner sc = new Scanner(System.in);
        {
            String str = sc.nextLine();
            int idx = 0;
            ArrayList<String> list = new ArrayList<String>();
            if (goodsHashMap.containsKey(str)) {
                for (int i = 0; i < goodsHashMap.get(str).size(); i++) {
                    System.out.println(goodsHashMap.get(str).get(i).toString());
                    list.add(goodsHashMap.get(str).get(i).toString());
                    idx++;
                }
            }else {
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
                    int flag = 0;
                    ShoppingCart tempShoppingCart = new ShoppingCart(temp[0],Double.parseDouble(temp[1]),temp[2],1);
                    for(int i = 0; i <shoppingCarts.size(); i++){
                        if(tempShoppingCart.equals(shoppingCarts.get(i))){
                            flag = 1;
                            shoppingCarts.get(i).setQuantity(shoppingCarts.get(i).getQuantity()+1);
                        }
                    }
                    if(flag == 0)
                        shoppingCarts.add(new ShoppingCart(temp[0],Double.parseDouble(temp[1]),temp[2],1));

                    System.out.println("加入成功~！");
                }catch (Exception e){
                    System.out.println("加入失败~！");
                    break;
                }
                System.out.print("输入-1结束输入/继续输入：");
            }
        }
        while(true){
            System.out.println("这是你的购物车清单~  结账请输入1 删除某一项请输入2 修改某一项数量请输入3 退出请输入-1");
            for(int i = 0; i < shoppingCarts.size(); i++){
                System.out.println(shoppingCarts.get(i));
            }
            int n = sc.nextInt();
            switch (n){
                case 1:
                    CheckOut(shoppingCarts,goodsHashMap);
                    System.out.print(goodsHashMap.toString());
                    return;
                case 2:
                    Remove(shoppingCarts);
                    break;
                case 3:
                    Modify(shoppingCarts);
                    break;
                default:
                    return;
            }
        }


    }
    static void Modify( ArrayList<ShoppingCart> shoppingCarts ){
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < shoppingCarts.size(); i++){
            System.out.println((i+1) + "：" + shoppingCarts.get(i));
        }
        System.out.print("请输入需要修改的商品序列号：");
        int x = sc.nextInt();
        System.out.print("请输入需改的数量：");
        int n = sc.nextInt();
        try {
            if(n == 0){
                shoppingCarts.remove(x-1);
            }else{
                shoppingCarts.get(x-1).setQuantity(n);
            }
            System.out.println("修改成功！");
        }catch (Exception e){
            System.out.println("修改失败！");
        }
    }
    static void Remove(ArrayList<ShoppingCart> shoppingCarts ){
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < shoppingCarts.size(); i++){
            System.out.println((i+1) + "：" + shoppingCarts.get(i));
        }
        System.out.print("请输入需要删除的商品序列号：");
        int x = sc.nextInt();
        try {
            shoppingCarts.remove(x-1);
        }catch (Exception e){
            System.out.println("删除失败！");
        }
    }
    static void CheckOut(ArrayList<ShoppingCart> shoppingCarts, HashMap<String, ArrayList<Goods> > goodsHashMap ){
        double sum = 0;
        for(int i = 0; i < shoppingCarts.size(); i++){
            int n = goodsHashMap.get(shoppingCarts.get(i).getName()).size();
            for (int j = 0; j < n; j++){
                if(goodsHashMap.get(shoppingCarts.get(i).getName()).get(j).equals(shoppingCarts.get(i))){
                    long x = goodsHashMap.get(shoppingCarts.get(i).getName()).get(j).getQuantity();
                    x -= shoppingCarts.get(i).getQuantity();
                    if(x <= 0){
                        System.out.println("购买失败，"+ shoppingCarts.get(i).getName() + "数量不足~！");
                        break;
                    }
                    sum += (double) shoppingCarts.get(i).getPrice()*shoppingCarts.get(i).getQuantity();
                    goodsHashMap.get(shoppingCarts.get(i).getName()).get(j).setQuantity(x);
                    break;
                }
            }
        }
        System.out.println("一共 " + sum + "元");
        System.out.println("结账成功！");
    }
}
