#include "harddisk.h"

int HardDisk::getCurrentPrice() const{
    int ageInDays = getAge();
    if(ageInDays < 30)
        return initialPrice;
    else if (ageInDays >= 30 && ageInDays < 90)
        return (int)(0.9 * initialPrice);
    else
        return (int)(0.8 * initialPrice);
}


HardDisk::HardDisk() {};

HardDisk::HardDisk(std::string name, int initialPrice, time_t dateOfAcquisition, int speedRPM):
    Product(name, initialPrice, dateOfAcquisition), speedRPM(speedRPM) {}

int HardDisk::getSpeedRPM() const {
    return speedRPM;
}

void HardDisk::printParams(std::ostream& os) const {
    Product::printParams(os);
    os << ", " << "SpeedRPM: " << speedRPM;
}

void HardDisk::writeParamsToStream(std::ostream &os) const {
    Product::writeParamsToStream(os);
    os << ' ' << speedRPM;
}

void HardDisk::loadParamsFromStream(std::istream &is) {
    Product::loadParamsFromStream(is);
    is >> speedRPM;
}
