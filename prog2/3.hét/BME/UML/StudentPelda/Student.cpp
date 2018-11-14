#include <iostream>
#include <map>


class Strudent{
    std::string ID;
};

class Course{
public:
    std::map<std::string, Student*> students;
}