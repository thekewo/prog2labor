#include <iostream>
#include <math.h>
using namespace std;

struct perceptron
{
       public:
              int Input[16]; // Up to 16 inputs
              int Bias; //bias
              double Weight[16]; 
              double Threshold;
              bool fireing;
       private:
               int Input1_pr[16];
               int Bias_pr;
               double Weight_pr;
               int Threshold_pr;
               bool fireing_pr;
};    
int main(int argc, char *argv[])
{
    double total;
    double subOutput;
    perceptron prpMain;
    prpMain.Bias = 3;
    prpMain.Threshold = 0.6;
    prpMain.Weight[1] = 0.2;
    prpMain.Weight[2] = 0.3;
    for (int i = 1; i <=2; i++)
    {
        cout << "What is the value of input ";
        cout << i;
        cout << ": ";
        cin >> prpMain.Input[i];
        cout << "\n";
        total = total + (prpMain.Input[i] * prpMain.Weight[i]);
    } 
   
    do 
    {
    subOutput = 1/(1+exp(-(total * prpMain.Threshold)));
    if (subOutput >= prpMain.Threshold)
    {
              prpMain.fireing = true;
              cout << "It fired ";
              cout << "\n ";
              cout << subOutput;
    } else { 
    prpMain.fireing = false;
    cout << "It didnt fire";
    for ( int j = 1; j <=2; j++)
    {
    prpMain.Weight[j] = prpMain.Input[j]*(prpMain.Threshold - subOutput); 
   }
   total = 0.0;
   for (int k = 1; k <= 2; k++)
   {
       total = total + (prpMain.Input[k] * prpMain.Weight[k]);
   }     
}
}while(prpMain.fireing = false);  
    system("PAUSE");
    return EXIT_SUCCESS;
}