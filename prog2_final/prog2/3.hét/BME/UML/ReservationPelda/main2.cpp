#include <iostream>
#include <vector>
#include "Bill2.cpp"

int main(int argc, char const *argv[])
{
    std::vector<Reservation> reservations;
    std::vector<Bill> bills;

    //Foglalas szamla nelkul
    Reservation r("egyes");
    reservations.push_back(r);

    //Foglalas szamlaval
    Reservation r2("kettes");
    Bill b(&r2);

    reservations.push_back(r2);
    bills.push_back(b);

    return 0;
}

//bár semmit nem csinál, errort nem ad vissza
//g++ Bill2.cpp main2.cpp -o main2.o
