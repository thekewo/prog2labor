#ifndef PRODUCT_H
#define PRODUCT_H

#include <iostream>
#include <ctime>

class Product {
protected:
    int initialPrice;
    time_t dateOfAcquisition;
    std::string name;
    virtual void printParams(std::ostream& os) const;
    virtual void loadParamsFromStream(std::istream& is);
    virtual void writeParamsToStream(std::ostream& os) const;

public:
    Product();
    Product(std::string name, int initialPrice, time_t dateOfAcquisition);
    virtual ~Product() {};
    int getInitialPrice() const;
    std::string getName() const;
    time_t getDateOfAcquisition() const;
    int getAge() const;
    virtual int getCurrentPrice() const;
    void print(std::ostream& os) const;
    virtual std::string getType() const = 0;
    virtual char getCharCode() const = 0;
    friend std::istream& operator>>(std::istream& is, Product& product);
    friend std::ostream& operator<<(std::ostream& os, Product& product);
};

#endif // PRODUCT_H
