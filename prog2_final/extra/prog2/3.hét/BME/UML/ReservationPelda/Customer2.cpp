class Customer{
    std::vector<Reservation*> reservations;
public:
    void AddReservation(Reservation* r){
        reservations.push_back(r);
    }

    void RemoveReservation(Reservation* r){
        reservations.erase(
            std::remove(reservations.begin(), reservations.end(),r),reservations.end())
        )
    }
        
    void ListReservations(){
        typedef std::vector<Reservation*>::iterator res_iterator;
        for(res_iterator it = reservations..begin();
            it != reservations.end(); it++){
                cout << (*it)->name;
            }
    }
    
}