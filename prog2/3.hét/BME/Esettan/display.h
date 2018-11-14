#ifndef DISPLAY1_H
#define DISPLAY_H

#include <iostream>
#include "product.h"

class Display : public Product{
    int inchWidth;
    int inchHeight;

protected:
    void printParams(std::ostream& os) const;
    void writeParamsToStream(std::ostream& os) const;
    void loadParamsFromStream(std::istream& is);
};

void Display::printParams(std::ostream& os) const{
    Product::printParams(os);
    os << ", " << "InchWidth: " << inchWidth;
    os << ", " << "InchHeight: " << inchHeight;
}
void Display::writeParamsToStream(std::ostream& os) const{
    Product::writeParamsToStream(os);
    os << ' ' << inchWidth << ' ' << inchHeight;
}

void Display::loadParamsFromStream(std::istream& is){
    Product::loadParamsFromStream(is);
    is >> inchWidth >> inchHeight;
}


#endif DISPLAY_H