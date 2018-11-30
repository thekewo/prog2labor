#include <cstddef>
#include <string>
#include <iostream>
#include <vector>

class Bill;

class Reservation{
    Bill* bill;
    std::string name;
    //std::vector<Accomodation*> accomodations;
public:
    Reservation(std::string s):bill(NULL){
           
    }
    const Bill* GetBill()const{return bill;}
    void SetBill(Bill* pb){bill = pb;}
    std::string GetName(){return name;}
};

class Bill{
    Reservation* reservation;
public:
    Bill(Reservation * r ):reservation(r){r->SetBill(this);}
    Reservation* GetReservation()const {return reservation;}
};

class Customer{
    std::vector<Reservation*> reservations;
public:
    void AddReservation(Reservation* r){
        reservations.push_back(r);
    }

    void RemoveReservation(Reservation* r){
        reservations.erase(
            std::remove(reservations.begin(), reservations.end(),r),
            reservations.end());
    }
        
    void ListReservations(){
        typedef std::vector<Reservation*>::iterator res_iterator;
        for(res_iterator it = reservations.begin();
            it != reservations.end(); it++){
                std::cout << (*it)->GetName();
            }
    }
    
};