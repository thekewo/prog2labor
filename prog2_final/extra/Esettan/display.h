#ifndef DISPLAY_H
#define DISPLAY_H
#include "product.h"

class Display: public Product {
    int inchWidth;
    int inchHeight;

protected:
    void printParams(std::ostream& os) const;
    void loadParamsFromStream(std::istream& is);
    void writeParamsToStream(std::ostream& os) const;
public:
    Display();
    Display(std::string name, int initialPrice, time_t dateOfAcquisition, int inchWidth, int inchHeight);
    virtual ~Display() {}
    int getCurrentPrice() const;
    int getInchWidth() const;
    int getInchHeight() const;
    std::string getType() const {return "Display";}
    char getCharCode() const {return charCode;}
    static const char charCode = 'd';
};

#endif // DISPLAY_H
