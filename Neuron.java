import java.util.Random;
import java.lang.Math;
public class Neuron{
  public Boolean First;
  public double bias;
  public double[] weights;
  public Neuron[] input;
  public Neuron(Neuron[] inputLayer){
    input=inputLayer;
    weights = new double[inputLayer.length];
    Random r=new Random();
    bias=r.nextDouble()*10;
    for(int i = 0;i<weights.length;i++){
      weights[i]=r.nextDouble()*2.-1.;
    }
  }
  public Neuron(double bsias){
    bias=bsias;
  }
  public double output( ){
        double sum=0.0;
        if(First){
          for(int i=0;i<weights.length&&i<input.length;i++){
            sum+=weights[i]*input[i].output();
          }}
    sum+=bias;
    return (1/(1+Math.exp(sum)));
  }
  public void setweights(int index, double newWeight){
    weights[index]=newWeight;
  }
  public void setbias(double newBias){
    bias= newBias;
  }
}
