class Reservation;

class Bill{
    Reservation* reservation;
public:
    Bill(Reservation * r):reservation(r){}
    Reservation * GetReservation()const {return reservation;}

};

class Reservation{
    Bill bill;
public:
    Reservation():bill(this){}
    const Bill* GetBill()const{return &bill;}
};