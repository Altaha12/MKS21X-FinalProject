import java.util.Random;
import java.lang.Math;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
/* Hello, This version has comments. Most of the methods where for me to train the network, and
save it. This is essentially a platform for creating Neural Networks. Most of the tools below are
for me to debug and develop the neural network.That wasn't the final product
however, it was the Neural Network for recognizing handwritten digits, which is for all practical
 purposes a a linear function of 784 variables.
*/
//The class Implements Serializable so I can save the objects and retrvie them easily
public class NeuralNetwork implements Serializable{
  private static final long serialVersionUID = 4L;
  public double[][][] bigTable;
  public double[][] smallTable;
  public double[][] sigmoidSmallTable;
  public double learningRate=0.9;
  //Constructor, Bigtable first index is layer, second index is Neuron, third Index is bias and weights, bias is at index 0
  // SmallTable stores the weighted sum of the  neurons from the previous layers for each neurons
  //SigmoidSmall Table is the sigmoid element wise of   smallTable
  public NeuralNetwork(int numberLayers,int[] numberNeurons){
    Random r=new Random();
    bigTable = new double[numberLayers][][];
    int prev=0;
    for(int i=0;i<numberLayers;i++){
      bigTable[i]= new double[numberNeurons[i]][];
      for(int j=0;j<numberNeurons[i];j++){
        bigTable[i][j]= new double[prev+1];
          for(int u=0;u<prev+1;u++){
            bigTable[i][j][u]=r.nextDouble()*2-1;
          }
      }
      prev=numberNeurons[i];
    }
    smallTable= new double[numberLayers][];
    sigmoidSmallTable = new double[numberLayers][];
    smallTable[0]= new double[bigTable[0].length];
    sigmoidSmallTable[0]= new double[bigTable[0].length];
    for(int i=0;i<smallTable[0].length;i++){
      smallTable[0][i]=bigTable[0][i][0];
      sigmoidSmallTable[0][i]=bigTable[0][i][0];
    }
    for(int i=1;i<smallTable.length;i++){
      smallTable[i]= new double[bigTable[i].length];
      sigmoidSmallTable[i]= new double[bigTable[i].length];
      for(int n=0;n<smallTable[i].length;n++){
        smallTable[i][n]=bigTable[i][n][0];
        for(int p=0;p<sigmoidSmallTable[i-1].length;p++){
          smallTable[i][n]+=sigmoidSmallTable[i-1][p]*bigTable[i][n][p+1];
        }
        sigmoidSmallTable[i][n]=sigmoid(smallTable[i][n]);
      }
    }
  }
  //Simple sigmoid funciton that outpus a value between 0 and 1 used by convention
  public static double sigmoid(double input){
    return (1/( 1 + Math.pow(Math.E,(-1*input))));
  }
// Derivative of sigmoid function for gradient descent
  public static double dsigmoid(double input){
    return sigmoid(input)*(1-sigmoid(input));
  }
  //Inverse of Sigmoid Function, not really used anymore
  public static double isigmoid(double input){
    return Math.log((1./input)-1)*(-1.);
  }
// This method saves the serialized object with the same version ID. allows me to use networks after training.
  public void save(String name) throws FileNotFoundException,IOException{
    ObjectOutputStream ssavedNetwork=new ObjectOutputStream(new FileOutputStream(name));
    ssavedNetwork.writeObject(this);
    ssavedNetwork.close();
  }
  // this method opens the serialized saved objects and returns a neuralnetowkr object. dakhal means enter
  public static NeuralNetwork dakhal(String name) throws FileNotFoundException,IOException,  ClassNotFoundException{
    ObjectInputStream savedNetwork = new ObjectInputStream(new FileInputStream(name));
    NeuralNetwork tobereturned= null;
    tobereturned = (NeuralNetwork) savedNetwork.readObject();
    savedNetwork.close();
    return tobereturned;
  }
  // this method takes input and them changes smallTable and sigmoid Small Table accordingly.
  public double[] feedForward(){
    double[] lastOutput=new double[bigTable[0].length];
    for(int i =0;i<lastOutput.length;i++){
      lastOutput[i]=bigTable[0][i][0];
    }
    double[] output;
    for(int i=1;i<bigTable.length;i++){
      output=new double[bigTable[i].length];
      for(int j=0;j<bigTable[i].length;j++){
        output[j]=bigTable[i][j][0];
        for(int u=1;u<bigTable[i][j].length;u++){
          output[j]+=bigTable[i][j][u]*lastOutput[u-1];
        }
        smallTable[i][j]=output[j];
        output[j]=sigmoid(output[j]);
        sigmoidSmallTable[i][j]= output[j];
      }
      lastOutput=output;

    }
    return lastOutput;
  }
  // Calculates error for last layer according to algorithm in book cited in Read.md
  public double[] E_O(double[] expected){
    double[] tbr= new double[expected.length];
    double[] actual=this.feedForward();
    double[] rawactual= new double[tbr.length];
    for(int i=0;i<tbr.length;i++){
      tbr[i]=(actual[i]-expected[i])*dsigmoid(isigmoid(actual[i]));
    }
    return tbr;
  }
  // calculates error for other layers
  public double[] E_H(int index, double[] errorNext){
    double[] error= new double[bigTable[index].length];
    for(int neuronindex=0;neuronindex<error.length;neuronindex++){
      error[neuronindex]=0;
      for(int weightneuron=0;weightneuron<bigTable[index+1].length;weightneuron++){
        error[neuronindex]+=bigTable[index+1][weightneuron][neuronindex+1]*errorNext[weightneuron];
      }
      error[neuronindex]=error[neuronindex]*dsigmoid(smallTable[index][neuronindex]);
    }
    return error;
  }
  //sets the first layer of smallTable in order to prepare for feed forward
  public void setInput(double [] newInput){
    for(int i =0;i<bigTable[0].length;i++){
      bigTable[0][i][0]= newInput[i];
      sigmoidSmallTable[0][i]=newInput[i];
      smallTable[0][i]=newInput[i];
    }
  }
  //just print array
  public static void printArray(double[]ary)/*0a*/{
    String r = "[";
    for(int i=0;i<ary.length;i++){
      r+=ary[i];
      if(!(ary.length-i==1))r+=", ";
    }
    r+="]";
    System.out.println(r);
  }
  public static void printArray(double[][]ary)/*0b*/{
    System.out.println("[");
    for(int i=0;i<ary.length;i++){
      printArray(ary[i]);
    }
    System.out.println("]");
  }
  // Debugging tools to see if structure of Neural Network is correct
  public void printLayer(int layer){
    printArray(bigTable[layer]);
  }
  public void printNeuron(int layer, int Neuron){
    printArray(bigTable[layer-1][Neuron]);
  }
  /*
  double[] input is an array of length 784 each corrosponding to a pixel of the 28*28 photo
  double[] output is an array of length 10 each slot is 0 unless it is the digit in the image, then its 1.
  double learning rate sets global learning rate, learning rate slows gradient descent so one image
    doesn't mess up the training of the network.

  */
  public void train(double[] input, double[] output, double l){
    learningRate=l;
    this.setInput(input);
    this.feedForward();
    double[][] biasPartials= new double[bigTable.length][];
    biasPartials[bigTable.length-1]= this.E_O(output);
    for(int i=bigTable.length-2;i>0;i--){
      biasPartials[i]=this.E_H(i,biasPartials[i+1]);
    }
    //finished collecting errors time to determine partial derivatives of weights
    for(int layer = 1; layer<bigTable.length;layer++){
      for(int neuron=0;neuron<bigTable[layer].length;neuron++){
        for(int weight=1;weight<bigTable[layer][neuron].length;weight++){
          bigTable[layer][neuron][weight]-=learningRate*biasPartials[layer][neuron]*smallTable[layer-1][weight-1];
        }
      }
    }
    for(int layer = 1; layer<bigTable.length;layer++){
      for(int neuron=0;neuron<bigTable[layer].length;neuron++){
      bigTable[layer][neuron][0]-=biasPartials[layer][neuron];
      }
    }
  }
  // Opens and reads the mnist test csv to save as an array, faster access saves alot of time.
  public static double[][] read(int lines) throws FileNotFoundException{
    double[][]tbr=new double[lines][785];
    Scanner scanner = new Scanner(new File("mnist_test.csv"));
    scanner.useDelimiter(",");
    for(int i=0;i<lines;i++){
      for(int j=0;j<785;j++){
        tbr[i][j]=scanner.nextDouble();
      }
      scanner.nextLine();
    }
    scanner.close();
    return tbr;
  }
  // takes a digit and makes an output array of length 10 with all value equal to zero except to the index
  //corossponding to the input v
  public static double[] createoutput(double v){
    double[] tbr= new double[10];
    for(int i=0;i<tbr.length;i++){
      if(v==i)tbr[i]=1;
      else tbr[i]=0;
    }
    return tbr;
  }
//  test the networks and determine an accuracy. with what read before.
  public void test(int n) throws FileNotFoundException{
    double[][] trainingdata = read(n);
    int number=0;
    for(int test=0;test<n;test++){
    this.setInput(Arrays.copyOfRange(trainingdata[test],1,785));
    double[] out = this.feedForward();
    int greatest=0;
    for(int i=0;i<out.length;i++){if(out[greatest]<out[i])greatest=i;}
    if(greatest==trainingdata[test][0])number+=1; System.out.println(greatest);}
    System.out.println("percent correct is " + number);
  }
  // this puts it all together given an image array of length 784 it returns a digit.
  public int What(double[] input){
    this.setInput(input);
    double[] out = this.feedForward();
    int greatest=0;
    for(int i=0;i<out.length;i++){if(out[greatest]<out[i])greatest=i;}
    return greatest;
  }
  //given an image of 28*28 it preps input for what() method
  public static double[] pic(String name) throws IOException, FileNotFoundException{
    BufferedImage image = new BufferedImage(28,28,BufferedImage.TYPE_INT_ARGB);
    image=ImageIO.read(new File(name));
    int index=0;
    double[]tbr=new double[784];
    for(int i=0;i<28;i++){
      for(int j=0;j<28;j++){
        Color c= new Color(image.getRGB(j,i),true);
        tbr[index]=c.getAlpha();
        index++;
      }
    }
    return tbr;
  }
  // given the name of an image that is not properly formatted it takes it and formats and saves import junit.framework.TestCase;
  // a file named standard.png or initiates it if not there. converts rpg grayscale to alpha values.
  public static void prepImage(String name) throws FileNotFoundException{
    BufferedImage image = new BufferedImage(28,28,BufferedImage.TYPE_INT_ARGB);
    BufferedImage image2 = new BufferedImage(28,28,BufferedImage.TYPE_INT_ARGB);
    try{
    image=ImageIO.read(new File(name));}
    catch(IOException e){
      System.out.println("nope");}
    Double[][] numberimage =new Double[28][28];
    for(int x=0;x<28;x++){
      for(int y=0;y<28;y++){
        Color c = new Color(image.getRGB(x,y));
        numberimage[x][y]= 255.-(0.2126*c.getRed() + 0.7152*c.getGreen() + 0.0722*c.getBlue());
        }
      /* Debugging just to see what was happening
if(c.getRed()>240&&c.getBlue()>240&&c.getGreen()>240){numberimage[x][y]=0.;
      System.out.println(numberimage[x][y].intValue());
        System.out.println(numberimage[x][y].doubleValue());
*/
      }

    for(int x=0;x<28;x++){
      for(int y=0;y<28;y++){
        Color c =new Color(0,0,0,numberimage[x][y].intValue());
        image2.setRGB(x,y,c.getRGB());
      }
    }
    try{
      ImageIO.write(image2,"png", new File("standard.png"));}
    catch(IOException e){
      System.out.println("nope");

  }}
  public static void main(String[] args) throws FileNotFoundException,IOException,  ClassNotFoundException{
    /*
    int[] layout= new int[]{784,700,550,450,300,100,10};
    NeuralNetwork popsy3= new NeuralNetwork(7,layout);
    double[][] trainingdata = read(60000);
    for(int epoch=0;epoch<60000;epoch++){
      popsy3.train(Arrays.copyOfRange(trainingdata[epoch],1,785),createoutput(trainingdata[epoch][0]),.0001555);
      if(epoch%1000==0)System.out.println("where we at "+epoch);
    }
    popsy3.save("popsy3.txt");

    double[][] input= read(100);

    for(int i=0;i<100;i++){
      System.out.println(popsy.What(Arrays.copyOfRange(input[i],1,785)));}
      */
    NeuralNetwork popsy= dakhal("popsyz.txt");
    if(args.length==2){
      if(args[1].equals("True")){
        System.out.println("Wazzap, this is an image of the digit "+popsy.What(pic(args[0])));}
      else if(args[1].equals("False")){
        prepImage(args[0]);
        System.out.println("Wazzap, this is an image of a "+popsy.What(pic("standard.png")));}
      else{
        System.out.println("Please Enter valid True or False, True if in MNIST Format, False if not");
      }
    }
    else{System.out.println("Invalid Input");}
  }
}
