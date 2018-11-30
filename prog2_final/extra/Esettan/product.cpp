#include "product.h"
#include <string.h>
#include <stdexcept>
#include <iostream>
#include <cstring>
#include <cstdlib>



using namespace std;

time_t Product::getDateOfAcquisition() const {
    return dateOfAcquisition;
}

int Product::getInitialPrice() const {
    return initialPrice;
}

std::string Product::getName() const {
    return name;
}

Product::Product() {}

Product::Product(std::string name, int initialPrice, time_t dateOfAcquisition): name(name), initialPrice(initialPrice),
    dateOfAcquisition(dateOfAcquisition) {
}

int Product::getAge() const{
    time_t currentTime;
    time(&currentTime);
    double timeDiffInSec = difftime(currentTime, dateOfAcquisition);
    return (int)(timeDiffInSec/(3600*24));
}

int Product::getCurrentPrice() const {
    return initialPrice;
}

void Product::print(std::ostream &os) const {
    os << "Type: " << getType() << ", ";
    os << "Name: " << getName();
    printParams(os);
}

void Product::printParams(std::ostream &os) const {
    char strDateOfAcquisition[9];
    strftime(strDateOfAcquisition, 9, "%Y%m%d",
             gmtime(&dateOfAcquisition));

    os << ", " << "Initial price: " << initialPrice
       << ", " << "Date of acquisition: " << strDateOfAcquisition
       << ", " << "Age: " << getAge()
       << ", " << "Current price: " << getCurrentPrice();
}

void Product::writeParamsToStream(std::ostream &os) const {
    char strDateOfAcquisition[9];
    tm* t = localtime(&dateOfAcquisition);
    strftime(strDateOfAcquisition, 9, "%Y%m%d", t);
    os << " " << name << " " << initialPrice << " " <<strDateOfAcquisition;
}

void Product::loadParamsFromStream(std::istream &is) {
    is >> name;
    is >> initialPrice;

    char buff[9];
    is.width(9);
    is >> buff;
    if (strlen(buff) != 8)
        throw range_error("Invalid time format");

    char workBuff[5];
    tm t;
    int year;
    strncpy(workBuff, buff, 4); workBuff[4] = '\0';
    year = atoi(workBuff); t.tm_year = year - 1900;
    strncpy(workBuff, &buff[4], 2); workBuff[2] = '\0';
    t.tm_mon = atoi(workBuff) - 1;
    strncpy(workBuff, &buff[6], 2); workBuff[2] = '\0';
    t.tm_mday = atoi(workBuff);
    t.tm_hour = t.tm_min = t.tm_sec = 0;
    t.tm_isdst = -1;

    dateOfAcquisition = mktime(&t);
}

std::istream& operator>>(istream& is, Product& product) {
    product.loadParamsFromStream(is);
    return is;
}

std::ostream& operator<<(ostream& os, Product& product) {
    os <<product.getCharCode();
    product.writeParamsToStream(os);
    return os;
}



