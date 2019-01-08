Public Class Network{
  private Neuron[][] all;
  public Network(int layers, int[] dimensions){
    all=new Neuron[layers+1][];
    all[0]= new Neuron[784];
    for(int i=0;i<all[0].length;i++){
      all[0][i]= new Neuron();
    }
    for(int i=1;i<all.length;i++){
      all[i]= new Neuron[dimensions[i]];
      for(int j=0;j<all[i].length;j++){
        all[i][j]= new Neuron(all[i-1]);
      }

    }
  }
  public void FeedForward(){}
  public void Backpropagate(){}
  public void train(){}


}
