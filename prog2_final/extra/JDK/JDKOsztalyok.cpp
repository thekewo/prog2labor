#include <iostream>
#include <boost/filesystem.hpp>

using namespace std;
using namespace boost::filesystem; 

void bejaro(std::string root);
int counter = 0;

int main(int argc, char* argv[]){

    //string root = "/usr/lib/jvm/java-11-openjdk-amd64";
    string root = "/home/kewo/Documents/JDK";
    bejaro(root);
    cout << counter << endl;



}

void bejaro(std::string root){
    for(recursive_directory_iterator end, dir(root); dir != end; dir++){
        if(extension(*dir) == ".java"){
            cout<<*dir<<endl;
            counter++;
        }
;

    }


}

//g++ JDKOsztalyok.cpp -lboost_system -lboost_filesystem -o JDKOsztalyok.o
