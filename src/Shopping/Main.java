package Shopping;
import Goods.Goods;

import java.io.*;

class ShoppingCart extends Goods{

    public ShoppingCart(String name, double price, String describe, long quantity) {
        super(name, price, describe, quantity);
    }
}


public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        try {

            BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("商品数据.txt"),"utf8"));
            String line;
            while((line = in.readLine()) != null){
                System.out.println(line.toString());
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }


    }
}
