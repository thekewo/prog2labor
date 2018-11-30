#include <iostream>
#include <algorithm>

int main(){
    class wat{
    public:
        wat(int param1, int& param2) : a(param1), b(new int){           //konstruktor 2 paraméterrel
            std::cout<<"\tLefutott a konstruktor!"<<std::endl;
            //b = new int;
            *b = param2;

        }

        wat(const wat &pelda){                                       //másoló konstruktor
            std::cout<<"\tLefutott a másoló konstruktor!"<<std::endl;
            a = pelda.a;
            b = new int;
            *b = *pelda.b;
        }

        wat& operator= (wat &pelda){                                 //másoló értékadás
            std::cout<<"\tMásoló értékadás történt!"<<std::endl;
            a = pelda.a;
            *b = *pelda.b;
            return *this;
        }

        wat(wat && pelda){                                         //mozgató konstruktor
            std::cout<<"\tLefutott a mozgato konstruktor!"<<std::endl;
            a = 0;
            b = nullptr;
            *this = std::move(pelda);
            
        }

        wat& operator= (wat && pelda){                            //mozgató értékadás
            std::cout<<"\tMozgato ertekadas tortent!"<<std::endl;
            std::swap(b,pelda.b);
            std::swap(a,pelda.a);
            
            return *this;
        }

        void Print(){
            if(b!=NULL)                                      
                std::cout<<"Az a ertek: "<<a<<" A b ertek "<<*b<<" , és b "<<b<<"-re mutat"<<std::endl;
            else
                std::cout<<"b nem letezik, a pedig 0"<<std::endl;
        }

        ~wat(){                                                     //destruktor
            std::cout<<"\tLefutott a destruktor!"<<std::endl;
            delete b;
        }

        

    private:
    int a;
    int* b;



    };

    int ertek1 = 8;
    int ertek2 = 9;

    std::cout<<"Legyen ertek1 értéke 8, ertek2 értéke pedig 9!"<<std::endl;

    std::cout<<"\n\npelda1 letrehozasa 10, ertek1 értékekkel:"<<std::endl;
    wat pelda1(10, ertek1);
    std::cout<<"pelda1 ertekei:"<<std::endl;
    pelda1.Print();

    std::cout<<"\n\npelda2 letrehozasa pelda1 alapjan:"<<std::endl;
    wat pelda2(pelda1);
    std::cout<<"pelda1 ertekei:"<<std::endl;
    pelda1.Print();
    std::cout<<"pelda2 ertekei:"<<std::endl;
    pelda2.Print();

    std::cout<<"\n\npelda3 letrehozasa 20, ertek2 ertekekkel:"<<std::endl;
    wat pelda3(20, ertek2);
    std::cout<<"pelda3 ertekei:"<<std::endl;
    pelda3.Print();

    std::cout<<"\n\npelda2 = pelda3:"<<std::endl;
    pelda2 = pelda3;
    std::cout<<"pelda3 ertekei:"<<std::endl;
    pelda3.Print();
    std::cout<<"pelda2 ertekei:"<<std::endl;
    pelda2.Print();

    std::cout<<"\n\npelda4 letrehozasa mozgato konstruktorral:"<<std::endl;
    wat pelda4(std::move(pelda1));
    std::cout<<"pelda1 ertekei:"<<std::endl;
    pelda1.Print();
    std::cout<<"pelda4 ertekei:"<<std::endl;
    pelda4.Print();

      std::cout<<"\n\nprogram vege:"<<std::endl;



    return 0;
}