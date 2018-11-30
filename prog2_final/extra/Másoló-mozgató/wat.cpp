#include <iostream>
#include <algorithm>

int main(){
    class asd{
    public:
        asd(int param1, int& param2) : a(param1), b(new int){           //konstruktor 2 paraméterrel
         
            *b = param2;

        }

        asd(const asd &pl){                                       //másoló konstruktor
           
            a = pl.a;
            b = new int;
            *b = *pl.b;
        }

        asd& operator= (asd &pl){                                 //másoló értékadás
            
            a = pl.a;
            *b = *pl.b;
            return *this;
        }

        asd(asd && pl){                                         //mozgató konstruktor
            
            a = 0;
            b = nullptr;
            *this = std::move(pl);
            
        }

        asd& operator= (asd && pl){                            //mozgató értékadás
      
            std::swap(b,pl.b);
            std::swap(a,pl.a);
            
            return *this;
        }

        void Print(){
            if(b!=NULL)                                      
                std::cout<<"Az a ertek: "<<a<<" A b ertek "<<*b<<" , és b "<<b<<"-re mutat"<<std::endl;
            else
                std::cout<<"b nem letezik, a pedig 0"<<std::endl;
        }

        ~asd(){                                                     //destruktor
            
            delete b;
        }

        

    private:
    int a;
    int* b;



    };

    int ertek1 = 4;
    int ertek2 = 5;

    
    asd pl1(12, ertek1);
    std::cout<<"pl1 ertekei:"<<std::endl;
    pl1.Print();

    
    asd pl2(pl1);
    std::cout<<"pl1 ertekei:"<<std::endl;
    pl1.Print();
    std::cout<<"pl2 ertekei:"<<std::endl;
    pl2.Print();

    
    asd pl3(20, ertek2);
    std::cout<<"pl3 ertekei:"<<std::endl;
    pl3.Print();

    
    pl2 = pl3;
    std::cout<<"pl3 ertekei:"<<std::endl;
    pl3.Print();
    std::cout<<"pl2 ertekei:"<<std::endl;
    pl2.Print();

    
    asd pl4(std::move(pl1));
    std::cout<<"pl1 ertekei:"<<std::endl;
    pl1.Print();
    std::cout<<"pl4 ertekei:"<<std::endl;
    pl4.Print();

      



    return 0;
}