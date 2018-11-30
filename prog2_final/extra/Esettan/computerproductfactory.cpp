#include "computerproductfactory.h"
#include "display.h"
#include "harddisk.h"
#include "computerconfiguration.h"

Product* ComputerProductFactory::createProduct(char typeCode) const {
    switch(typeCode) {
    case 'd':
        return new Display();
    case 'h':
        return new HardDisk();
    case 'c':
        return new ComputerConfiguration();
    }

    return NULL;
}
