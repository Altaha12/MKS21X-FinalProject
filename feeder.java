import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;




public class feeder {
  /*Print Array Functions From previous problems so I check format*/
  public static void printArray(int[]ary)/*0a*/{
    String r = "[";
    for(int i=0;i<ary.length;i++){
      r+=ary[i];
      if(!(ary.length-i==1))r+=", ";
    }
    r+="]";
    System.out.println(r);
  }
  public static void printArray(int[][]ary)/*0b*/{
    System.out.println("[");
    for(int i=0;i<ary.length;i++){
      printArray(ary[i]);
    }
    System.out.println("]");
  }
    public static void main(String[] args)  throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("train.csv"));
        int[][] numberimage=new int[28][28];
        scanner.useDelimiter(",");
        int label=scanner.nextInt();
        for(int i=0;i<28;i++){
          for(int j=0;j<28;j++){
            numberimage[j][i]=scanner.nextInt();}}
        scanner.close();
        System.out.println(""+label);
        printArray(numberimage);
        BufferedImage image = new BufferedImage(28,28,BufferedImage.TYPE_INT_ARGB);
        for(int x=0;x<28;x++){
          for(int y=0;y<28;y++){
            Color c =new Color(0,0,0,numberimage[x][y]);
            image.setRGB(x,y,c.getRGB());
          }
        }
        try{
          ImageIO.write(image,"png", new File("image.gif"));}
        catch(IOException e){
          System.out.println("nope");
        }


    }}
