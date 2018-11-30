#include "display.h"

void Display::printParams(std::ostream& os) const {
    Product::printParams(os);
    os << ", " << "InchWidth: " << inchWidth;
    os << ", " << "InchHeight: " << inchHeight;
}

void Display::writeParamsToStream(std::ostream &os) const {
    Product::writeParamsToStream(os);
    os << ' ' << inchWidth << ' ' << inchHeight;
}

void Display::loadParamsFromStream(std::istream &is) {
    Product::loadParamsFromStream(is);
    is >> inchWidth >> inchHeight;
}

Display::Display() {}

Display::Display(std::string name, int initialPrice, time_t dateOfAcquisition, int inchWidth, int inchHeight):
    Product(name, initialPrice, dateOfAcquisition), inchWidth(inchWidth), inchHeight(inchHeight) {}

int Display::getCurrentPrice() const {
    int ageInDays = getAge();
    if(ageInDays < 30)
        return initialPrice;
    else if (ageInDays >= 30 && ageInDays < 90)
        return (int)(0.9 * initialPrice);
    else
        return (int)(0.8 * initialPrice);
}

int Display::getInchWidth() const {
    return inchWidth;
}

int Display::getInchHeight() const {
    return inchHeight;
}
