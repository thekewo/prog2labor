

#include <cstdlib>

using namespace std;

/*
 * 
 */
class Szulo {
public:
    int a = 0;
    int b = 0;
};

class Gyerek: public  Szulo {
public:
    int c = 0;
}; 

int main(int argc, char** argv) {
    Szulo* szulo = new Gyerek;
    szulo->a = 1;    //ezt szabad, mert el tudjuk érni
    
    //szulo.c = 1; ezt nem tudjuk elérni, hiába public változó a 'c'
    
    return 0;
}