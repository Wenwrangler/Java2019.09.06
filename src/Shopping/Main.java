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
        ArrayList<ShoppingCart> shoppingCartArrayList = new ArrayList<ShoppingCart>();
        try {
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("商品数据.txt"),"gbk"));
            String line;
            while((line = in.readLine()) != null){
                String[] tempBufferString = line.split("&……%");
                Goods tempGoods = new Goods(tempBufferString[0],Double.parseDouble(tempBufferString[1]),tempBufferString[2],Long.parseLong(tempBufferString[3]));
                if(goodsHashMap.containsKey(tempBufferString[0])){
                    goodsHashMap.get(tempBufferString[0]).add(tempGoods);
                }else{
                    ArrayList<Goods> arrayListGoods = new ArrayList<Goods>();
                    arrayListGoods.add(tempGoods);
                    goodsHashMap.put(tempBufferString[0],arrayListGoods);
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
            ArrayList<String> tempProductData = new ArrayList<String>();
            if (goodsHashMap.containsKey(str)) {
                for (int i = 0; i < goodsHashMap.get(str).size(); i++) {
                    System.out.println((i+1)+"："+goodsHashMap.get(str).get(i).toString());
                    tempProductData.add(goodsHashMap.get(str).get(i).toString());
                    idx++;
                }
            }else {
                System.out.println("未查找到此物品！");
                return;
            }
            System.out.print("请输入加入到购物车的物品行号：");
            while (true){
                try {
                    int label = sc.nextInt();
                    if(label==-1)
                        break;
                    String[] tempSplitString = tempProductData.get(label-1).split("    ");
                    int flag = 0;
                    ShoppingCart tempShoppingCart = new ShoppingCart(tempSplitString[0],Double.parseDouble(tempSplitString[1]),tempSplitString[2],1);
                    for(int i = 0; i <shoppingCartArrayList.size(); i++){
                        if(tempShoppingCart.equals(shoppingCartArrayList.get(i))){
                            flag = 1;
                            shoppingCartArrayList.get(i).setQuantity(shoppingCartArrayList.get(i).getQuantity()+1);
                        }
                    }
                    if(flag == 0)
                        shoppingCartArrayList.add(new ShoppingCart(tempSplitString[0],Double.parseDouble(tempSplitString[1]),tempSplitString[2],1));
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
            for(int i = 0; i < shoppingCartArrayList.size(); i++){
                System.out.println(shoppingCartArrayList.get(i));
            }
            int branchLabel = sc.nextInt();
            switch (branchLabel){
                case 1:
                    CheckOut(shoppingCartArrayList,goodsHashMap);
                    System.out.print(goodsHashMap.toString());
                    return;
                case 2:
                    Remove(shoppingCartArrayList);
                    break;
                case 3:
                    Modify(shoppingCartArrayList);
                    break;
                default:
                    return;
            }
        }


    }
    static void Modify( ArrayList<ShoppingCart> shoppingCartArrayList ){
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < shoppingCartArrayList.size(); i++){
            System.out.println((i+1) + "：" + shoppingCartArrayList.get(i));
        }
        System.out.print("请输入需要修改的商品序列号：");
        int label = sc.nextInt();
        System.out.print("请输入需改的数量：");
        int number = sc.nextInt();
        try {
            if(number == 0){
                shoppingCartArrayList.remove(label-1);
            }else{
                shoppingCartArrayList.get(label-1).setQuantity(number);
            }
            System.out.println("修改成功！");
        }catch (Exception e){
            System.out.println("修改失败！");
        }
    }
    static void Remove(ArrayList<ShoppingCart> shoppingCartArrayList ){
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < shoppingCartArrayList.size(); i++){
            System.out.println((i+1) + "：" + shoppingCartArrayList.get(i));
        }
        System.out.print("请输入需要删除的商品序列号：");
        int label = sc.nextInt();
        try {
            shoppingCartArrayList.remove(label-1);
        }catch (Exception e){
            System.out.println("删除失败！");
        }
    }
    static void CheckOut(ArrayList<ShoppingCart> shoppingCartsArrayList, HashMap<String, ArrayList<Goods> > goodsHashMap ){
        double sum = 0;
        for(int i = 0; i < shoppingCartsArrayList.size(); i++){
            int n = goodsHashMap.get(shoppingCartsArrayList.get(i).getName()).size();
            for (int j = 0; j < n; j++){
                if(goodsHashMap.get(shoppingCartsArrayList.get(i).getName()).get(j).equals(shoppingCartsArrayList.get(i))){
                    long amount = goodsHashMap.get(shoppingCartsArrayList.get(i).getName()).get(j).getQuantity();
                    amount -= shoppingCartsArrayList.get(i).getQuantity();
                    if(amount <= 0){
                        System.out.println("购买失败，"+ shoppingCartsArrayList.get(i).getName() + "数量不足~！");
                        break;
                    }
                    sum += (shoppingCartsArrayList.get(i).getPrice()*100*shoppingCartsArrayList.get(i).getQuantity())/100;
                    goodsHashMap.get(shoppingCartsArrayList.get(i).getName()).get(j).setQuantity(amount);
                    break;
                }
            }
        }
        System.out.println("一共 " + sum + "元");
        System.out.println("结账成功！");
    }
}
