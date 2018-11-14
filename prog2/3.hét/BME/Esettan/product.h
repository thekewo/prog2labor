#ifndef PRODUCT_H
#define PRODUCT_H

#include <iostream>
#include <ctime>
#include <string>
#include <cstring>
#include <stdexcept>

class Product{
protected:
    int initialPrice; //beszerzési ár
    time_t dateOfAcquisition; //beszerzés dátuma
    std::string name; //neve
    virtual void printParams(std::ostream& os) const;
    virtual void loadParamsFromStream(std::istream& is);
    virtual void writeParamsToStream(std::ostream& os) const;
public:
    Product();
    Product(std::string name, int initialPrice, time_t dateOfAcquisition);
    virtual ~Product(){};
    int GetInitialPrice() const;
    std::string GetName() const;
    time_t GetDateOfAcquisition() const;
    int GetAge() const;
    virtual int GetCurrentPrice() const;
    void Print(std::ostream& os) const;
    virtual std::string GetType() const = 0;
    virtual char GetCharCode() const = 0;
    friend std::istream& operator>>(std::istream& is, Product& product);
    friend std::ostream& operator<<(std::ostream& os, const Product& product);
};

time_t Product::GetDateOfAcquisition() const{
    return dateOfAcquisition;
}

int Product::GetInitialPrice() const{
    return initialPrice;
}

std::string Product::GetName() const {return name;}

Product::Product(){}

Product::Product(std::string name, int initialPrice, time_t dateOfAcquisition):
    name(name), initialPrice(initialPrice), dateOfAcquisition(dateOfAcquisition){}

int Product::GetAge() const{
    time_t currentTime;
    time(&currentTime);
    double timeDiffInSec = difftime(currentTime, dateOfAcquisition);
    return (int)(timeDiffInSec/(3600 * 24));
}

int Product::GetCurrentPrice() const{
    return initialPrice;
}

void Product::Print(std::ostream& os) const{
    os << "Type: " << GetType() << ", ";
    os << "Name? " << GetName();
    printParams(os);
}

void Product::printParams(std::ostream& os) const{
    char strDateOfAcquisition[9];
    strftime(strDateOfAcquisition, 9, "%Y%m%d", gmtime(&dateOfAcquisition));

    os << ", " << "InitialPrice: " << initialPrice
        << ", " << "DateOfAcquisition: " <<strDateOfAcquisition
        << ", " << "Age: " << GetAge()
        << ", " << "Current price: " << GetCurrentPrice();
}

void Product::writeParamsToStream(std::ostream& os) const{
    char strDateOfAcquisition[9];
    tm* t = localtime(&dateOfAcquisition);
    strftime(strDateOfAcquisition, 9, "%Y%m%d", t);
    os << ' ' << name << ' ' << initialPrice << ' ' << strDateOfAcquisition;
}

void Product::loadParamsFromStream(std::istream& is){
    is >> name;
    is >> initialPrice;

    char buff[9];
    is.width(9);
    is >> buff;
    if(strlen(buff) != 8)
        throw std::range_error("Invalid time format.");
    
    char workBuff[5];
    tm t;
    int year;
    strncpy(workBuff, buff, 4);
    workBuff[4] = '\0';
    year = atoi(workBuff);
    t.tm_year = year - 1900;
    strncpy(workBuff, &buff[4], 2);
    workBuff[2] =  '\0';
    t.tm_mon = atoi(workBuff) - 1;
    strncpy(workBuff, &buff[6], 2);
    workBuff[2] = '\0';
    t.tm_mday = atoi(workBuff);
    t.tm_hour = t.tm_min = t.tm_sec = 0;
    t.tm_isdst = -1;

    dateOfAcquisition = mktime(&t);
}

std::istream& operator>>(std::istream& is, Product& product){
    product.loadParamsFromStream(is);
    return is;
}

std::ostream& operator<<(std::ostream& os, const Product& product){
    os << product.GetCharCode();
    product.writeParamsToStream(os);
    return os;
}

#endif PRODUCT_H