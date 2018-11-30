#ifndef HARDDISK_H
#define HARDDISK_H

#include "product.h"

class HardDisk: public Product {
    int speedRPM;
protected:
    void printParams(std::ostream& os) const;
    void loadParamsFromStream(std::istream& is);
    void writeParamsToStream(std::ostream& os) const;
public:
    HardDisk();
    HardDisk(std::string name, int initialPrice, time_t dateOfAcquisition, int speedRPM);
    virtual ~HardDisk() {}
    int getCurrentPrice() const;
    int getSpeedRPM() const;
    std::string getType() const {return "HardDisk";}
    char getCharCode() const {return charCode;}
    static const char charCode = 'h';
};

#endif // HARDDISK_H
