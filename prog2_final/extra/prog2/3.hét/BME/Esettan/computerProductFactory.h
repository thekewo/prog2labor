#ifndef COMPUTERPRODUCTFACTORY_H
#define COMPUTERPRODUCTFACTORY_H

#include <iostream>
#include "productFactory.h"
#include "hardDisk.h"
#include "display.h"


class ComputerProductFactory: public ProductFactory{
public:
    Product* CreateProduct(char typeCode) const;
};

Product* ComputerProductFactory::CreateProduct(char typeCode) const{
    switch(typeCode){
        case 'd':
            return new Display();
        case 'h':
            return new HardDisk();
        case 'c':
            return new ComputerConfiguration();
    }
    return NULL;
}


#endif COMPUTERPRODUCTFACTORY_H