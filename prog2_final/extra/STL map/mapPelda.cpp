#include <iostream>
#include <ctime>
#include <map>
#include <string>
#include <vector>
#include <algorithm>

int randomNumber(int min, int max);
void mapPrinter(std::map<std::string, int>& in);     //kiirja a map elemeit
void mapFiller(std::map<std::string, int> &in);     //feltolti a mapot nevekkel és random pontokkal
std::vector<std::pair<std::string, int>> mapSorter(std::map<std::string, int> &in); //a map elemeit a pont szerint rendezve visszaadka egy vektorba
//bool comparePoints(auto &&a, auto &&b);


int main(){
    srand((unsigned int)time(NULL));
    std::map<std::string, int> ikszde;
    mapFiller(ikszde);
    std::cout << "Rendezetlen map elemei:" << std::endl;
    mapPrinter(ikszde);
    std::vector<std::pair<std::string, int>> orderedIkszde = mapSorter(ikszde);

    std::cout << "Rendezett map elemei:" << std::endl;
    for(auto& i :orderedIkszde){
        std::cout << i.first << " " << i.second << std::endl;
    }
    



    return 0;
}

int randomNumber(int min, int max){
    return min+ rand()%(max-min);
}

void mapPrinter(std::map<std::string, int>& in){
    for(auto & it: in){
        std::cout << it.first << " " << it.second << std::endl; 
    }
}

void mapFiller(std::map<std::string, int> &in){
    for(int i = 0; i<10; i++){
        std::string temp = "ember" + std::to_string(i);
        in[temp] = randomNumber(0, 1000);
    }
}

std::vector<std::pair<std::string, int>> mapSorter(std::map<std::string, int>& in){
    std::vector<std::pair<std::string, int>> ordered;
    

    for(auto &it :in){
        std::pair<std::string, int> temp{it.first, it.second};
        //std::cout << it.first << "\t" << it.second << std::endl;
            ordered.push_back(temp);  
    }

    std::sort (
                std::begin ( ordered ), std::end ( ordered ),
        [ = ] ( auto && p1, auto && p2 ) {
                return p1.second > p2.second;
        }
    );
    
    
    
    
    //std::sort(std::begin(ordered), std::end(ordered), comparePoints)

    return ordered;
};
