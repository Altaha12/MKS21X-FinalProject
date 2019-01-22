


How to Compile/Run:




1.Compile NeuralNetwork.java
2. Run in terminal by writing "java NeuralNetwork", followed by two arguments.

3. First Argument is the file name, for example Untitled.png, the file has to be a png jpg or a gif.

4. Second Argument is "True" or "False" with this exact capitalization, Please select true if the file is in MNIST Format like the ones I included in the Repo. False otherwise.

5. Directions for creating your own images:
          White Background Black color or the following HTML Color: 242424. Its just very Dark Grey.
          Save as Png jpg or GIF
          Run in Terminal.
          
Note: There should be a greater rate of accuracy with the images I created because of the way the network it was trained. It was trained to recognize examples in the MNIST DataSet and has acheived an accuracy rate of 93% was that data set. I however believe that because I didn't implement a technique called dropout, the network was overfitted to these examples. My final Product is a Neuralwork saved as a serialized object that is for all practical purposes a linear function. Most of the code in the class was for me to develop the neural network and has no implications for the user.

Example with Running the Network on a file named apcs.png:"java NeuralNetwork apcs.png False"


Log:


01/02-01/03: Created Feeder Class, Class isn't used in final product, but is a tool I used to feed images into the neural Network. It is basically a scanner for the csv file witht the training data. I also used it to construct images from the training data to verify output manually.


01/05:Created Neuron Class Which was initiall intended as the subunit of the network. I however Determined that the Class was unneccesary and that all the information it holds could be simply stored in avery large multi-dimensional array. Class was never used afterwards or tested.

01/06: Created Class Network and started writing code but hit dead end when I was unable to determine how to store the weights and biases for each Neuron in manner that was intuitive and easy to deal with.

01/07-01/10: Spent Some times reading the book on this link http://neuralnetworksanddeeplearning.com/index.html in order to understand Neural Networks and how they learned. Spent sometime on the math and the algorith so I can implement it properly. I also Planned out everything on paper, attached to prototype. This stage was dedicated to understanding the algorithm rather than trying to code without understanding, which failed. (I didn't implement stochastic gradient descent as suggested by the book because I noticed that it yeilded worse results than simple gradient descent)


01/11: Created NeuralNetwork Class with Constructor, methods for saving the NeuralNetwork after training it, and method for getting input data.


01/12:Created Methods for feeding forward the input and determining error in each layer according to formulas provided in the aforementioned book. 


01/13:Created the Training method, train(), that put everything together and started training the Network.


01/14:Creting the Test method to test Network and starting trying out different structures. Also figured out how to SSH into School Server to train the network on it(Training takes time and causes my laptop to slow down so I ran the code remotely)


01/15-01/20:Tested Different Network Structures Best Network I got had an accuracy of about 93%.


Note: A lot of the work was planning on paper so as to understand the math behind the NeuralNetwork. That planning allowed for quick development that didn't require alot of debugging. Thus I have few commits because the code was the easy part of this project and most of the time was dedicated to either testing different network structures or planning, neither involved any changes to the code but changing the parameters when constructing a neural Network in the main() method.
